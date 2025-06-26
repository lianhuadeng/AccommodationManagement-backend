package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.accommodationmanagement.mapper.RoomMapper;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IBedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scu.accommodationmanagement.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 床位 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService {
    @Autowired
    private BedMapper bedMapper;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public String getLocationByUserId(Long userId) {
        return bedMapper.getLocationByUserId(userId);
    }

    @Override
    public Bed getByUserId(Long applierId) {
        return bedMapper.selectOne(new QueryWrapper<Bed>().eq("user_id", applierId));
    }

    @Override
    public void batchAllocateBeds(Map<String, List<User>> groupedStudents, Building building) {
        // 1. 获取楼栋所有可用房间
        List<Room> rooms = roomMapper.getRoomsByBuilding(building.getBuildingId());

        // 2. 按分组进行分配
        groupedStudents.forEach((groupKey, studentList) -> {
            // 为每个分组分配连续的床位
            allocateGroupToRooms(studentList, rooms);
        });
    }

    public Integer getOccupiedBeds(Long roomId) {
        return bedMapper.getOccupiedBeds(roomId);
    }

    private void allocateGroupToRooms(List<User> students, List<Room> rooms) {
        int studentIndex = 0;

        for (Room room : rooms) {
            // 获取房间可用床位数
            int availableBeds = room.getBedNum() - getOccupiedBeds(room.getRoomId());

            // 为当前房间分配学生
            for (int i = 0; i < availableBeds && studentIndex < students.size(); i++) {
                User student = students.get(studentIndex++);

                // 创建床位分配记录
                // TODO: 床位用户ID修改
                Bed bed = bedMapper.selectById(room.getRoomId()*100 + i);
                bed.setRoomId(room.getRoomId());
                bed.setUserId(student.getUserId());

                bedMapper.updateById(bed);
            }

            if (studentIndex >= students.size()) break;
        }
    }

}
