package syr.design.chat.listener;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import syr.design.chat.config.MqConfig;
import syr.design.chat.model.SocketMessage;
import syr.design.chat.netty.WebSocketManager;
import syr.design.chat.utils.StringUtils;

import javax.annotation.Resource;

/**
 * @author shangyouren
 */
@Component
public class Listener {

    @Resource(name = "webSocketManager")
    private WebSocketManager webSocketManager;

    @RabbitListener(queues = MqConfig.chat.QUEUE_NAME)
    public void listener(String message){
        SocketMessage socketMessage = JSON.parseObject(message, SocketMessage.class);
        if (socketMessage != null && socketMessage.getMessage() != null && socketMessage.getMessage().getToUserName() != null ){
            webSocketManager.sendSocketMessage(StringUtils.md5hash(socketMessage.getMessage().getToUserName()), socketMessage);
        }
    }

}
