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
import org.springframework.transaction.annotation.Propagation;
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

    // 创建固定大小线程池
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 64
    );

    @Override
    public void add(String name, String type,
                    Integer buildingNum, Integer floorNum,
                    Integer roomNumPerFloor, Integer bedNumPerRoom) {

        // 1. 创建Park记录
        Park park = new Park();
        park.setName(name);
        park.setType(type);
        park.setBuildingNum(0);
        parkMapper.insert(park);
        long parkId = park.getParkId();
        String parkIdStr = String.format("%02d", parkId);

        // 2. 参数不全时提前返回
        if (buildingNum == null || floorNum == null ||
                roomNumPerFloor == null || bedNumPerRoom == null) {
            return;
        }

        // 3. 并行创建楼栋
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int bIdx = 1; bIdx <= buildingNum; bIdx++) {
            final int buildingIndex = bIdx;
            futures.add(
                    CompletableFuture.runAsync(() ->
                                    generateBuilding(
                                            parkId, parkIdStr, buildingIndex,
                                            floorNum, roomNumPerFloor, bedNumPerRoom
                                    ),
                            threadPool
                    )
            );
        }

        // 4. 等待所有楼栋创建完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 5. 更新park的实际楼栋数
        park.setBuildingNum(buildingNum);
        parkMapper.updateById(park);
    }

    // 生成单个楼栋（包含楼层/房间/床位）
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 独立事务
    public void generateBuilding(long parkId, String parkIdStr, int bIdx,
                                 int floorNum, int roomNumPerFloor, int bedNumPerRoom) {
        // 楼栋ID生成
        String buildIdxStr = String.format("%02d", bIdx);
        long buildingId = Long.parseLong(parkIdStr + buildIdxStr);

        // 插入楼栋记录
        Building building = new Building();
        building.setBuildingId(buildingId);
        building.setParkId(parkId);
        building.setFloorNum(floorNum);
        building.setRoomNum(roomNumPerFloor);
        buildingMapper.insert(building);

        // 生成房间和床位（同步操作）
        for (int f = 1; f <= floorNum; f++) {
            String floorStr = String.format("%02d", f);
            for (int r = 1; r <= roomNumPerFloor; r++) {
                // 插入房间记录
                String roomIdxStr = String.format("%02d", r);
                long roomId = Long.parseLong(parkIdStr + buildIdxStr + floorStr + roomIdxStr);
                Room room = new Room();
                room.setRoomId(roomId);
                room.setBuildingId(buildingId);
                room.setFloor(f);
                room.setBedNum(bedNumPerRoom);
                roomMapper.insert(room);

                // 插入床位记录
                for (int bed = 1; bed <= bedNumPerRoom; bed++) {
                    String bedIdxStr = String.format("%02d", bed);
                    long bedId = Long.parseLong(parkIdStr + buildIdxStr + floorStr + roomIdxStr + bedIdxStr);
                    Bed bedEntity = new Bed();
                    bedEntity.setBedId(bedId);
                    bedEntity.setRoomId(roomId);
                    bedMapper.insert(bedEntity);
                }
            }
        }
    }
}
