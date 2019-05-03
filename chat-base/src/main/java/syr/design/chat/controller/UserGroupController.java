package syr.design.chat.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.enums.EnumRoleLev;
import syr.design.chat.enums.EnumUserGroupStatus;
import syr.design.chat.model.Groups;
import syr.design.chat.model.Result;
import syr.design.chat.model.Role;
import syr.design.chat.model.UserGroup;
import syr.design.chat.service.IGroupService;
import syr.design.chat.service.IRoleService;
import syr.design.chat.service.IUserGroupService;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/userGroup")
public class UserGroupController extends BaseController {

    @Resource
    private IGroupService groupService;

    @Resource
    private IUserGroupService userGroupService;

    @Resource
    private IRoleService roleService;

    /**
     * 用户申请加群
     */
    @PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result apply(@RequestParam("userId") Long userId,
                        @RequestParam("groupId") Long groupId){
        Groups group = this.groupService.getById(groupId);
        if (group == null){
            return result(EnumResultCode.FAIL, "这不会是个鬼群吧，我怎么找不着她->_->");
        }
        UserGroup userGroup = this.userGroupService.getOne(new LambdaQueryWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, group)
                .eq(UserGroup::getUserId, userId));
        if (userGroup != null){
            if (userGroup.getStatus().equals(EnumUserGroupStatus.apply.value())){
                return result(EnumResultCode.FAIL, "喜欢这个群，就多点几下，说不定群管同意的更快哦");
            }else if (userGroup.getStatus().equals(EnumUserGroupStatus.agree.value())){
                return result(EnumResultCode.FAIL, "瞎点啥");
            }else {
                userGroup.setStatus(EnumUserGroupStatus.apply.value());
                this.userGroupService.updateById(userGroup);
                return result(EnumResultCode.SUCCESS);
            }
        }
        Role role = this.roleService.getOne(new LambdaQueryWrapper<Role>().eq(Role::getLev, EnumRoleLev.member.value()));
        userGroup = new UserGroup();
        userGroup.setStatus(EnumUserGroupStatus.apply.value());
        userGroup.setUserId(userId);
        userGroup.setGroupId(groupId);
        userGroup.setRoleId(role.getId());
        this.userGroupService.save(userGroup);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 管理员同意加群
     */
    @PostMapping(value = "/agree", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result agree(@RequestParam("userId") Long userId,
                        @RequestParam("userGroupId") Long userGroupId){
        UserGroup userGroup = this.userGroupService.getById(userGroupId);
        if (userGroup == null){
            return result(EnumResultCode.FAIL, "我有点害怕，最近总有人同意不存在的人加群");
        }
        UserGroup managerUserGroup = this.userGroupService.getOne(new LambdaQueryWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, userGroup.getGroupId())
                .eq(UserGroup::getUserId, userId));
        if (managerUserGroup == null){
            return result(EnumResultCode.FAIL, "你是谁？");
        }
        Role role = this.roleService.getById(managerUserGroup.getRoleId());
        if (role.getLev() > EnumRoleLev.manager.value()){
            return result(EnumResultCode.FAIL, "让你不好好混，这会儿连个管理员都不是吧");
        }
        userGroup.setStatus(EnumUserGroupStatus.agree.value());
        this.userGroupService.updateById(userGroup);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 退群
     */
    @PostMapping(value = "/refund", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result refund(@RequestParam("userId") Long userId,
                         @RequestParam("groupId") Long groupId,
                         @RequestParam(value = "nextOwnerUserId", required = false) Long nextOwnerUserId){
        UserGroup userGroup = this.userGroupService.getOne(new LambdaQueryWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, groupId)
                .eq(UserGroup::getUserId, userId));
        if (userGroup == null){
            return result(EnumResultCode.FAIL, "瞎闹");
        }
        Role role = this.roleService.getById(userGroup.getRoleId());
        boolean result = this.userGroupService.refund(userGroup, nextOwnerUserId, role.getLev());
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 我的群组
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result list(@RequestParam("userId") Long userId,
                       @RequestParam("status") Integer status){
        return result(this.userGroupService.getGroupByUserId(userId, status));

    }


}

