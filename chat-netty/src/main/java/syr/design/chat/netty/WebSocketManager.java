package syr.design.chat.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;
import syr.design.chat.enums.EnumWebSocketMessageType;
import syr.design.chat.model.Message;
import syr.design.chat.model.SocketMessage;
import syr.design.chat.model.Users;
import syr.design.chat.utils.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @author shangyouren
 */
@Component
public class WebSocketManager {

    private ConcurrentMap<String, ChannelGroup> userTagChannelMap;

    private ConcurrentMap<String, String> channelidUserTagMap;

    public WebSocketManager(ConcurrentMap<String, ChannelGroup> userTagChannelMap, ConcurrentMap<String, String> channelidUserTagMap){
        this.userTagChannelMap = userTagChannelMap;
        this.channelidUserTagMap = channelidUserTagMap;
    }

    void addChannel(Users users, Channel channel){
        if (users == null || channel == null){
            return ;
        }
        ChannelGroup group = userTagChannelMap.get(StringUtils.getNettyTag(users));
        if (group == null){
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        if (group.find(channel.id()) == null){
            group.add(channel);
            userTagChannelMap.put(StringUtils.getNettyTag(users), group);
        }
        channelidUserTagMap.computeIfAbsent(channel.id().asLongText(), t-> StringUtils.getNettyTag(users));
    }

    void removeChannel(Channel channel){
        if (channel == null){
            return ;
        }
        if (channelidUserTagMap.get(channel.id().asLongText()) != null) {
            String userNettyTag = channelidUserTagMap.get(channel.id().asLongText());
            channelidUserTagMap.remove(channel.id().asLongText());
            ChannelGroup group = userTagChannelMap.get(userNettyTag);
            if (group != null) {
                group.remove(channel);
                if (group.size() <= 0) {
                    userTagChannelMap.remove(userNettyTag);
                }else {
                    userTagChannelMap.put(userNettyTag, group);
                }
            }
        }
    }

    private ChannelGroup findChannel(String userNettyTag) {
        ChannelGroup group;
        if (userTagChannelMap.get(userNettyTag) == null){
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }else {
            group = userTagChannelMap.get(userNettyTag);
        }
        return group;
    }

    public void sendMessageToUser(Users users, Message message) {
        if (users == null || message == null) {
            return;
        }
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setFromUserId(users.getId());
        socketMessage.setType(EnumWebSocketMessageType.message.value());
        socketMessage.setMessage(message);
        socketMessage.setFromUserName(users.getUsername());
        ChannelGroup group = findChannel(StringUtils.getNettyTag(message));
        if (group == null) {
            return;
        }
        group.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(socketMessage)));
        group = findChannel(StringUtils.getNettyTag(users));
        if (group == null) {
            return;
        }
        group.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(socketMessage)));
        System.out.println("[socket message]" + JSON.toJSONString(message));
    }

    public void sendMessageToGroup(List<Users> toUsers, Users fromUsers, Message message) {
        if (toUsers == null || toUsers.size() <= 0 || fromUsers == null || message == null) {
            return;
        }
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setFromUserId(fromUsers.getId());
        socketMessage.setType(EnumWebSocketMessageType.message.value());
        socketMessage.setMessage(message);
        socketMessage.setFromUserName(fromUsers.getUsername());
        ChannelGroup group;
        for (Users touser : toUsers) {
            group = findChannel(StringUtils.getNettyTag(touser));
            if (group != null) {
                group.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(socketMessage)));
            }
        }
    }

    void sendSimpleMessage(String usernameMd5, String msg) {
        if (usernameMd5 == null || "".equals(usernameMd5) || msg == null || "".equals(msg)) {
            return;
        }
        ChannelGroup group = findChannel(usernameMd5);
        if (group == null) {
            return;
        }
        SocketMessage message = new SocketMessage();
        message.setFromUserId(0L);
        message.setType(EnumWebSocketMessageType.dispay.value());
        message.setMsg(msg);
        message.setFromUserName("系统");
        group.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
    }

    public void sendSocketMessage(String usernameMd5, SocketMessage msg){
        if (usernameMd5 == null || "".equals(usernameMd5.trim()) || msg == null){
            return ;
        }
        ChannelGroup group = findChannel(usernameMd5);
        if (group == null){
            return ;
        }
        group.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }

}
