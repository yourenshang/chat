package syr.design.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("syr.design.chat.mapper")
public class ChatNettyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatNettyApplication.class, args);
    }

}
