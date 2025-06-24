package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IUserService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import com.scu.accommodationmanagement.utils.JwtUtil;
import com.scu.accommodationmanagement.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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



    @PostMapping("/login")
    public JsonResponse login(@RequestParam Long userId, @RequestParam String password) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        User byId = userService.getById(userId);
        if (Md5Util.getMD5String(password).equals(byId.getPassword())) {
            // 登录成功
            // 生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userID", byId.getUserId());
            claims.put("userName", byId.getName());
            String token = JwtUtil.genToken(claims);

            // 把token存储到redis中
            operations.set(token, token, 1, TimeUnit.HOURS);
            if (byId.getType().equals("学生")){
                return JsonResponse.success(token,"student");
            }else if(byId.getType().equals("教师")){
                return JsonResponse.success(token,"teacher");
            }else if(byId.getType().equals("宿舍管理员")){
                return JsonResponse.success(token,"dormitory");
            }else if (byId.getType().equals("维系管理员")){
                return JsonResponse.success(token,"maintenance");
            }else {
                return JsonResponse.success(token,"system");
            }
        }
        return JsonResponse.failure("账号或密码错误");
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
