package syr.design.chat.message;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import syr.design.chat.config.MqConfig;
import syr.design.chat.model.SocketMessage;

import javax.annotation.Resource;

@Component
public class RabbitPub {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void pub(SocketMessage socketMessage){
        rabbitTemplate.convertSendAndReceive(MqConfig.chat.EXCHANGE_NAME, MqConfig.chat.ROUTING_KEY, JSON.toJSONString(socketMessage));
    }

}
