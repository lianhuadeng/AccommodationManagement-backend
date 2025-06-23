package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.mapper.UserMapper;
import com.scu.accommodationmanagement.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
