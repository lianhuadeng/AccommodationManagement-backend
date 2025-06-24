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
 * 房间
 * </p>
 *
 * @author scu
 * @since 2025-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    @TableId(value = "room_id", type = IdType.NONE)
    private Long roomId;

    /**
     * 楼栋id
     */
    @TableField("building_id")
    private Long buildingId;

    /**
     * 楼层
     */
    @TableField("floor")
    private Integer floor;

    /**
     * 房间床位数
     */
    @TableField("bed_num")
    private Integer bedNum;


}
