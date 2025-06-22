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
 * 宿舍管理员
 * </p>
 *
 * @author author
 * @since 2025-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dormitory_admin")
public class DormitoryAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 宿舍管理员工号
     */
    @TableId(value = "dormitory_id", type = IdType.AUTO)
    private Long dormitoryId;

    /**
     * 园区id
     */
    @TableField("park_id")
    private Long parkId;

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
