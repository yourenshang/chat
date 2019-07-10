package syr.design.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import syr.design.chat.model.Groups;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author syr
 * @since 2019-04-28
 */
public interface IGroupService extends IService<Groups> {

    boolean add(Groups group, Long userId);

    boolean del(Groups group);

}
