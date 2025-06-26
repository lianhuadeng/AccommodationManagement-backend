package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 房间 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IRoomService extends IService<Room> {

    List<Room> getRoomsByBuilding(Long buildingId);
}
