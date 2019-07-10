package syr.design.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import syr.design.chat.utils.juc.ExecutorUtils;

@Configuration
public class BeanConfig {

    @Bean
    public ExecutorUtils executorUtils(){
        return new ExecutorUtils();
    }

}
