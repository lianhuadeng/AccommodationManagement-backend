package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Application;
import com.scu.accommodationmanagement.service.IApplicationService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public JsonResponse add(@RequestBody Application application) {
        applicationService.save(application);
        return JsonResponse.success("申请完成，请等待分管领导审核");
    }

    @PostMapping("/update")
    public JsonResponse update(@RequestBody Application application) {

        applicationService.updateById(application);
        return JsonResponse.success("修改完成，请等待分管领导审核");
    }

    @PostMapping("/delete")
    public JsonResponse delete(int id) {
        applicationService.removeById(id);
        return JsonResponse.success("删除成功");
    }

    @GetMapping("/list")
    public JsonResponse list() {
        return JsonResponse.success(applicationService.list());
    }
}
