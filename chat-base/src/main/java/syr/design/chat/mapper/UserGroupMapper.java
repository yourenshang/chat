package syr.design.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
