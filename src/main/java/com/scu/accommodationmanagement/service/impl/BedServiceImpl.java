package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.mapper.RoomMapper;
import com.scu.accommodationmanagement.model.dto.BedListDTO;
import com.scu.accommodationmanagement.model.dto.LocationDTO;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.*;
import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.service.IBedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public String getLocationByBedId(Long targetBed) {
        return bedMapper.getLocationByBedId(targetBed);
    }

    @Override
    public PageDTO<BedListDTO> pageList(Integer pageNum, Integer pageSize, Long parkId, Long buildingId, Long floor, Long roomId) {
        // 1. 构造 MP 分页对象
        Page<BedListDTO> page = new Page<>(pageNum, pageSize);

        // 2. 调用自定义的 Mapper 方法
        IPage<BedListDTO> resultPage = bedMapper.pageList(page, parkId, buildingId, floor, roomId);

        // 3. 封装 DTO 并返回
        PageDTO<BedListDTO> dto = new PageDTO<>();
        dto.setTotal(resultPage.getTotal());
        dto.setItems(resultPage.getRecords());
        return dto;
    }

    @Override
    public LocationDTO getLocationByUserIdForApplication(Long userId) {
        return bedMapper.getLocationByUserIdForApplication(userId);
    }

    @Override
    public Long getDormitoryAdminIdByBedId(Long bedId) {
        return bedMapper.getDormitoryAdminIdByBedId(bedId);
    }

    @Override
    public void updateByIdForApplication(Bed currentBed) {
        bedMapper.updateByIdForApplication(currentBed);
    }

    @Override
    public void clearUser(Bed currentBed) {
        bedMapper.clearUser(currentBed);
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
                Bed bed = bedMapper.selectById(room.getRoomId() * 100 + i + 1);
                bed.setUserId(student.getUserId());

                try {
                    bedMapper.updateById(bed);
                } catch (Exception e) {
                    throw new RuntimeException("学号为：" + student.getUserId() + " 的学生已经安排过宿舍");
                }
            }

            if (studentIndex >= students.size()) break;
        }
    }

}
