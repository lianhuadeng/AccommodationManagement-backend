package com.scu.accommodationmanagement.mapper;

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


}
