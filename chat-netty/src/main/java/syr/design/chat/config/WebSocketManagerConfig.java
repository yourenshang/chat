package syr.design.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import syr.design.chat.netty.WebSocketManager;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebSocketManagerConfig {

    @Bean(name = "webSocketManager")
    public WebSocketManager webSocketManager(){
        return new WebSocketManager(new ConcurrentHashMap<>(), new ConcurrentHashMap<>());
    }

}
