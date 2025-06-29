package com.scu.accommodationmanagement.mapper;

import com.scu.accommodationmanagement.model.po.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 房间 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface RoomMapper extends BaseMapper<Room> {


    List<Room> getRoomsByBuilding(Long buildingId);

    List<Room> getlist(Long parkId, Long buildingId, Long floor);
}
