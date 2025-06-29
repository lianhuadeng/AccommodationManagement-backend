package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Disciplinary;
import com.scu.accommodationmanagement.mapper.DisciplinaryMapper;
import com.scu.accommodationmanagement.service.IDisciplinaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 违纪记录 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class DisciplinaryServiceImpl extends ServiceImpl<DisciplinaryMapper, Disciplinary> implements IDisciplinaryService {
    @Autowired
    private DisciplinaryMapper disciplinaryMapper;
    @Override
    public PageDTO<Disciplinary> getDisciplinaryList(Integer pageNum, Integer pageSize, String reason, LocalDateTime startTime, LocalDateTime endTime) {
        // 1. 构造 MP 分页对象
        Page<Disciplinary> page = new Page<>(pageNum, pageSize);

        // 2. 调用自定义的 Mapper 方法
        IPage<Disciplinary> resultPage = disciplinaryMapper.pageList(page, reason, startTime, endTime);

        // 3. 封装 DTO 并返回
        PageDTO<Disciplinary> dto = new PageDTO<>();
        dto.setTotal(resultPage.getTotal());
        dto.setItems(resultPage.getRecords());
        return dto;
    }
}
