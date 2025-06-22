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
 * 教师
 * </p>
 *
 * @author author
 * @since 2025-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师工号
     */
    @TableId(value = "teacher_id", type = IdType.AUTO)
    private Long teacherId;

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
     * 职称
     */
    @TableField("title")
    private String title;

    /**
     * 学院
     */
    @TableField("college")
    private String college;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 联系方式
     */
    @TableField("contact")
    private String contact;


}
