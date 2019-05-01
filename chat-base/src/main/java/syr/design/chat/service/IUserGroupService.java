package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.UserGroup;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface IUserGroupService extends IService<UserGroup> {

    List<UserGroup> findByGroupId(Long groupId);

    boolean refund(UserGroup userGroup, Long nextOwnerUserId, Integer lev);
}
