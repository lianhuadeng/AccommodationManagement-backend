package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 住宿调整申请 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IApplicationService extends IService<Application> {

    PageDTO<Application> pageList(String studentId, String applicationType, String status, LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    List<Application> myApplication(Long userId);

    Application getLatestByApplierId(Long userId);
}
