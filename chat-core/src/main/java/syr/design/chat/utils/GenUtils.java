package syr.design.chat.utils;

import com.alibaba.fastjson.JSONObject;
import syr.design.chat.model.Message;
import syr.design.chat.model.SocketMessage;

import java.util.Date;

/**
 * @author shangyouren
 */
public class GenUtils {


    public static SocketMessage getSocketMessage(String fromUserName,
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

    public static Message getMessage(JSONObject one){
        Message message = new Message();
        message.setToUserName(one.getString("toUserName"));
        message.setToUserId(one.getString("toUserId") == null ? null : Long.valueOf(one.getString("toUserId").trim()));
        message.setFromUserId(one.getString("fromUserId") == null ? null : Long.valueOf(one.getString("fromUserId").trim()));
        message.setFromUserName(one.getString("fromUserName"));
        message.setToGroupId(one.getString("toGroupId") == null ? null : Long.valueOf(one.getString("toGroupId").trim()));
        message.setType(one.getInteger("type"));
        message.setStatus(one.getInteger("status"));
        message.setMessageType(one.getInteger("messageType"));
        message.setToGroupName(one.getString("toGroupName"));
        message.setMessage(one.getString("message"));
        return message;
    }

    public static SocketMessage getSocketMessage(JSONObject one){
        SocketMessage socketMessage = new SocketMessage();
        socketMessage.setMessage(getMessage(one.getJSONObject("message")));
        socketMessage.setMsg(one.getString("msg"));
        socketMessage.setType(one.getInteger("type"));
        socketMessage.setToken(one.getString("token"));
        socketMessage.setFromUserId(one.getString("fromUserId") == null ? null : Long.valueOf(one.getString("fromUserId").trim()));
        socketMessage.setFromUserName(one.getString("fromUserName"));
        return socketMessage;
    }
}
