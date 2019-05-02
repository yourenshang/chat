package syr.design.chat.utils;

import syr.design.chat.model.Message;
import syr.design.chat.model.SocketMessage;

import java.util.Date;

/**
 * @author shangyouren
 */
public class GenUtils {


    public static SocketMessage getSockenMessage(String fromUserName,
                                                 Long fromUserId,
                                                 String token,
                                                 Integer type,
                                                 String msg,
                                                 Message message){
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setFromUserName(fromUserName);
        socketMessage.setFromUserId(fromUserId);
        socketMessage.setToken(token);
        socketMessage.setType(type);
        socketMessage.setMsg(msg);
        socketMessage.setMessage(message);
        return socketMessage;
    }


    public static Message getMessage(String fromUserName,
                                     Long fromUserId,
                                     String messageString,
                                     Integer messageType,
                                     Integer status,
                                     Integer type,
                                     Long toGroupId,
                                     Long toUserId,
                                     String toUserName){
        Message message = new Message();
        message.setFromUserId(fromUserId);
        message.setFromUserName(fromUserName);
        message.setMessage(messageString);
        message.setMessageType(messageType);
        message.setSendTime(new Date());
        message.setStatus(status);
        message.setType(type);
        message.setToGroupId(toGroupId);
        message.setToUserId(toUserId);
        message.setToUserName(toUserName);
        return message;
    }
}
