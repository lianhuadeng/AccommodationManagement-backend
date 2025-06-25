package com.scu.accommodationmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    public PageDTO<Application> pageList(String studentId, String applicationType, String status, LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        PageDTO<Application> pageDTO = new PageDTO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Application> foundItemList = applicationMapper.pageList(studentId, applicationType, status, startTime, endTime);
        Page<Application> page = (Page<Application>) foundItemList;

        pageDTO.setTotal(page.getTotal());
        pageDTO.setItems(page.getResult());
        return pageDTO;
    }

    @Override
    public List<Application> myApplication(Long userId) {
        return applicationMapper.myApplication(userId);
    }

    @Override
    public Application getLatestByApplierId(Long userId) {
        return applicationMapper.getLatestByApplierId(userId);
    }

}
