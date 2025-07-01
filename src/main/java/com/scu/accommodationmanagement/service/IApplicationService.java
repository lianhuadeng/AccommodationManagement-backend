package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.accommodationmanagement.utils.JsonResponse;

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

    PageDTO<Application> pageList(Long studentId, String applicationType, String status, LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    Application getLatestByApplierId(Long userId);

    Application getByTargetBed(Long targetBed);

    List<Application> userApplications(Long userId, String status);

    List<Application> adminApplications(Long userId, String status);

    List<Application> getByApplierId(Long userId);

    List<Application> getByDormitoryId(Long userId);

    List<Application> getByLeaderId(Long userId);

    List<Application> getToBeReviewedApplication(Long userId);

}
