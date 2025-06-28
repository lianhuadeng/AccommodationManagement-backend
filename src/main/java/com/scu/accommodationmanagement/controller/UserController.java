package com.scu.accommodationmanagement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scu.accommodationmanagement.model.dto.UserInfoDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.model.vo.ChangePasswordVO;
import com.scu.accommodationmanagement.model.vo.LoginVO;
import com.scu.accommodationmanagement.service.FileService;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.service.IUserService;
import com.scu.accommodationmanagement.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Autowired
    private FileService fileService;


    @PostMapping("/login")
    public JsonResponse login(@RequestBody LoginVO loginVO) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        User byId = userService.getById(loginVO.getUserId());
        if (byId == null) {
            return JsonResponse.failure("账号或密码错误");
        }
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

    @PostMapping("/updateContact")
    public JsonResponse updateContact(@RequestParam String contact) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return JsonResponse.failure("请先登录！");
        }
        currentUser.setContact(contact);
        userService.updateById(currentUser);
        return JsonResponse.successMessage("联系方式修改成功！");
    }

    @PostMapping("/changePassword")
    public JsonResponse changePassword(@RequestBody ChangePasswordVO changePasswordVO) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return JsonResponse.failure("请先登录！");
        }
        if (!Md5Util.getMD5String(changePasswordVO.getOldPassword()).equals(currentUser.getPassword())) {
            return JsonResponse.failure("旧密码错误！");
        }
        if (!changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
            return JsonResponse.failure("两次密码输入不一致！");
        }
        currentUser.setPassword(Md5Util.getMD5String(changePasswordVO.getNewPassword()));
        userService.updateById(currentUser);
        return JsonResponse.successMessage("密码修改成功！");
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

    @PostMapping("/systemAdmin/addUserWithExcel")
    public JsonResponse addUserWithExcel(@RequestParam("file") MultipartFile file, @RequestParam String userType) throws IOException {
        // 权限校验
        User currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.getType().equals("系统管理员")) {
            return JsonResponse.failure("无权限操作！");
        }

        try {
            // 1. 检查文件是否为空
            if (file.isEmpty()) {
                return JsonResponse.failure("上传的文件为空");
            }

            // 2. 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !(contentType.equals("application/vnd.ms-excel") ||
                    contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
                return JsonResponse.failure("仅支持Excel文件(xls/xlsx)");
            }

            // 3. 解析Excel
            List<User> users = fileService.parseUserExcel(file, userType);

            // 4. 数据处理
            List<User> validUsers = new ArrayList<>();
            int successCount = 0;
            int errorCount = 0;

            for (User user : users) {
                // 跳过无效数据
                if (user.getUserId() == null || user.getName() == null) {
                    errorCount++;
                    continue;
                }

                // 检查用户是否已存在
                if (userService.getById(user.getUserId()) != null) {
                    errorCount++;
                    continue;
                }

                // 设置默认密码
                String defaultPassword = user.getUserId().toString();
                defaultPassword = defaultPassword.substring(Math.max(0, defaultPassword.length() - 6));
                user.setPassword(Md5Util.getMD5String(defaultPassword));

                // 设置用户类型
                user.setType(userType);

                validUsers.add(user);
            }

            // 5. 批量保存
            if (!validUsers.isEmpty()) {
                userService.saveBatch(validUsers);
                successCount = validUsers.size();
            }

            return JsonResponse.success("导入完成" + successCount + "条，失败" + errorCount + "条");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonResponse.failure("导入失败: " + e.getMessage());
        }
    }

    //TODO: 待测试；可能需要分页
    @GetMapping("/maintenanceList")
    public JsonResponse getMaintenanceList(){
        User currentUser = getCurrentUser();
        if (currentUser.getType().equals("学生") || currentUser.getType().equals("教师")){
            return JsonResponse.failure("无获取权限！");
        }
        List<User> users = userService.list(new QueryWrapper<User>().eq("type", "维修管理员"));
        return JsonResponse.success(users);
    }
    //TODO: 待测试
    @GetMapping("/dormitoryList")
    public JsonResponse getDormitoryList(){
        User currentUser = getCurrentUser();
        if (currentUser.getType().equals("学生") || currentUser.getType().equals("教师")){
            return JsonResponse.failure("无获取权限！");
        }
        List<User> users = userService.list(new QueryWrapper<User>().eq("type", "宿舍管理员"));
        return JsonResponse.success(users);
    }
    //TODO: 待测试
    @GetMapping("/leaderList")
    public JsonResponse getLeaderList(){
        User currentUser = getCurrentUser();
        if (currentUser.getType().equals("学生") || currentUser.getType().equals("教师")){
            return JsonResponse.failure("无获取权限！");
        }
        List<User> users = userService.list(new QueryWrapper<User>().eq("type", "分管领导"));
        return JsonResponse.success(users);
    }

    @PostMapping("/logout")
    public JsonResponse logout(@RequestParam String token){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        //删除redis中对应的token
        operations.getOperations().delete(token);
        return JsonResponse.success("退出成功！");
    }



}
