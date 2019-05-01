package syr.design.chat.service;

import syr.design.chat.model.Message;

/**
 * @author shangyouren
 */
public interface IWebSocketFrameService {

    boolean sendMessage(Long userId, Message message);
}
