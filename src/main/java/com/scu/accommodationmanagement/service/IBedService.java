package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.Bed;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 床位 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IBedService extends IService<Bed> {

    String getLocationByUserId(Long userId);
}
