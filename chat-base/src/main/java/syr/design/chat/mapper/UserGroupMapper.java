package syr.design.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import syr.design.chat.model.Groups;
import syr.design.chat.model.UserGroup;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    List<UserGroup> findByGroupId(Long groupId);

    //查找groups
    List<Groups> getGroupByUserId(@Param("userId") Long userId, @Param("status") Integer status);

    List<UserGroup> findWaitAgree(@Param("userId") Long userId, @Param("lev") Integer lev, @Param("status") Integer status);
}
