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
 * 卫生检查
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("hygiene_check")
public class HygieneCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 卫生检查id
     */
    @TableId(value = "hygiene_id", type = IdType.AUTO)
    private Long hygieneId;

    /**
     * 宿舍管理员工号
     */
    @TableField("dormitory_id")
    private Long dormitoryId;

    /**
     * 房间号
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 分数
     */
    @TableField("score")
    private Long score;

    /**
     * 检查时间
     */
    @TableField("check_time")
    private LocalDateTime checkTime;


}
