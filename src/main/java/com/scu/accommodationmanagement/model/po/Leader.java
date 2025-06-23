package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分管领导
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("leader")
public class Leader implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分管领导工号
     */
    @TableId(value = "leader_id", type = IdType.NONE)
    private Long leaderId;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String contact;


}
