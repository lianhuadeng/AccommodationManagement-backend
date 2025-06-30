package com.scu.accommodationmanagement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.dto.BedListDTO;
import com.scu.accommodationmanagement.model.dto.LocationDTO;
import com.scu.accommodationmanagement.model.po.Bed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 床位 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface BedMapper extends BaseMapper<Bed> {
    String getLocationByUserId(Long userId);


    Integer getOccupiedBeds(Long roomId);

    String getLocationByBedId(Long targetBed);


    IPage<BedListDTO> pageList(Page<BedListDTO> page, Long parkId, Long buildingId, Long floor, Long roomId);

    LocationDTO getLocationByUserIdForApplication(Long userId);
}
