package com.scu.accommodationmanagement.mapper;

import com.scu.accommodationmanagement.model.po.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 维修申请 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface RepairMapper extends BaseMapper<Repair> {

    List<Repair> userRepairs(Long userId, String status);

    List<Repair> adminRepairs(Long userId, String status);
}
