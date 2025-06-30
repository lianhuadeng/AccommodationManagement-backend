package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.mapper.BedMapper;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.mapper.UserMapper;
import com.scu.accommodationmanagement.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BedMapper bedMapper;

    @Override
    public void addStudent(User user) {
        userMapper.insert(user);
    }

    @Override
    public Long getDormitoryAdminIdByUserId(Long userId) {
        return userMapper.getDormitoryAdminIdByUserId(userId);
    }

    @Override
    public PageDTO<User> userPageList(Integer pageNum, Integer pageSize, String type) {
        // 1. 构造 MP 分页对象
        Page<User> page = new Page<>(pageNum, pageSize);

        // 2. 调用自定义的 Mapper 方法
        IPage<User> resultPage = userMapper.pageList(page, type);

        // 3. 封装 DTO 并返回
        PageDTO<User> dto = new PageDTO<>();
        dto.setTotal(resultPage.getTotal());
        dto.setItems(resultPage.getRecords());
        return dto;
    }

    @Override
    public Long getDormitoryAdminIdByBedId(Long bedId) {
        return bedMapper.getDormitoryAdminIdByBedId(bedId);
    }
}
