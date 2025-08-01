package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.Building;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 楼栋 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IBuildingService extends IService<Building> {

    void add(Long parkId, Integer floorNum, Integer roomNumPerFloor, Integer bedNumPerRoom);

    List<Building> listByParkId(Long parkId);

    List<Building> listByDormitoryId(Long dormitoryId);

    Building getByDormitoryId(Long userId);

    List<Building> getUnManagedBuilding();

}
