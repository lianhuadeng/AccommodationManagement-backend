package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.mapper.ParkMapper;
import com.scu.accommodationmanagement.mapper.RoomMapper;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.mapper.BuildingMapper;
import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.service.IBuildingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 楼栋 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements IBuildingService {
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private BedMapper bedMapper;


    @Override
    public void add(Long parkId,
                    Integer floorNum,
                    Integer roomNumPerFloor,
                    Integer bedNumPerRoom) {
        Park park = parkMapper.selectById(parkId);

        // 1. 更新园区楼栋计数
        int newBuildingCount = park.getBuildingNum() + 1;
        park.setBuildingNum(newBuildingCount);
        parkMapper.updateById(park);

        // 2. 生成Building
        String parkIdStr = String.format("%02d", parkId);
        String buildIdxStr = String.format("%02d", newBuildingCount);
        long buildingId = Long.parseLong(parkIdStr + buildIdxStr);

        Building building = new Building();
        building.setBuildingId(buildingId);
        building.setParkId(parkId);
        building.setDormitoryId(null);
        building.setFloorNum(floorNum);
        building.setRoomNum(roomNumPerFloor);
        this.save(building);

        // 3. 生成房间和床位（如果提供了床位参数）
        if (bedNumPerRoom != null) {
            for (int floor = 1; floor <= floorNum; floor++) {
                String floorStr = String.format("%02d", floor);

                for (int room = 1; room <= roomNumPerFloor; room++) {
                    String roomIdxStr = String.format("%02d", room);
                    long roomId = Long.parseLong(parkIdStr + buildIdxStr + floorStr + roomIdxStr);

                    // 创建Room
                    Room roomEntity = new Room();
                    roomEntity.setRoomId(roomId);
                    roomEntity.setBuildingId(buildingId);
                    roomEntity.setFloor(floor);
                    roomEntity.setBedNum(bedNumPerRoom);
                    roomMapper.insert(roomEntity);

                    // 创建Beds
                    for (int bed = 1; bed <= bedNumPerRoom; bed++) {
                        String bedIdxStr = String.format("%02d", bed);
                        long bedId = Long.parseLong(parkIdStr + buildIdxStr + floorStr + roomIdxStr + bedIdxStr);

                        Bed bedEntity = new Bed();
                        bedEntity.setBedId(bedId);
                        bedEntity.setRoomId(roomId);
                        bedEntity.setUserId(null);
                        bedMapper.insert(bedEntity);
                    }
                }
            }
        }
    }

    @Override
    public List<Building> listByParkId(Long parkId) {
        return buildingMapper.listByParkId(parkId);
    }

    @Override
    public List<Building> listByDormitoryId(Long dormitoryId) {return buildingMapper.listByDormitoryId(dormitoryId);}
}
