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
 * 卫生检查
 * </p>
 *
 * @author scu
 * @since 2025-06-23
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
    @TableId(value = "hygiene_id", type = IdType.NONE)
    private Long hygieneId;

    /**
     * 宿舍管理员工号
     */
    private Long dormitoryId;

    /**
     * 房间号
     */
    private Long roomId;

    /**
     * 分数
     */
    private Long score;

    /**
     * 检查时间
     */
    private LocalDateTime checkTime;


}
