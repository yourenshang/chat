package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface IUsersService extends IService<Users> {

    /**
     * 根据用户名查询用户
     */
    Users selectByUserName(String username);

    List<Users> findByGroupId(Long toGroupId);
}
