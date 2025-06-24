package com.scu.accommodationmanagement.mapper;

import com.scu.accommodationmanagement.model.po.Application;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 住宿调整申请 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface ApplicationMapper extends BaseMapper<Application> {

    List<Application> pageList(String studentId, String applicationType, String status, LocalDateTime startTime, LocalDateTime endTime);
}
