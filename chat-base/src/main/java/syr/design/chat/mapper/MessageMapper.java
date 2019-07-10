package syr.design.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import syr.design.chat.model.Message;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author syr
 * @since 2019-04-28
 */
public interface MessageMapper extends BaseMapper<Message> {

    List<Message> getMessageByUser(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId, @Param("type") Integer type);

    List<Message> getMessageByGroup(@Param("userId") Long userId, @Param("groupId") Long groupId, @Param("type") Integer type);

    List<Message> getMessage(@Param("userId") Long userId);
}
