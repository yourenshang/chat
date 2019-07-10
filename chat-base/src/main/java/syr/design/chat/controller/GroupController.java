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
 * @author syr
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/group")
public class GroupController extends BaseController{

    @Resource
    private IGroupService groupService;

    @Resource
    private IUserGroupService userGroupService;

    @Resource
    private IRoleService roleService;

    /**
     * 创建群
     */
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result create(@RequestParam("userId") Long userId,
                         @RequestParam("groupName") String groupName){
        if (groupName == null || "".equals(groupName.trim())){
            return result(EnumResultCode.FAIL, "没有群组名，您是想让我给您起么");
        }
        Groups group = this.groupService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getGroupName, groupName));
        if (group != null){
            return result(EnumResultCode.FAIL, "这个名好抢手的啊，换一个吧，您来晚了呢");
        }
        group = new Groups();
        group.setGroupName(groupName);
        boolean result = this.groupService.add(group, userId);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 删除群组
     */
    @PostMapping(value = "/del", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result delete(@RequestParam("userId") Long userId,
                         @RequestParam("groupId") Long groupId){
        Groups group = this.groupService.getById(groupId);
        if (group == null){
            return result(EnumResultCode.FAIL, "死鬼是不可能再死一次的，群组也是一样，没有的群组不可能被删除");
        }
        UserGroup userGroup = this.userGroupService.getOne(new LambdaQueryWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, groupId)
                .eq(UserGroup::getUserId, userId)
                .eq(UserGroup::getStatus, EnumUserGroupStatus.agree.value()));
        if (userGroup == null){
            return result("您还不是这个群组的成员呢，想要删除这个群组请联系超管陈卓同志哦（17640150504）");
        }
        Role role = this.roleService.getById(userGroup.getRoleId());
        if (role == null || !role.getLev().equals(EnumRoleLev.owner.value())){
            return result(EnumResultCode.FAIL, "您没有删除这个群的权限呢");
        }
        boolean result = this.groupService.del(group);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 修改群组名
     */
    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result edit(@RequestParam("userId") Long userId,
                       @RequestParam("groupId") Long groupId,
                       @RequestParam("groupName") String groupName){
        if (groupName == null || "".equals(groupName.trim())){
            return result(EnumResultCode.FAIL, "没有群组名，您是想让我给您起么");
        }
        Groups group = this.groupService.getById(groupId);
        if (group == null){
            return result(EnumResultCode.FAIL, "您这群还没见着呢，就想着改名了");
        }
        UserGroup userGroup = this.userGroupService.getOne(new LambdaQueryWrapper<UserGroup>()
                .eq(UserGroup::getGroupId, groupId)
                .eq(UserGroup::getUserId, userId)
                .eq(UserGroup::getStatus, EnumUserGroupStatus.agree.value()));
        if (userGroup == null){
            return result(EnumResultCode.FAIL, "群名给他改了，让他们抓瞎，您这活干的，没毛病");
        }
        Role role = this.roleService.getById(userGroup.getRoleId());
        if (role == null || role.getLev() > EnumRoleLev.manager.value()){
            return result(EnumResultCode.FAIL ,"群名一改，只有我知道，是不是，但是对不住，您得先干到管理员啊");
        }
        group.setGroupName(groupName);
        this.groupService.updateById(group);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 搜索群组
     */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result search(@RequestParam("keywords") String keywords){
        return result(this.groupService.list(new LambdaQueryWrapper<Groups>().like(Groups::getGroupName, keywords)));
    }
}

