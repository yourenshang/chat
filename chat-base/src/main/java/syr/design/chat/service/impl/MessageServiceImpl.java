package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import syr.design.chat.enums.EnumMessageSendType;
import syr.design.chat.mapper.MessageMapper;
import syr.design.chat.model.Message;
import syr.design.chat.service.IMessageService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public List<Message> getMessageByUser(Long userId, Long friendUserId) {
        return this.baseMapper.getMessageByUser(userId, friendUserId, EnumMessageSendType.user.value());
    }

    @Override
    public List<Message> getMessageByGroup(Long userId, Long groupId) {
        return this.baseMapper.getMessageByGroup(userId, groupId, EnumMessageSendType.group.value());
    }

    @Override
    public List<Message> getMessage(Long userId) {
        return this.baseMapper.getMessage(userId);
    }
}
