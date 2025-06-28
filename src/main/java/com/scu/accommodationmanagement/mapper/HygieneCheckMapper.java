package com.scu.accommodationmanagement.mapper;

import com.scu.accommodationmanagement.model.po.HygieneCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 卫生检查 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface HygieneCheckMapper extends BaseMapper<HygieneCheck> {

    List<HygieneCheck> getPageList(Long roomId, String reason, LocalDateTime startTime, LocalDateTime endTime);
}
