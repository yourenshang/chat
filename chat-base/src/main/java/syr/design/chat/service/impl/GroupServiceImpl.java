package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syr.design.chat.enums.EnumRoleLev;
import syr.design.chat.enums.EnumUserGroupStatus;
import syr.design.chat.mapper.GroupMapper;
import syr.design.chat.model.Groups;
import syr.design.chat.model.Role;
import syr.design.chat.model.UserGroup;
import syr.design.chat.service.IGroupService;
import syr.design.chat.service.IRoleService;
import syr.design.chat.service.IUserGroupService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Groups> implements IGroupService {

    @Resource
    private IRoleService roleService;

    @Resource
    private IUserGroupService userGroupService;

    @Override
    public boolean add(Groups group, Long userId) {
        UserGroup userGroup = new UserGroup();
        Role role = this.roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getLev, EnumRoleLev.owner.value()));
        boolean result = this.save(group);
        if (result){
            userGroup.setGroupId(group.getId());
            userGroup.setRoleId(role.getId());
            userGroup.setUserId(userId);
            userGroup.setStatus(EnumUserGroupStatus.agree.value());
            result = this.userGroupService.save(userGroup);
        }
        return result;
    }

    @Override
    public boolean del(Groups group) {
        boolean result = this.removeById(group.getId());
        if (result){
            result = this.userGroupService.remove(new LambdaQueryWrapper<UserGroup>().eq(UserGroup::getGroupId, group.getId()));
        }
        return result;
    }
}
