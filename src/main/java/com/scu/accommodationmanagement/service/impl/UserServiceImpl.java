package com.scu.accommodationmanagement.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.dto.PageDTO;
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

    @Override
    public void addStudent(User user) {
        userMapper.insert(user);
    }

    @Override
    public Long getDormitoryAdminIdByUserId(Long userId) {
        return userMapper.getDormitoryAdminIdByUserId(userId);
    }

    @Override
    public PageDTO<User> userPageList(Integer pageNum, Integer pageSize) {
        Page<User> mpPage = new Page<>(pageNum, pageSize);
        IPage<User> resultPage = userMapper.selectPage(mpPage, null);

        PageDTO<User> pageDTO = new PageDTO<>();
        pageDTO.setTotal(resultPage.getTotal());
        pageDTO.setItems(resultPage.getRecords());
        return pageDTO;
    }
}
