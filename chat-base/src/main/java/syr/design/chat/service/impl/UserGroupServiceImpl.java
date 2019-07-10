package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syr.design.chat.enums.EnumRoleLev;
import syr.design.chat.enums.EnumUserGroupStatus;
import syr.design.chat.mapper.UserGroupMapper;
import syr.design.chat.model.Groups;
import syr.design.chat.model.Role;
import syr.design.chat.model.UserGroup;
import syr.design.chat.service.IGroupService;
import syr.design.chat.service.IRoleService;
import syr.design.chat.service.IUserGroupService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author syr
 * @since 2019-04-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements IUserGroupService {

    @Resource
    private IGroupService groupService;

    @Resource
    private IRoleService roleService;

    @Override
    public List<UserGroup> findByGroupId(Long groupId) {
        return this.baseMapper.findByGroupId(groupId);
    }

    @Override
    public boolean refund(UserGroup userGroup, Long nextOwnerUserId, Integer lev) {
        boolean result = this.removeById(userGroup.getId());
        if (result){
            List<UserGroup> userGroups = this.findByGroupId(userGroup.getGroupId());
            if (userGroups == null || userGroups.size() <= 0){
                result = this.groupService.removeById(userGroup.getGroupId());
            }else {
                if (lev.equals(EnumRoleLev.owner.value())){
                    Role role = this.roleService.getOne(new LambdaQueryWrapper<Role>()
                            .eq(Role::getLev, EnumRoleLev.owner.value()));
                    if (nextOwnerUserId == null){
                        UserGroup nextOwner = userGroups.get(0);
                        nextOwner.setRoleId(role.getId());
                        result = this.updateById(nextOwner);
                    }else {
                        UserGroup nextOwner = this.getById(nextOwnerUserId);
                        if (nextOwner == null){
                            nextOwner = userGroups.get(0);
                            nextOwner.setRoleId(role.getId());
                            result = this.updateById(nextOwner);
                        }else {
                            nextOwner.setRoleId(role.getId());
                            result = this.updateById(nextOwner);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Groups> getGroupByUserId(Long userId, Integer status) {
        return this.baseMapper.getGroupByUserId(userId, status);
    }

    @Override
    public List<UserGroup> findWaitAgree(Long userId) {
        return this.baseMapper.findWaitAgree(userId, EnumRoleLev.manager.value(), EnumUserGroupStatus.apply.value());
    }
}
