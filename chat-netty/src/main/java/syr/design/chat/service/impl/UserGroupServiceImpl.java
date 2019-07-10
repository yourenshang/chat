package syr.design.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syr.design.chat.mapper.NettyUserGroupMapper;
import syr.design.chat.model.UserGroup;
import syr.design.chat.service.IUserGroupService;

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
public class UserGroupServiceImpl extends ServiceImpl<NettyUserGroupMapper, UserGroup> implements IUserGroupService {

}
