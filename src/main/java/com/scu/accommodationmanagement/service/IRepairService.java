package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.Repair;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 维修申请 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IRepairService extends IService<Repair> {

    List<Repair> getByUserId(Long userId);
}
