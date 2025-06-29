package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.mapper.RoomMapper;
import com.scu.accommodationmanagement.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 房间 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> getRoomsByBuilding(Long buildingId) {
        return roomMapper.getRoomsByBuilding(buildingId);
    }

    @Override
    public List<Room> getlist(Long parkId, Long buildingId, Long floor) {
        return roomMapper.getlist(parkId, buildingId, floor);
    }
}
