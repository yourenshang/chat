package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.Friend;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface IFriendService extends IService<Friend> {

    Friend findFriend(Long userId, Long toUserId);

    List<Friend> findFriendList(Long userId, Integer status);
}
