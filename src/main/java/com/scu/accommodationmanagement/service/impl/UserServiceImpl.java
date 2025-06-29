package com.scu.accommodationmanagement.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
        PageDTO<User> pageDTO = new PageDTO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userMapper.userPageList();
        Page<User> page = (Page<User>) userList;

        pageDTO.setTotal(page.getTotal());
        pageDTO.setItems(page.getResult());
        return pageDTO;
    }
}
