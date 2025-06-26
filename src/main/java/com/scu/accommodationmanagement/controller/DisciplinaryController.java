package com.scu.accommodationmanagement.controller;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Disciplinary;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IDisciplinaryService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 违纪记录 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/disciplinary")
public class DisciplinaryController {
    @Autowired
    private IDisciplinaryService disciplinaryService;

    //TODO: 待测试
    @PostMapping("/add")
    public JsonResponse addDisciplinary(@RequestBody Disciplinary disciplinary) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")) {
            return JsonResponse.failure("权限不足");
        }
        disciplinary.setDormitoryId(user.getUserId());
        disciplinaryService.save(disciplinary);
        return JsonResponse.successMessage("提交成功");
    }

    //TODO: 待测试
    @PostMapping("/setScore")
    public JsonResponse setScore(@RequestParam Long disciplinaryId, @RequestParam Long score) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("分管领导")) {
            return JsonResponse.failure("权限不足");
        }
        Disciplinary disciplinary = disciplinaryService.getById(disciplinaryId);
        disciplinary.setScore(score);
        disciplinary.setLeaderId(user.getUserId());
        disciplinaryService.updateById(disciplinary);
        return JsonResponse.successMessage("评分成功");
    }

    //TODO: 待测试
    @GetMapping("/pageList")
    public JsonResponse<PageDTO<Disciplinary>> getDisciplinaryList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime
    ) {
        return JsonResponse.success(disciplinaryService.getDisciplinaryList(pageNum, pageSize, reason, startTime, endTime));
    }
}
