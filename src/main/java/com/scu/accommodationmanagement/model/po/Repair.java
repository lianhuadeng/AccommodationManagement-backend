package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
/**
 * <p>
 * 维修申请
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("repair")
public class Repair implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 维修申请id
     */
    @TableId(value = "repair_id", type = IdType.AUTO)
    private Long repairId;

    /**
     * 申请人学生id
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 宿舍管理员id
     */
    @TableField("dormitory_id")
    private Long dormitoryId;

    /**
     * 维修管理员id
     */
    @TableField("maintenance_id")
    private Long maintenanceId;

    /**
     * 位置
     */
    @TableField("location")
    private String location;

    /**
     * 报修内容
     */
    @TableField("content")
    private String content;

    /**
     * 申请时间
     */
    @TableField("apply_time")
    private LocalDateTime applyTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
