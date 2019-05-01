package syr.design.chat.config;

import io.netty.channel.group.ChannelGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import syr.design.chat.netty.WebSocketManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class WebSocketManagerConfig {

    @Bean(name = "user_tag_channel_map")
    public ConcurrentMap<String, ChannelGroup> concurrentMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "channelid_user_tag_map")
    public ConcurrentMap<String, String> concurrentMap2(){
        return new ConcurrentHashMap<>();
    }

    @Bean(name = "webSocketManager")
    public WebSocketManager webSocketManager(ConcurrentMap<String, ChannelGroup> map1, ConcurrentMap<String, String> map2){
        return new WebSocketManager(map1, map2);
    }

}
