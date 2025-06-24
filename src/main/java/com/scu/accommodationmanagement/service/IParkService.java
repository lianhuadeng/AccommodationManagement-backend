package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.Park;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 园区 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IParkService extends IService<Park> {

    void add(String name, String type, Integer buildingNum, Integer floorNum, Integer roomNumPerFloor, Integer bedNumPerRoom);

}
