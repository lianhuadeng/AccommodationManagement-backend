package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.mapper.ApplicationMapper;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 住宿调整申请 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {
    @Autowired
    private ApplicationMapper applicationMapper;


    @Override
    public PageDTO<Application> pageList(Long studentId, String applicationType, String status, LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        // 1. 构造 MP 分页对象
        Page<Application> page = new Page<>(pageNum, pageSize);

        // 2. 调用自定义的 Mapper 方法
        IPage<Application> resultPage = applicationMapper.pageList(page, studentId, applicationType, status, startTime, endTime);

        // 3. 封装 DTO 并返回
        PageDTO<Application> dto = new PageDTO<>();
        dto.setTotal(resultPage.getTotal());
        dto.setItems(resultPage.getRecords());
        return dto;
    }

    @Override
    public Application getLatestByApplierId(Long userId) {
        return applicationMapper.getLatestByApplierId(userId);
    }

    @Override
    public Application getByTargetBed(Long targetBed) {
        return applicationMapper.getByTargetBed(targetBed);
    }

    @Override
    public List<Application> userApplications(Long userId, String status) {
        return applicationMapper.selectList(new QueryWrapper<Application>().eq("applier_id", userId).eq("status", status).eq("is_deleted", 0));
    }

    @Override
    public List<Application> adminApplications(Long userId, String status) {
        return applicationMapper.selectList(new QueryWrapper<Application>().eq("dormitory_id", userId).eq("status", status).eq("is_deleted", 0));
    }

    @Override
    public List<Application> getByApplierId(Long userId) {
        return applicationMapper.selectList(new QueryWrapper<Application>().eq("applier_id", userId));
    }

    @Override
    public List<Application> getByDormitoryId(Long userId) {
        return applicationMapper.selectList(
                new QueryWrapper<Application>()
                        .eq("dormitory_id", userId)
                        .eq("status", "已处理")
                        .eq("is_deleted", false));
    }

    @Override
    public List<Application> getByLeaderId(Long userId) {
        return applicationMapper.selectList(
                new QueryWrapper<Application>()
                        .eq("leader_id", userId)
                        .eq("status", "待处理")
                        .eq("is_deleted", false));
    }


}
