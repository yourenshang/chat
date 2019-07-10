package syr.design.chat.model;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @author syr
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Friend extends Model<Friend> {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUserId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUserId;

    /**
     * 0 申请中 1 已同意 2 已拒绝
     */
    private Integer status;

    private LocalDateTime createdAt;

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
