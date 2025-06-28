package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.model.po.Repair;
import com.scu.accommodationmanagement.mapper.RepairMapper;
import com.scu.accommodationmanagement.service.IRepairService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 维修申请 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements IRepairService {
    @Autowired
    private RepairMapper repairMapper;


    @Override
    public List<Repair> userRepairs(Long userId, String status) {
        return repairMapper.userRepairs(userId, status);
    }

    @Override
    public List<Repair> maintenanceAdminRepairs(Long userId, String status) {
        return repairMapper.maintenanceAdminRepairs(userId, status);
    }

    @Override
    public List<Repair> dormitoryAdminRepairs(Long userId, String status) {
        return repairMapper.dormitoryAdminRepairs(userId, status);
    }
}
