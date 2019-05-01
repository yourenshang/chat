package syr.design.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import syr.design.chat.model.Friend;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface FriendMapper extends BaseMapper<Friend> {

    Friend findFriend(@Param("userId") Long userId,
                      @Param("toUserId") Long toUserId);

    List<Friend> findFriendList(@Param("userId") Long userId,
                                @Param("status") Integer status);
}
