package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.mapper.BuildingMapper;
import com.scu.accommodationmanagement.mapper.RoomMapper;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.mapper.ParkMapper;
import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.service.IParkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 园区 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class ParkServiceImpl extends ServiceImpl<ParkMapper, Park> implements IParkService {
    @Autowired
    private ParkMapper parkMapper;
    @Autowired
    private BuildingMapper buildingMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private BedMapper bedMapper;

    //TODO: 用线程优化
    @Override
    public void add(String name, String type,
                    Integer buildingNum, Integer floorNum,
                    Integer roomNumPerFloor, Integer bedNumPerRoom) {

        // 1. 创建Park记录
        Park park = new Park();
        park.setName(name);
        park.setType(type);
        park.setBuildingNum(0); // 初始化为0，后面会更新
        parkMapper.insert(park);
        long parkId = park.getParkId();

        // 2. 检查参数是否完整
        if (buildingNum == null || floorNum == null ||
                roomNumPerFloor == null || bedNumPerRoom == null) {
            return; // 参数不全时，只添加park
        }


        // 2. 更新park表的building_num字段
        park.setBuildingNum(buildingNum);
        parkMapper.updateById(park);

        String parkIdStr = String.format("%02d", parkId);

        // 3. 生成Building、Room和Bed
        for (int bIdx = 1; bIdx <= buildingNum; bIdx++) {
            // 创建Building记录
            String buildIdxStr = String.format("%02d", bIdx);
            long buildingId = Long.parseLong(parkIdStr + buildIdxStr);

            Building building = new Building();
            building.setBuildingId(buildingId);
            building.setParkId(parkId);
            building.setDormitoryId(null);
            building.setFloorNum(floorNum);
            building.setRoomNum(roomNumPerFloor);
            buildingMapper.insert(building);

            // 生成房间
            for (int f = 1; f <= floorNum; f++) {
                String floorStr = String.format("%02d", f);

                for (int r = 1; r <= roomNumPerFloor; r++) {
                    // 创建Room记录
                    String roomIdxStr = String.format("%02d", r);
                    long roomId = Long.parseLong(parkIdStr + buildIdxStr + floorStr + roomIdxStr);

                    Room room = new Room();
                    room.setRoomId(roomId);
                    room.setBuildingId(buildingId);
                    room.setFloor(f); // 设置楼层
                    room.setBedNum(bedNumPerRoom);
                    roomMapper.insert(room);

                    // 生成床位
                    for (int bed = 1; bed <= bedNumPerRoom; bed++) {
                        // 创建Bed记录
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

}
