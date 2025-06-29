package com.scu.accommodationmanagement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    IPage<HygieneCheck> pageList(Page<HygieneCheck> page, Long roomId, String reason, LocalDateTime startTime, LocalDateTime endTime);

}
