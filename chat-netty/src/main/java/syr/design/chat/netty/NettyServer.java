package syr.design.chat.netty;//package syr.design.chat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Service;
import syr.design.chat.utils.juc.ExecutorUtils;

import javax.annotation.PostConstruct;

@Service
public class NettyServer {

    @PostConstruct
    public void initNetty(){
        ExecutorUtils.getPool().submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("----------  netty启动开始  ---------");
                runNetty();
            }
        });
    }

    private void runNetty(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new WebSocketChannelInitializer());
            Channel channel = bootstrap.bind(9006).sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
