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
 * 园区
 * </p>
 *
 * @author scu
 * @since 2025-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("park")
public class Park implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 园区id
     */
    @TableId(value = "park_id", type = IdType.AUTO)
    private Long parkId;

    /**
     * 园区名
     */
    private String name;

    /**
     * 园区类型
     */
    private String type;

    /**
     * 园区内楼栋数量
     */
    private Integer buildingNum;


}
