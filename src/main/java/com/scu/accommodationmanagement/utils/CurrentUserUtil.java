package com.scu.accommodationmanagement.utils;

import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CurrentUserUtil {
    private static IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        CurrentUserUtil.userService = userService;
    }

    public static User getCurrentUser() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long userID = (Long) map.get("userId");
        if (userID == null) {
            throw new RuntimeException("用户未登录");
        }
        return userService.getById(userID);
    }
}