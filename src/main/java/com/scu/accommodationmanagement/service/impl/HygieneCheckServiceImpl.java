package com.scu.accommodationmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        PageDTO<HygieneCheck> pageDTO = new PageDTO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<HygieneCheck> hygieneCheckList = hygieneCheckMapper.getPageList(roomId, reason, startTime, endTime);
        Page<HygieneCheck> page = (Page<HygieneCheck>) hygieneCheckList;

        pageDTO.setTotal(page.getTotal());
        pageDTO.setItems(page.getResult());
        return pageDTO;
    }
}
