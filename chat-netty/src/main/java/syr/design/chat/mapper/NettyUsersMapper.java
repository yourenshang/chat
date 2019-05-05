package syr.design.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import syr.design.chat.model.Users;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface NettyUsersMapper extends BaseMapper<Users> {

    List<Users> findByGroupId(@Param("toGroupId") Long toGroupId, @Param("status") Integer status);
}
