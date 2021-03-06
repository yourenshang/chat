package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.Message;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syr
 * @since 2019-04-28
 */
public interface IMessageService extends IService<Message> {

    List<Message> getMessageByUser(Long userId, Long friendUserId);

    List<Message> getMessageByGroup(Long userId, Long id);

    List<Message> getMessage(Long userId);
}
