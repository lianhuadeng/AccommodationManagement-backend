package com.scu.accommodationmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        PageDTO<Disciplinary> pageDTO = new PageDTO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Disciplinary> disciplinaryList = disciplinaryMapper.getDisciplinaryList(reason, startTime, endTime);
        Page<Disciplinary> page = (Page<Disciplinary>) disciplinaryList;

        pageDTO.setTotal(page.getTotal());
        pageDTO.setItems(page.getResult());
        return pageDTO;
    }
}
