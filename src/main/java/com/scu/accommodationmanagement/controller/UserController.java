package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.dto.UserInfoDTO;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.model.vo.LoginVO;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.service.IUserService;
import com.scu.accommodationmanagement.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.scu.accommodationmanagement.utils.CurrentUserUtil.getCurrentUser;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IBedService bedService;

    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginVO loginVO) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        User byId = userService.getById(loginVO.getUserId());
        if (Md5Util.getMD5String(loginVO.getPassword()).equals(byId.getPassword())) {
            // 登录成功
            // 生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", byId.getUserId());
            claims.put("name", byId.getName());
            String token = JwtUtil.genToken(claims);

            // 把token存储到redis中
            operations.set(token, token, 1, TimeUnit.HOURS);
            return JsonResponse.success(token, byId.getType());
        }

        return JsonResponse.failure("账号或密码错误");
    }

    @PostMapping("/systemAdmin/addStudent")
    public JsonResponse addStudent(@RequestBody User user){
        // 判断是否具有权限
        User currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.getType().equals("系统管理员")) {
            return JsonResponse.failure("无权限操作！");
        }
        User byId = userService.getById(user.getUserId());
        if (byId != null) {
            return JsonResponse.failure("学生已存在！");
        }
        user.setType("学生");
        user.setPassword(Md5Util.getMD5String(user.getUserId().toString()
                .substring(user.getUserId().toString().length() - 6)));
        userService.addStudent(user);
        return JsonResponse.successMessage("添加成功！");

    }

    @PostMapping("/systemAdmin/addAdmin")
    public JsonResponse addAdmin(@RequestBody User user, @RequestParam String type){
        User currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.getType().equals("系统管理员")) {
            return JsonResponse.failure("无权限操作！");
        }
        User byId = userService.getById(user.getUserId());
        if (byId != null) {
            return JsonResponse.failure("用户已存在！");
        }
        user.setType(type);
        user.setPassword(Md5Util.getMD5String(user.getUserId().toString()
                .substring(user.getUserId().toString().length() - 6)));
        userService.save(user);
        return JsonResponse.successMessage("添加成功！");
    }

    @GetMapping("/userInfo")
    public JsonResponse getUserInfo() {
        User user = getCurrentUser();
        if (user != null) {
            String location = bedService.getLocationByUserId(user.getUserId());
            UserInfoDTO userInfoDTO = new UserInfoDTO(
                    user.getName(),
                    user.getUserId(),
                    user.getContact(),
                    location
            );
            return JsonResponse.success(userInfoDTO);
        }else {
            return JsonResponse.failure("error");
        }
    }

    @PostMapping("/systemAdmin/setAdminType")
    public JsonResponse setPermission(@RequestParam Long userId, @RequestParam String adminType){
        // Permission: 宿舍管理员，维修管理员，分管领导
        User currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.getType().equals("系统管理员")) {
            return JsonResponse.failure("无权限操作！");
        }
        User byId = userService.getById(userId);
        if (byId == null) {
            return JsonResponse.failure("用户不存在！");
        }
        byId.setType(adminType);
        userService.updateById(byId);
        return JsonResponse.successMessage("设置成功！");
    }

//    @PostMapping("/systemAdmin/userPageList")
//    public JsonResponse userPageList(
//            Integer pageNum,
//            Integer pageSize,
//            @RequestParam(required = false) String userId,
//            @RequestParam(required = false) String sex,
//            @RequestParam(required = false) String type,
//    ){
//
//    }


}
