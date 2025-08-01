package com.scu.accommodationmanagement.mapper;

import com.scu.accommodationmanagement.model.po.Building;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 楼栋 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface BuildingMapper extends BaseMapper<Building> {

    List<Building> listByParkId(Long parkId);

    List<Building> listByDormitoryId(Long dormitoryId);
}
