package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import syr.design.chat.mapper.NettyMessageMapper;
import syr.design.chat.model.Message;
import syr.design.chat.service.IMessageService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<NettyMessageMapper, Message> implements IMessageService {

}
