package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.dto.MyRepairDTO;
import com.scu.accommodationmanagement.model.po.Repair;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.FileService;
import com.scu.accommodationmanagement.service.IRepairService;
import com.scu.accommodationmanagement.service.IUserService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.DateTimeConverterUtil;
import com.scu.accommodationmanagement.utils.ImageCompressUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 维修申请 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/repair")
public class RepairController {
    @Autowired
    private IRepairService repairService;
    @Autowired
    private IUserService userService;

    @Autowired
    private FileService fileService;

    //TODO:待测试
    @PostMapping("/add")
    public JsonResponse add(@RequestBody Repair repair) {
        repair.setStudentId(CurrentUserUtil.getCurrentUser().getUserId());
        repair.setDormitoryId(userService.getDormitoryAdminIdByUserId(CurrentUserUtil.getCurrentUser().getUserId()));
        repair.setStatus("待分配");
        repairService.save(repair);
        return JsonResponse.successMessage("申请成功");
    }
    //TODO:待测试
    @PostMapping("/cancel")
    public JsonResponse cancel(@RequestParam Long repairId) {
        Repair repair = repairService.getById(repairId);
        if (repair == null) {
            return JsonResponse.failure("申请不存在");
        }
        if (!repair.getStatus().equals("待分配")){
            return JsonResponse.failure("申请已处理, 无法取消");
        }
        repair.setIsDeleted(true);
        repairService.updateById(repair);
        return JsonResponse.successMessage("取消成功");
    }
    //TODO:待测试
    @PostMapping("/update")
    public JsonResponse update(@RequestBody Repair repair) {
        Repair oldRepair = repairService.getById(repair.getRepairId());
        if (oldRepair == null) {
            return JsonResponse.failure("申请不存在");
        }
        if (!oldRepair.getStatus().equals("待处理")){
            return JsonResponse.failure("申请已处理, 无法修改");
        }
        repairService.updateById(repair);
        return JsonResponse.successMessage("修改成功");
    }

    // 学生查看自己的申请
    //TODO:待测试
    @GetMapping("/userRepairs")
    public JsonResponse userRepairs(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("学生")){
            return JsonResponse.failure("请以学生身份登录");
        }
        return JsonResponse.success(repairService.userRepairs(user.getUserId(), status));
    }

    // 维修管理员查看需要自己处理的申请
    //TODO:待测试
    @GetMapping("/maintenanceAdminRepairs")
    public JsonResponse maintenanceAdminRepairs(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("维修管理员")){
            return JsonResponse.failure("权限不足");
        }
        return JsonResponse.success(repairService.maintenanceAdminRepairs(user.getUserId(), status));
    }

    //TODO:待测试
    @PostMapping("/dormitoryAdminRepairs")
    public JsonResponse dormitoryAdminRepairs(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")){
            return JsonResponse.failure("权限不足");
        }
        return JsonResponse.success(repairService.dormitoryAdminRepairs(user.getUserId(), status));
    }

    @PostMapping("/uploadImage")
    public JsonResponse uploadImage(@RequestParam MultipartFile file){
        try {
            //图片压缩
            MultipartFile compressedFile = ImageCompressUtil.compressImage(file);
            Map upload = fileService.upload(compressedFile);
            return JsonResponse.success(upload.get("url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 宿舍管理员分配维修
    //TODO: 待测试
    @PostMapping("/allocate")
    public JsonResponse allocate(@RequestParam Long repairId, @RequestParam Long maintenanceId) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")){
            return JsonResponse.failure("权限不足");
        }
        Repair repair = repairService.getById(repairId);
        repair.setDormitoryId(user.getUserId());
        repair.setMaintenanceId(maintenanceId);
        repair.setStatus("待处理");
        repairService.updateById(repair);
        return JsonResponse.successMessage("分配完成");
    }

    @GetMapping("/myRepair")
    public JsonResponse myRepair() {
        User user = CurrentUserUtil.getCurrentUser();
        List<Repair> repairs = repairService.getByStudentId(user.getUserId());
        List<MyRepairDTO> myRepairDTOList = new ArrayList<>();
        for (Repair repair : repairs) {
            MyRepairDTO myRepairDTO = new MyRepairDTO();
            myRepairDTO.setRepairId(repair.getRepairId());
            myRepairDTO.setRepairItem(repair.getRepairItem());
            myRepairDTO.setContent(repair.getContent());
            myRepairDTO.setApplyTime(DateTimeConverterUtil.convertToChineseDateTime(repair.getApplyTime()));
            myRepairDTO.setStatus(repair.getStatus());
            myRepairDTO.setLocation(repair.getLocation());
            myRepairDTO.setPictureUrl(repair.getPictureUrl());
            if (!repair.getStatus().equals("待分配"))
                myRepairDTO.setMaintainerName(userService.getById(repair.getMaintenanceId()).getName());
            myRepairDTOList.add(myRepairDTO);
        }
        return JsonResponse.success(myRepairDTOList);
    }
}
