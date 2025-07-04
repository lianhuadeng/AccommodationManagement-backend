package com.scu.accommodationmanagement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.vo.BedListVO;
import com.scu.accommodationmanagement.model.vo.LocationVO;
import com.scu.accommodationmanagement.model.po.Bed;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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


    IPage<BedListVO> pageList(Page<BedListVO> page, Long parkId, Long buildingId, Long floor, Long roomId);

    LocationVO getLocationByUserIdForApplication(Long userId);

    Long getDormitoryAdminIdByBedId(Long bedId);

    void updateByIdForApplication(Bed currentBed);

    void clearUser(Bed currentBed);
}
