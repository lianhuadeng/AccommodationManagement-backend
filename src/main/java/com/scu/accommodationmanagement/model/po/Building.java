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
 * 楼栋
 * </p>
 *
 * @author scu
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("building")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼栋id
     */
    @TableId(value = "building_id", type = IdType.NONE)
    private Long buildingId;

    /**
     * 园区id
     */
    private Long parkId;

    /**
     * 宿管工号
     */
    private Long dormitoryId;

    /**
     * 楼栋楼层数
     */
    private Integer floorNum;

    /**
     * 每层房间数
     */
    private Integer roomNum;


}
