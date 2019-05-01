package syr.design.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitmqConfig {

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean(name = "directExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(MqConfig.chat.EXCHANGE_NAME);
    }

    @Bean(name = "chatQueue")
    public Queue chatQueue(){
        return new Queue(MqConfig.chat.QUEUE_NAME, false);
    }

    @Bean
    public Binding bind(@Qualifier("directExchange") DirectExchange exchange,
                        @Qualifier("chatQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(MqConfig.chat.ROUTING_KEY);
    }


}
