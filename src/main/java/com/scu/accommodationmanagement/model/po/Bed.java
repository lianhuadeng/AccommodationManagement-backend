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
 * 床位
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bed")
public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 床位号
     */
    @TableId(value = "bed_id", type = IdType.NONE)
    private Long bedId;

    /**
     * 房间号
     */
    @TableField("room_id")
    private Long roomId;

    /**
     * 学号或工号
     */
    @TableField("user_id")
    private Long userId;


}
