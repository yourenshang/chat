package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import syr.design.chat.mapper.FriendMapper;
import syr.design.chat.model.Friend;
import syr.design.chat.service.IFriendService;

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
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

    @Override
    public Friend findFriend(Long userId, Long toUserId) {
        return this.baseMapper.findFriend(userId, toUserId);
    }

    @Override
    public List<Friend> findFriendList(Long userId, Integer status) {
        return this.baseMapper.findFriendList(userId, status);
    }
}
