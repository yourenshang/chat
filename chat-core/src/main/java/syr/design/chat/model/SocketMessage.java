package syr.design.chat.model;

import lombok.Data;

@Data
public class SocketMessage {

    private Long fromUserId;

    private String token;

    private Integer type;

    private Message message;

    private String msg;

    private String fromUserName;
}
