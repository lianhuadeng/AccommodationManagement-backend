package com.scu.accommodationmanagement.model.po;

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
 * 维修申请
 * </p>
 *
 * @author scu
 * @since 2025-06-25
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
    private Long studentId;

    /**
     * 宿舍管理员id
     */
    private Long dormitoryId;

    /**
     * 维修管理员id
     */
    private Long maintenanceId;

    /**
     * 维修状态
     */
    private String status;

    /**
     * 维修物品内容
     */
    private String repairItem;

    /**
     * 位置
     */
    private String location;

    /**
     * 报修内容
     */
    private String content;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    private String pictureUrl;

    private Boolean isDeleted;
}
