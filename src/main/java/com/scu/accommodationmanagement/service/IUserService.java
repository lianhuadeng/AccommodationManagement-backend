package com.scu.accommodationmanagement.service;

import com.scu.accommodationmanagement.model.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface IUserService extends IService<User> {

    void addStudent(User user);

    Long getDormitoryAdminIdByUserId(Long userId);
}
