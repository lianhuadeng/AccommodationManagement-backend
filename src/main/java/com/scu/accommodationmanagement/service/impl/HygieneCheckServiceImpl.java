package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.HygieneCheck;
import com.scu.accommodationmanagement.mapper.HygieneCheckMapper;
import com.scu.accommodationmanagement.service.IHygieneCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 卫生检查 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class HygieneCheckServiceImpl extends ServiceImpl<HygieneCheckMapper, HygieneCheck> implements IHygieneCheckService {
    @Autowired
    private HygieneCheckMapper hygieneCheckMapper;

    @Override
    public PageDTO<HygieneCheck> getPageList(Integer pageNum, Integer pageSize, Long roomId, String reason, LocalDateTime startTime, LocalDateTime endTime) {
        // 1. 构造 MP 分页对象
        Page<HygieneCheck> page = new Page<>(pageNum, pageSize);

        // 2. 调用自定义的 Mapper 方法
        IPage<HygieneCheck> resultPage = hygieneCheckMapper.pageList(page, roomId, reason, startTime, endTime);

        // 3. 封装 DTO 并返回
        PageDTO<HygieneCheck> dto = new PageDTO<>();
        dto.setTotal(resultPage.getTotal());
        dto.setItems(resultPage.getRecords());
        return dto;
    }
}
