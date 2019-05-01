package syr.design.chat.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syr.design.chat.enums.EnumMessageSendType;
import syr.design.chat.model.Friend;
import syr.design.chat.model.Message;
import syr.design.chat.model.Users;
import syr.design.chat.netty.WebSocketManager;
import syr.design.chat.service.IFriendService;
import syr.design.chat.service.IMessageService;
import syr.design.chat.service.IUsersService;
import syr.design.chat.service.IWebSocketFrameService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shangyouren
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WebSocketFrameServiceImpl implements IWebSocketFrameService {

    @Resource
    private IUsersService usersService;

    @Resource
    private IFriendService iFriendService;

    @Resource
    private IMessageService messageService;

    @Resource
    private WebSocketManager webSocketManager;

    @Override
    public boolean sendMessage(Long userId, Message message){
        Users users = this.usersService.getById(userId);
        if (users == null){
            return false;
        }
        if (message.getType().equals(EnumMessageSendType.user.value())){
            Friend friend = this.iFriendService.findFriend(userId, message.getToUserId());
            if (friend == null){
                return false;
            }
            webSocketManager.sendMessageToUser(users, message);
        }else if (message.getType().equals(EnumMessageSendType.group.value())){
            List<Users> usersList = this.usersService.findByGroupId(message.getToGroupId());
            webSocketManager.sendMessageToGroup(usersList, users, message);
        }
        this.messageService.save(message);
        return true;
    }

}
