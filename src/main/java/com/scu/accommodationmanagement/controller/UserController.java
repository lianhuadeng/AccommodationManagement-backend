package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IUserService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import com.scu.accommodationmanagement.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

    @PostMapping("/login")
    public JsonResponse login(@RequestParam Long userId, @RequestParam String password) {


    }
    @PostMapping("/addStudent")
    public JsonResponse addStudent(@RequestParam Long userId,
                                   @RequestParam String name,
                                   @RequestParam String college,
                                   @RequestParam String major,
                                   @RequestParam Long grade,
                                   @RequestParam Long clazz,
                                   @RequestParam String contact) {
        User byId = userService.getById(userId);
        if (byId != null) {
            return JsonResponse.failure("学生已添加");
        }
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setCollege(college);
        user.setMajor(major);
        user.setGrade(grade);
        user.setContact(contact);
        user.setClazz(clazz);
        user.setType("学生");
        user.setPassword(Md5Util.getMD5String(user.getUserId().toString()
                .substring(user.getUserId().toString().length() - 6)));
        userService.addStudent(user);
        return JsonResponse.successMessage("success");

    }

}
