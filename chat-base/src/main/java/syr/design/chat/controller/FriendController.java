package syr.design.chat.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import syr.design.chat.enums.EnumFriendStatus;
import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.model.Friend;
import syr.design.chat.model.Result;
import syr.design.chat.model.Users;
import syr.design.chat.service.IFriendService;
import syr.design.chat.service.IUsersService;
import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Resource
    private IUsersService usersService;

    @Resource
    private IFriendService friendService;

    /**
     * 申请添加朋友
     */
    @PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Result applyFriend(@RequestParam("userId") Long userId,
                               @RequestParam("friendUserId") Long friendUserId) {
        Users friendUser = this.usersService.getById(friendUserId);
        Users users = this.usersService.getById(userId);
        if (friendUser == null) {
            return result(EnumResultCode.FAIL, "没有这个用户呢，您是寂寞了么，不如找我们的陈卓帅哥聊一聊（17640150504）");
        }
        Friend friend = this.friendService.findFriend(userId, friendUserId);
        if (friend == null) {
            friend = new Friend();
            friend.setFromUserId(userId);
            friend.setToUserId(friendUserId);
            friend.setStatus(EnumFriendStatus.apply.value());
            friend.setFromUserName(users.getUsername());
            friend.setToUserName(friendUser.getUsername());
            this.friendService.save(friend);
            return result(EnumResultCode.SUCCESS);
        } else {
            if (friend.getStatus().equals(EnumFriendStatus.agree.value())) {
                return result(EnumResultCode.FAIL, "这么喜欢他（她）么，您已经是他（她）的好友了呀");
            } else {
                if (friend.getFromUserId().equals(userId)) {
                    if (friend.getStatus().equals(EnumFriendStatus.apply.value())) {
                        return result(EnumResultCode.FAIL, "这么急切的想要添加他（她）么，不如坐下来喝杯茶等一等，奇迹属于你");
                    } else {
                        friend.setStatus(EnumFriendStatus.apply.value());
                        this.friendService.updateById(friend);
                        return result(EnumResultCode.SUCCESS);
                    }

                } else {
                    friend.setStatus(EnumFriendStatus.agree.value());
                    this.friendService.updateById(friend);
                    return result(EnumResultCode.SUCCESS);
                }
            }
        }
    }

    /**
     * 同意添加朋友
     */
    @PostMapping(value = "/agree", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result agreeFriend(@RequestParam("userId") Long userId,
                              @RequestParam("fromUserId") Long fromUserId){
        Friend friend = this.friendService.findFriend(userId, fromUserId);
        if (friend == null){
            return result(EnumResultCode.FAIL, "他（她）还没有申请呢，您也不要着急么，面包总会有的，爱情也会有的");
        }
        if (friend.getStatus().equals(EnumFriendStatus.agree.value())){
            return result(EnumResultCode.FAIL, "您是不是忘了，他（她）已经是您的朋友了");
        }
        if (friend.getFromUserId().equals(userId)){
            return result(EnumResultCode.FAIL, "这是您发起的申请，这么急切的想成为他（她）的好友么，还是耐心等待吧");
        }
        if (friend.getStatus().equals(EnumFriendStatus.refuse.value())){
            return result(EnumResultCode.FAIL, "您已经拒绝了他（她）的申请，这么讨厌一个人是不是不大合适");
        }
        friend.setStatus(EnumFriendStatus.agree.value());
        this.friendService.updateById(friend);
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 删除朋友、申请
     */
    @PostMapping(value = "/del", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result delete(@RequestParam("userId") Long userId,
                         @RequestParam("fromUserId") Long fromUserId){
        Friend friend = this.friendService.findFriend(userId, fromUserId);
        if (friend == null){
            return result(EnumResultCode.FAIL, "这么讨厌他（她），就是想删了他（她）是么");
        }
        this.friendService.removeById(friend.getId());
        return result(EnumResultCode.SUCCESS);
    }

    /**
     * 朋友、申请列表
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result list(@RequestParam("userId") Long userId,
                       @RequestParam(value = "status", required = false) Integer status){
        return result(this.friendService.findFriendList(userId, status));
    }

}

