package com.scu.accommodationmanagement.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scu.accommodationmanagement.model.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
public interface UserMapper extends BaseMapper<User> {


    Long getDormitoryAdminIdByUserId(Long userId);

    List<User> userPageList();

    IPage<User> pageList(Page<User> page, String type);
}
