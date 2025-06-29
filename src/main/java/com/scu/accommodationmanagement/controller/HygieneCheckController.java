package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.HygieneCheck;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IHygieneCheckService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 卫生检查 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/hygiene-check")
public class HygieneCheckController {
    @Autowired
    private IHygieneCheckService hygieneCheckService;

    //TODO: 待测试
    @PostMapping("/add")
    public JsonResponse addHygieneCheck(@RequestBody HygieneCheck hygieneCheck) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")){
            return JsonResponse.failure("权限不足");
        }
        hygieneCheck.setDormitoryId(user.getUserId());
        hygieneCheckService.save(hygieneCheck);
        return JsonResponse.successMessage("添加成功");
    }

    //查看房间卫生检查统计
    //TODO: 待实现
//    @GetMapping("/statics")
//    public JsonResponse getStatics(){
//
//    }

    //查看卫生检查结果
    //TODO: 待测试
    @GetMapping("/pageList")
    public JsonResponse<PageDTO<HygieneCheck>> getPageList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime
    ){
        return JsonResponse.success(hygieneCheckService.getPageList(pageNum, pageSize, roomId, reason, startTime, endTime));
    }

}
