package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 住宿调整申请
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("application")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 住宿调整申请id
     */
    @TableId(value = "application_id", type = IdType.AUTO)
    private Long applicationId;

    /**
     * 申请人学号
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 审核领导id
     */
    @TableField("leader_id")
    private Long leaderId;

    /**
     * 处理者宿舍管理员id
     */
    @TableField("dormitory_id")
    private Long dormitoryId;

    /**
     * 申请类型
     */
    @TableField("application_type")
    private String applicationType;

    /**
     * 申请内容
     */
    @TableField("content")
    private String content;

    /**
     * 申请状态
     */
    @TableField("status")
    private String status;

    /**
     * 申请时间
     */
    @TableField("application_time")
    private LocalDateTime applicationTime;

    /**
     * 审核时间
     */
    @TableField("review_time")
    private LocalDateTime reviewTime;

    /**
     * 处理时间
     */
    @TableField("process_time")
    private LocalDateTime processTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
