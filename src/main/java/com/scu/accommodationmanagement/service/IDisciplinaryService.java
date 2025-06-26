package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Disciplinary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scu.accommodationmanagement.utils.JsonResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 违纪记录 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IDisciplinaryService extends IService<Disciplinary> {

    PageDTO<Disciplinary> getDisciplinaryList(Integer pageNum, Integer pageSize, String reason, LocalDateTime startTime, LocalDateTime endTime);
}
