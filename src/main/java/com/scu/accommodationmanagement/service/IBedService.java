package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.dto.BedListDTO;
import com.scu.accommodationmanagement.model.dto.LocationDTO;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Bed;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 床位 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IBedService extends IService<Bed> {

    String getLocationByUserId(Long userId);

    Bed getByUserId(Long applierId);

    void batchAllocateBeds(Map<String, List<User>> groupedStudents, Building building);

    String getLocationByBedId(Long targetBed);


    PageDTO<BedListDTO> pageList(Integer pageNum, Integer pageSize, Long parkId, Long buildingId, Long floor, Long roomId);

    LocationDTO getLocationByUserIdForApplication(Long userId);

    Long getDormitoryAdminIdByBedId(Long bedId);
}
