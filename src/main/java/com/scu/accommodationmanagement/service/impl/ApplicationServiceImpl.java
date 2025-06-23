package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.mapper.ApplicationMapper;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 住宿调整申请 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

}
