package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.Groups;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
public interface IGroupService extends IService<Groups> {

    boolean add(Groups group, Long userId);

    boolean del(Groups group);
}
