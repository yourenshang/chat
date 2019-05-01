package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import syr.design.chat.mapper.UsersMapper;
import syr.design.chat.model.Users;
import syr.design.chat.service.IUsersService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {


    @Override
    public Users selectByUserName(String username) {
        return this.getOne(new LambdaQueryWrapper<Users>().eq(Users::getUsername, username));
    }

    @Override
    public List<Users> findByGroupId(Long toGroupId) {
        return this.baseMapper.findByGroupId(toGroupId);
    }
}
