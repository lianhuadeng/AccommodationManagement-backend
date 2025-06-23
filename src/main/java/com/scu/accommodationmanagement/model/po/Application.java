package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2025-06-23
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
    @TableId(value = "application_id", type = IdType.NONE)
    private Long applicationId;

    /**
     * 申请人学号
     */
    private Long studentId;

    /**
     * 审核领导id
     */
    private Long leaderId;

    /**
     * 处理者宿舍管理员id
     */
    private Long dormitoryId;

    /**
     * 申请类型
     */
    private String applicationType;

    /**
     * 申请内容
     */
    private String content;

    /**
     * 申请状态
     */
    private String status;

    /**
     * 申请时间
     */
    private LocalDateTime applicationTime;

    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;

    /**
     * 处理时间
     */
    private LocalDateTime processTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;


}
