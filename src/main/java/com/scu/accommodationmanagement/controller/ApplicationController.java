package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    //TODO:待测试
    @PostMapping("/add")
    public JsonResponse add(@RequestBody Application application) {
        Application oldApplication = applicationService.getLatestByApplierId(application.getApplierId());
        if (oldApplication != null && !oldApplication.getStatus().equals("已处理")) {
            return JsonResponse.failure("已提交过申请，请勿重复提交");
        }
        applicationService.save(application);
        return JsonResponse.success("申请完成，请等待分管领导审核");
    }
    //TODO:待测试
    @PostMapping("/update")
    public JsonResponse update(@RequestBody Application application) {
        Application oldApplication = applicationService.getById(application.getApplicationId());
        if (!oldApplication.getStatus().equals("待审核")){
            return JsonResponse.failure("该申请已处理，请勿重复操作");
        }
        applicationService.updateById(application);
        return JsonResponse.success("修改完成，请等待分管领导审核");
    }
    //TODO:待测试
    @PostMapping("/cancel")
    public JsonResponse delete(Long applicationId) {
        Application byId = applicationService.getById(applicationId);
        if (!byId.getStatus().equals("待审核")){
            return JsonResponse.failure("该申请已开始处理，无法删除！");
        }
        byId.setIsDeleted(true);
        applicationService.updateById(byId);
        return JsonResponse.success("撤销成功");
    }

    // TODO: 待测试
    @GetMapping("/pageList")
    public JsonResponse<PageDTO<Application>> pageList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String applicationType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime
            ) {
        return JsonResponse.success(applicationService.pageList(studentId, applicationType, status, startTime, endTime, pageNum, pageSize));
    }
    //TODO:待测试
    @GetMapping("/myApplication")
    public JsonResponse myApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        if (user == null) {
            return JsonResponse.failure("请先登录");
        }
        return JsonResponse.success(applicationService.myApplication(user.getUserId()));
    }


}
