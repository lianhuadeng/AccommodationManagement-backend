package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.HygieneCheck;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 卫生检查 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IHygieneCheckService extends IService<HygieneCheck> {

    PageDTO<HygieneCheck> getPageList(Integer pageNum, Integer pageSize, Long roomId, String reason, LocalDateTime startTime, LocalDateTime endTime);
}
