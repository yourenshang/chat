package syr.design.chat.netty;//package syr.design.chat.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import syr.design.chat.enums.EnumWebSocketMessageType;
import syr.design.chat.model.SocketMessage;
import syr.design.chat.model.Users;
import syr.design.chat.service.IUsersService;
import syr.design.chat.service.IWebSocketFrameService;
import syr.design.chat.utils.*;

import java.util.List;
import java.util.Map;

@Repository
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;
    private String websocketUri = "ws://127.0.0.1:9006/syr_websocket";

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            fullHttpRequestHandler(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            webSocketFramHandler(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * 初次建立连接调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SpringUtils.webSocketManager().addChannel(null, ctx.channel());
    }

    /**
     * 断开连接调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SpringUtils.webSocketManager().removeChannel(ctx.channel());
    }

    /**
     * channel读区完通道后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }

    /**
     * 捕获异常调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 处理websocket的信息
     */
    private void webSocketFramHandler(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("不支持二进制消息"));
            return;
        }
        String text = ((TextWebSocketFrame) frame).text();
        System.out.println(text);
        ApplicationContext context = SpringUtils.getApplicationContext();
        JJWTUtil jjwtUtil = context.getBean(JJWTUtil.class);
        SocketMessage socketMessage = GenUtils.getSocketMessage(JSON.parseObject(text));
        String token = socketMessage.getToken();
        if (token == null) {
            ctx.close();
            return;
        }
        Map<String, Object> tokenMap = jjwtUtil.checkToken(token);
        if (tokenMap == null) {
            ctx.close();
            return;
        }
        String userId = tokenMap.get("userId").toString();
        RedisUtils redisUtil = context.getBean(RedisUtils.class);
        if (!redisUtil.hasKey(userId + "user")) {
            ctx.close();
            return;
        }
        boolean result = false;
        if (socketMessage.getType().equals(EnumWebSocketMessageType.message.value())) {
            result = context.getBean(IWebSocketFrameService.class).sendMessage(Long.valueOf(userId), socketMessage.getMessage());
        }
        if (!result) {
            Users users = redisUtil.get(userId + "user", Users.class);
            SpringUtils.webSocketManager().sendSimpleMessage(StringUtils.md5hash(users.getUsername()), "消息发送失败");
        }
    }

    /**
     * 处理初次建立连接http的信息
     */
    private void fullHttpRequestHandler(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.getDecoderResult().isSuccess() || !("websocket".equals(request.headers().get("Upgrade")))) {
            //返回失败信息
            sendResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }
        String uri = request.getUri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        List<String> usernames = parameters.get("username");
        String username = usernames.get(0);
        ApplicationContext context = SpringUtils.getApplicationContext();
        //验证用户权限
        IUsersService usersService = context.getBean(IUsersService.class);
        Users users = usersService.selectByUserName(username);
        if (users == null) {
            return;
        }
        SpringUtils.webSocketManager().addChannel(users, ctx.channel());
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(websocketUri, null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), request);
        }
    }

    /**
     * 向客户端发送http的response
     */
    private void sendResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture future = ctx.channel().writeAndFlush(response);
        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

}
