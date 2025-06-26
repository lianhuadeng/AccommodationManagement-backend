package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.FileService;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.service.IBuildingService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.scu.accommodationmanagement.utils.CurrentUserUtil.getCurrentUser;

/**
 * <p>
 * 床位 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/bed")
public class BedController {
    @Autowired
    private IBedService bedService;
    @Autowired
    private FileService fileService;
    @Autowired
    private IBuildingService buildingService;

    // 批量宿舍操作
    //TODO: 待测试
    @PostMapping("/multiSchedule")
    public JsonResponse multiSchedule(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long buildingId,
            @RequestParam String groupBy
    ) {
        User user = getCurrentUser();
        if (!user.getType().equals("宿舍管理员")) {
            return JsonResponse.failure("权限不足");
        }

        try {
            // 1. 解析Excel获取学生列表
            List<User> students = fileService.parseUserExcel(file, "学生");

            // 2. 按分组依据进行分组
            Map<String, List<User>> groupedStudents = students.stream()
                    .collect(Collectors.groupingBy(student -> {
                        switch (groupBy) {
                            case "学院": return student.getCollege();
                            case "专业": return student.getMajor();
                            case "班级": return student.getClazz().toString();
                            default: return "未分组";
                        }
                    }));

            // 3. 获取楼栋信息
            Building building = buildingService.getById(buildingId);

            // 4. 调用宿舍分配服务
            bedService.batchAllocateBeds(groupedStudents, building);

            return JsonResponse.success("批量排宿完成");
        } catch (IOException e) {
            return JsonResponse.failure("文件解析失败: " + e.getMessage());
        } catch (Exception e) {
            return JsonResponse.failure("排宿失败: " + e.getMessage());
        }
    }

}
