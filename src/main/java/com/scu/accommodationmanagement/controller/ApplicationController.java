package com.scu.accommodationmanagement.controller;

import com.scu.accommodationmanagement.model.vo.MyApplicationVO;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.model.po.Bed;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.model.vo.ReviewDataVO;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.service.IUserService;
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
    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    public JsonResponse add(@RequestBody Application application) {
        User user = CurrentUserUtil.getCurrentUser();
        application.setApplierId(user.getUserId());
        Application oldApplication = applicationService.getLatestByApplierId(application.getApplierId());
        if (oldApplication != null && !oldApplication.getStatus().equals("已处理") && !oldApplication.getStatus().equals("不通过")) {
            return JsonResponse.failure("已提交过申请，请勿重复提交");
        }

        //获取目标床位
        Bed targetbed;
        if (application.getApplicationType().equals("校外住宿") ||
                application.getApplicationType().equals("个人退宿")){
            //退宿或校外住宿时，目标床位即为当前用户所在床位
            targetbed = bedService.getById(bedService.getByUserId(user.getUserId()));
            if (targetbed == null) {
                return JsonResponse.failure("您尚未申请过床位，请先申请");
            }
            application.setTargetBed(targetbed.getBedId());
        }else {
            //其余情况为校内住宿或学生互换，目标床位即为申请时选择的床位
            targetbed = bedService.getById(application.getTargetBed());
        }

        //校验目标床位是否被占用
        if ((application.getApplicationType().equals("普通入住") ||
                application.getApplicationType().equals("普通调整")) &&
                targetbed.getUserId() != null){
            return JsonResponse.failure("目标床位已被占用，请重新选择");
        }
        //校验普通入住申请时，自己是否已经有床位
        if (application.getApplicationType().equals("普通入住") && bedService.getById(bedService.getByUserId(user.getUserId())) != null){
            return JsonResponse.failure("您已有床位，无需申请");
        }

        Long dormitoryAdminIdByBedId = bedService.getDormitoryAdminIdByBedId(targetbed.getBedId());
        if (dormitoryAdminIdByBedId == null){
            return JsonResponse.failure("该楼栋未分配宿舍管理员，请重新选择");
        }
        //分配处理人：宿舍管理员
        application.setDormitoryId(bedService.getDormitoryAdminIdByBedId(targetbed.getBedId()));

        application.setApplierId(CurrentUserUtil.getCurrentUser().getUserId());
        application.setStatus("待审核");
        applicationService.save(application);
        return JsonResponse.successMessage("申请完成，请等待分管领导审核");
    }

    @PostMapping("/update")
    public JsonResponse update(@RequestBody Application application) {
        Application oldApplication = applicationService.getById(application.getApplicationId());
        if (oldApplication == null) {
            return JsonResponse.failure("该申请不存在");
        }
        if (!oldApplication.getStatus().equals("待审核")) {
            return JsonResponse.failure("该申请已处理，请勿重复操作");
        }

        Application byId = applicationService.getByTargetBed(application.getTargetBed());
        Bed bed = bedService.getById(application.getTargetBed());
        if (byId != null || bed.getUserId() != null) {
            return JsonResponse.failure("目标床位被占用，请重新选择");
        }

        application.setApplierId(CurrentUserUtil.getCurrentUser().getUserId());
        applicationService.updateById(application);
        return JsonResponse.successMessage("修改完成，请等待分管领导审核");
    }

    @PostMapping("/cancel")
    public JsonResponse delete(Long applicationId) {
        Application byId = applicationService.getById(applicationId);
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getUserId().equals(byId.getApplierId())) {
            return JsonResponse.failure("只能撤销自己的申请！");
        }

        if (!byId.getStatus().equals("待审核") && !byId.getStatus().equals("不通过")) {
            return JsonResponse.failure("该申请已开始处理，无法删除！");
        }

        byId.setIsDeleted(true);
        applicationService.updateById(byId);
        return JsonResponse.successMessage("撤销成功");
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
        return JsonResponse.success(applicationService.pageList(studentId, applicationType, status, startTime, endTime, pageNum, pageSize));
    }

    @GetMapping("/userApplications")
    public JsonResponse userApplications(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("学生")) {
            return JsonResponse.failure("请先登录学生账号！");
        }
        return JsonResponse.success(applicationService.userApplications(user.getUserId(), status));
    }

    @GetMapping("/adminApplications")
    public JsonResponse adminApplications(@RequestParam String status) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")) {
            return JsonResponse.failure("无权限查看申请！");
        }
        return JsonResponse.success(applicationService.adminApplications(user.getUserId(), status));
    }

    @PostMapping("/review")
    public JsonResponse review(@RequestBody ReviewDataVO reviewDataVO) {
        User user = CurrentUserUtil.getCurrentUser();
        Long applicationId = reviewDataVO.getApplicationId();
        String opinion = reviewDataVO.getOpinion();
        if (!user.getType().equals("分管领导")) {
            return JsonResponse.failure("无权限审核申请！");
        }
        Application application = applicationService.getById(applicationId);
        if (!application.getStatus().equals("待审核")) {
            return JsonResponse.failure("该申请已处理，请勿重复操作！");
        }
        application.setLeaderId(user.getUserId());
        if (!opinion.isBlank()) {
            application.setOpinion(opinion);
            application.setStatus("不通过");
        } else {
            application.setStatus("待处理");
        }

        application.setReviewTime(LocalDateTime.now());
        applicationService.updateById(application);
        return JsonResponse.successMessage("审核完成，请等待宿舍管理员处理");
    }

    @PostMapping("/process")
    public JsonResponse process(@RequestParam Long applicationId) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")) {
            return JsonResponse.failure("无权限处理申请！");
        }
        Application application = applicationService.getById(applicationId);
        if (application.getStatus().equals("待审核") || application.getStatus().equals("不通过")) {
            return JsonResponse.failure("该申请未审核通过，无法处理！");
        }
        Bed targetbed = bedService.getById(application.getTargetBed());
        switch (application.getApplicationType()) {
            case "普通入住" -> {
                targetbed.setUserId(application.getApplierId());
                bedService.updateById(targetbed);
            }
            case "普通调整" -> {
                Bed currentBed = bedService.getByUserId(application.getApplierId());
                currentBed.setUserId(null);
                bedService.updateByIdForApplication(currentBed);
                targetbed.setUserId(application.getApplierId());
                bedService.updateByIdForApplication(targetbed);
            }
            case "学生互换" -> {
                Bed currentBed = bedService.getByUserId(application.getApplierId());
                Long targetBedUser = targetbed.getUserId();
                Long currentBedUser = currentBed.getUserId();
                bedService.clearUser(currentBed);
                bedService.clearUser(targetbed);

                currentBed.setUserId(targetBedUser);
                bedService.updateByIdForApplication(currentBed);
                targetbed.setUserId(currentBedUser);
                bedService.updateByIdForApplication(targetbed);
            }
            case "个人退宿", "校外住宿" -> {
                Bed currentBed = bedService.getByUserId(application.getApplierId());
                bedService.clearUser(currentBed);
            }
        }

        application.setStatus("已处理");
        application.setProcessTime(LocalDateTime.now());
        applicationService.updateById(application);
        return JsonResponse.successMessage("处理成功");
    }

    @GetMapping("/myApplication")
    public JsonResponse myApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        List<Application> apps = applicationService.getByApplierId(user.getUserId());
        return JsonResponse.success(convertToVO(apps));
    }

    @GetMapping("/processedApplication")
    public JsonResponse processedApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!"宿舍管理员".equals(user.getType())) {
            return JsonResponse.failure("无权限查看已处理申请！");
        }
        List<Application> apps = applicationService.getByDormitoryId(user.getUserId());
        return JsonResponse.success(convertToVO(apps));
    }

    @GetMapping("/reviewedApplication")
    public JsonResponse reviewedApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!"分管领导".equals(user.getType())) {
            return JsonResponse.failure("无权限查看已审核申请！");
        }
        List<Application> apps = applicationService.getByLeaderId(user.getUserId());
        return JsonResponse.success(convertToVO(apps));
    }

    @GetMapping("/toBeReviewedApplication")
    public JsonResponse toBeReviewedApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!"分管领导".equals(user.getType())) {
            return JsonResponse.failure("无权限查看待审核申请！");
        }
        List<Application> apps = applicationService.getToBeReviewedApplication(user.getUserId());
        List<MyApplicationVO> myApplicationVOS = convertToVO(apps);
        return JsonResponse.success(myApplicationVOS);
    }

    @GetMapping("/toBeProcessedApplication")
    public JsonResponse toBeProcessedApplication() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!"宿舍管理员".equals(user.getType())) {
            return JsonResponse.failure("无权限查看待处理申请！");
        }
        List<Application> apps = applicationService.getToBeProcessedApplication(user.getUserId());
        return JsonResponse.success(convertToVO(apps));
    }

    private List<MyApplicationVO> convertToVO(List<Application> applications) {
        List<MyApplicationVO> vos = new ArrayList<>();
        for (Application app : applications) {
            if (app.getIsDeleted()) continue;
            MyApplicationVO vo = new MyApplicationVO();
            vo.setApplicationId(app.getApplicationId());
            vo.setApplicationType(app.getApplicationType());
            vo.setApplicationTime(DateTimeConverterUtil.convertToChineseDateTime(app.getApplicationTime()));
            vo.setStatus(app.getStatus());
            vo.setRemark(app.getRemark());
            vo.setOpinion(app.getOpinion());
            vo.setApplierId(app.getApplierId());
            vo.setApplierName(userService.getById(app.getApplierId()).getName());
            vo.setDormitoryAdminName(userService.getById(app.getDormitoryId()).getName());
            if (app.getLeaderId() != null) {
                vo.setLeaderName(userService.getById(app.getLeaderId()).getName());
            }
            if (!"校外住宿".equals(app.getApplicationType())) {
                vo.setTargetLocation(bedService.getLocationByBedId(app.getTargetBed()));
            } else {
                vo.setTargetLocation(app.getNewAddress());
            }
            vos.add(vo);
        }
        return vos;
    }
}
