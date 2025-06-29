package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.dto.MyApplicationDTO;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.DateTimeConverterUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 住宿调整申请 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IBedService bedService;

    @PostMapping("/add")
    public JsonResponse add(@RequestBody Application application) {
        Application oldApplication = applicationService.getLatestByApplierId(application.getApplierId());
        if (oldApplication != null && !oldApplication.getStatus().equals("已处理")) {
            return JsonResponse.failure("已提交过申请，请勿重复提交");
        }
        //校验床位是否被占用
        Application byId = applicationService.getByTargetBed(application.getTargetBed());
        Bed bed = bedService.getById(application.getTargetBed());
        if (byId != null || bed.getUserId() != null) {
            return JsonResponse.failure("目标床位被占用，请重新选择");
        }

        application.setApplierId(CurrentUserUtil.getCurrentUser().getUserId());
        application.setStatus("待审核");
        applicationService.save(application);
        return JsonResponse.success("申请完成，请等待分管领导审核");
    }

    @PostMapping("/update")
    public JsonResponse update(@RequestBody Application application) {
        Application oldApplication = applicationService.getById(application.getApplicationId());
        if (oldApplication == null) {
            return JsonResponse.failure("该申请不存在");
        }
        if (!oldApplication.getStatus().equals("待审核")){
            return JsonResponse.failure("该申请已处理，请勿重复操作");
        }

        Application byId = applicationService.getByTargetBed(application.getTargetBed());
        Bed bed = bedService.getById(application.getTargetBed());
        if (byId != null || bed.getUserId() != null) {
            return JsonResponse.failure("目标床位被占用，请重新选择");
        }

        application.setApplierId(CurrentUserUtil.getCurrentUser().getUserId());
        applicationService.updateById(application);
        return JsonResponse.success("修改完成，请等待分管领导审核");
    }

    @PostMapping("/cancel")
    public JsonResponse delete(Long applicationId) {
        Application byId = applicationService.getById(applicationId);
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getUserId().equals(byId.getApplierId())){
            return JsonResponse.failure("只能撤销自己的申请！");
        }

        if (!byId.getStatus().equals("待审核") && !byId.getStatus().equals("不通过")){
            return JsonResponse.failure("该申请已开始处理，无法删除！");
        }

        byId.setIsDeleted(true);
        applicationService.updateById(byId);
        return JsonResponse.success("撤销成功");
    }

    @GetMapping("/pageList")
    public JsonResponse<PageDTO<Application>> pageList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime
            ) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("分管领导") && ! user.getType().equals("系统管理员")){
            return JsonResponse.failure("无权限查看申请！");
        }
        return JsonResponse.success(applicationService.pageList(studentId, applicationType, status, startTime, endTime, pageNum, pageSize));
    }
    //TODO:待测试
    @GetMapping("/userApplications")
    public JsonResponse userApplications(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("学生")) {
            return JsonResponse.failure("请先登录学生账号！");
        }
        return JsonResponse.success(applicationService.userApplications(user.getUserId(), status));
    }

    //TODO:待测试
    @GetMapping("/adminApplications")
    public JsonResponse adminApplications(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")){
            return JsonResponse.failure("无权限查看申请！");
        }
        return JsonResponse.success(applicationService.adminApplications(user.getUserId(), status));
    }

    //TODO: 待测试
    @PostMapping("/review")
    public JsonResponse review(@RequestParam Long applicationId,
                               @RequestParam(required = false) String opinion,
                               @RequestParam(required = false) Long dormitoryId) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("分管领导")){
            return JsonResponse.failure("无权限审核申请！");
        }
        Application application = applicationService.getById(applicationId);
        if (!application.getStatus().equals("待审核")){
            return JsonResponse.failure("该申请已处理，请勿重复操作！");
        }
        application.setLeaderId(user.getUserId());
        application.setDormitoryId(dormitoryId);
        if(!opinion.isEmpty()){
            application.setOpinion(opinion);
            application.setStatus("不通过");
        }

        application.setStatus("待处理");
        application.setReviewTime(LocalDateTime.now());
        applicationService.updateById(application);
        return JsonResponse.success("审核完成，请等待宿舍管理员处理");
    }

    //TODO: 待测试
    @PostMapping("/process")
    public JsonResponse process(@RequestParam Long applicationId) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")){
            return JsonResponse.failure("无权限处理申请！");
        }
        Application application = applicationService.getById(applicationId);
        if (application.getStatus().equals("待审核") || application.getStatus().equals("不通过")){
            return JsonResponse.failure("该申请未审核通过，无法处理！");
        }
        Bed targetbed = bedService.getById(application.getTargetBed());
        switch (application.getApplicationType()) {
            case "普通入住", "普通调整" -> {
                targetbed.setUserId(application.getApplierId());
                bedService.updateById(targetbed);
            }
            case "学生互换" -> {
                Bed currentBed = bedService.getByUserId(application.getApplierId());
                currentBed.setUserId(targetbed.getUserId());
                targetbed.setUserId(application.getApplierId());
                bedService.updateById(currentBed);
                bedService.updateById(targetbed);
            }
            case "个人退宿", "校外住宿" -> {
                Bed currentBed = bedService.getByUserId(application.getApplierId());
                currentBed.setUserId(null);
                bedService.updateById(currentBed);
            }
        }

        application.setStatus("已处理");
        application.setProcessTime(LocalDateTime.now());
        applicationService.updateById(application);
        return JsonResponse.success("处理成功");
    }

    @GetMapping("/myApplication")
    public JsonResponse myApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        List<Application> byApplierId = applicationService.getByApplierId(user.getUserId());
        List<MyApplicationDTO> myApplications = new ArrayList<>();
        for (Application application : byApplierId) {
            if (application.getIsDeleted()) continue;
            MyApplicationDTO tmp = new MyApplicationDTO();
            tmp.setApplicationId(application.getApplicationId());
            tmp.setApplicationType(application.getApplicationType());
            tmp.setApplicationTime(DateTimeConverterUtil.convertToChineseDateTime(application.getApplicationTime()));
            tmp.setStatus(application.getStatus());
            tmp.setRemark(application.getRemark());
            tmp.setOpinion(application.getOpinion());
            if (!application.getApplicationType().equals("校外住宿"))
                tmp.setTargetLocation(bedService.getLocationByBedId(application.getTargetBed()));
            else tmp.setTargetLocation(application.getNewAddress());
            myApplications.add(tmp);
        }
        return JsonResponse.success(myApplications);
    }
}
