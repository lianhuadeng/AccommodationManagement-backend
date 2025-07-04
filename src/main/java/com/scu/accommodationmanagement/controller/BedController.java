package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.vo.BedListVO;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.*;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private IUserService userService;
    @Autowired
    private IParkService parkService;

    // 批量宿舍操作
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

            return JsonResponse.successMessage("批量排宿完成");
        } catch (IOException e) {
            return JsonResponse.failure("文件解析失败: " + e.getMessage());
        } catch (Exception e) {
            return JsonResponse.failure("排宿失败: " + e.getMessage());
        }
    }

    @GetMapping("/pageList")
    public JsonResponse<PageDTO<BedListVO>> pageList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Long parkId,
            @RequestParam(required = false) Long buildingId,
            @RequestParam(required = false) Long floor,
            @RequestParam(required = false) Long roomId
    ) {
        PageDTO<BedListVO> bedListDTOPageDTO = bedService.pageList(pageNum, pageSize, parkId, buildingId, floor, roomId);
        for (BedListVO bed : bedListDTOPageDTO.getItems()){
            bed.setParkName(parkService.getById(bed.getParkId()).getName());
            if (bed.getUserId() != null){
                bed.setUserName(userService.getById(bed.getUserId()).getName());
            }else {
                bed.setUserName("未分配");
            }
        }
        return JsonResponse.success(bedListDTOPageDTO);
    }

}
