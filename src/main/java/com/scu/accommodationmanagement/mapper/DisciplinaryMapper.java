package com.scu.accommodationmanagement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.po.Disciplinary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 违纪记录 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface DisciplinaryMapper extends BaseMapper<Disciplinary> {

    IPage<Disciplinary> pageList(Page<Disciplinary> page, String reason, LocalDateTime startTime, LocalDateTime endTime);
}
