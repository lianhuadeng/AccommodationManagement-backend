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
 * 违纪记录
 * </p>
 *
 * @author scu
 * @since 2025-06-23
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
    @TableId(value = "disciplinary_id", type = IdType.NONE)
    private Long disciplinaryId;

    /**
     * 扣分领导id
     */
    private Long leaderId;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 宿舍管理员id
     */
    private Long dormitoryId;

    /**
     * 违纪原因
     */
    private String reason;

    /**
     * 处罚分数
     */
    private Long score;


}
