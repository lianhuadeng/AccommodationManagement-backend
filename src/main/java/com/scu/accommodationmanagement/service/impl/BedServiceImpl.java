package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.service.IBedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 床位 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class BedServiceImpl extends ServiceImpl<BedMapper, Bed> implements IBedService {
    @Autowired
    private BedMapper bedMapper;
    @Override
    public String getLocationByUserId(Long userId) {
        return bedMapper.getLocationByUserId(userId);
    }

    @Override
    public Bed getByUserId(Long applierId) {
        return bedMapper.selectOne(new QueryWrapper<Bed>().eq("user_id", applierId));
    }
}
