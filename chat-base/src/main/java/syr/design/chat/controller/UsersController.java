package syr.design.chat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import syr.design.chat.enums.EnumResultCode;
import syr.design.chat.enums.EnumWebSocketMessageType;
import syr.design.chat.message.RabbitPub;
import syr.design.chat.model.Result;
import syr.design.chat.model.Users;
import syr.design.chat.service.IUsersService;
import syr.design.chat.utils.GenUtils;
import syr.design.chat.utils.JJWTUtil;
import syr.design.chat.utils.RedisUtil;
import syr.design.chat.utils.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@RestController
@RequestMapping("/users")
public class UsersController extends BaseController {

    @Resource
    private IUsersService usersService;

    @Resource
    private JJWTUtil jjwtUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RabbitPub rabbitPub;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password){
        Users users = this.usersService.selectByUserName(username);
        if (users == null){
            return result(EnumResultCode.FAIL, "咦，我怎么找不到这个用户");
        }
        if (!users.getPassword().equals(StringUtils.md5hash(password))){
            return result(EnumResultCode.FAIL, "哦，密码不对，是不是忘了，对了，没有找回密码的接口哦");
        }
        String token = this.jjwtUtil.generateToken(users.getId().toString());
        this.redisUtil.setObject(users.getId().toString() + "user", users, 3000);
        return result(token);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        Users users = this.usersService.selectByUserName(username);
        if (users != null){
            return result(EnumResultCode.FAIL, "这个用户名很抢手呀，被别人捷足先登了啊");
        }
        users = new Users();
        users.setUsername(username);
        users.setPassword(StringUtils.md5hash(password));
        this.usersService.save(users);
        return result(EnumResultCode.SUCCESS);
    }

    @GetMapping(value = "/ajax", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result ajax(){
        return result(GenUtils.getSocketMessage("xitong", 0L, "123", EnumWebSocketMessageType.dispay.value(), "shangyouren", null));
    }

    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Result search(@RequestParam("keywords") String keywords){
        return result(this.usersService.list(new LambdaQueryWrapper<Users>().like(Users::getUsername, keywords)));
    }

}

