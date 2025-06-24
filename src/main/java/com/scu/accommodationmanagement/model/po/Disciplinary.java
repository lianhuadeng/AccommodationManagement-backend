package com.scu.accommodationmanagement.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
/**
 * <p>
 * 违纪记录
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("disciplinary")
public class Disciplinary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 违纪id
     */
    @TableId(value = "disciplinary_id", type = IdType.AUTO)
    private Long disciplinaryId;

    /**
     * 扣分领导id
     */
    @TableField("leader_id")
    private Long leaderId;

    /**
     * 学号
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 宿舍管理员id
     */
    @TableField("dormitory_id")
    private Long dormitoryId;

    /**
     * 违纪原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 处罚分数
     */
    @TableField("score")
    private Long score;


}
