package syr.design.chat.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cyan
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private Long toGroupId;

    /**
     * 1 发送给个人 2 发送给群
     */
    private Integer type;

    private String message;

    private LocalDateTime sendTime;

    /**
     * 0 正常
     */
    private Integer status;

    private LocalDateTime createdAt;

    /**
     * 1 文本消息  2 url消息
     */
    private Integer messageType;

    private String toUserName;

    private String fromUserName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }
}
