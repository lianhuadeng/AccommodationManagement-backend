package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @author author
 * @since 2025-06-22
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
    @TableId(value = "leader_id", type = IdType.AUTO)
    private Long leaderId;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 联系方式
     */
    @TableField("contact")
    private String contact;


}
