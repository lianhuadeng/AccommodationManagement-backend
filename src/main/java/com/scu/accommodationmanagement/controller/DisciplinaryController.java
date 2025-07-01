package com.scu.accommodationmanagement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scu.accommodationmanagement.model.dto.PageDTO;
import com.scu.accommodationmanagement.model.po.Disciplinary;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.model.vo.DisciplinaryVO;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.service.IDisciplinaryService;
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
    @Autowired
    private IUserService userService;
    @Autowired
    private IBedService bedService;

    @PostMapping("/add")
    public JsonResponse addDisciplinary(@RequestBody Disciplinary disciplinary) {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("宿舍管理员")) {
            return JsonResponse.failure("权限不足");
        }
        User student = userService.getById(disciplinary.getStudentId());
        if (student == null || !student.getType().equals("学生")) {
            return JsonResponse.failure("学生不存在");
        }
        disciplinary.setDormitoryId(user.getUserId());
        disciplinaryService.save(disciplinary);
        return JsonResponse.successMessage("提交成功");
    }

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

    @GetMapping("/myDisciplinary")
    public JsonResponse getMyDisciplinary() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("学生")) {
            return JsonResponse.failure("接口仅供学生使用");
        }
        List<DisciplinaryVO> disciplinaryVOList = new ArrayList<>();
        List<Disciplinary> disciplinaryList = disciplinaryService.list(new QueryWrapper<Disciplinary>().eq("student_id", user.getUserId()));
        for (Disciplinary disciplinary : disciplinaryList) {
            DisciplinaryVO disciplinaryVO = new DisciplinaryVO();
            disciplinaryVO.setDisciplinaryId(disciplinary.getDisciplinaryId());
            disciplinaryVO.setDormitoryId(disciplinary.getDormitoryId());
            disciplinaryVO.setDormitoryName(userService.getById(disciplinary.getDormitoryId()).getName());
            disciplinaryVO.setStudentId(disciplinary.getStudentId());
            disciplinaryVO.setStudentName(userService.getById(disciplinary.getStudentId()).getName());
            disciplinaryVO.setReason(disciplinary.getReason());
            disciplinaryVO.setTime(DateTimeConverterUtil.convertToChineseDateTime(disciplinary.getCreateTime()));
            disciplinaryVO.setLocation(bedService.getLocationByUserId(disciplinary.getStudentId()));
            if (disciplinary.getScore()!= null) disciplinaryVO.setScore(disciplinary.getScore());
            disciplinaryVOList.add(disciplinaryVO);
        }
        return JsonResponse.success(disciplinaryVOList);
    }

    @GetMapping("/toBeRateDisciplinary")
    public JsonResponse toBeRateDisciplinary() {
        User user = CurrentUserUtil.getCurrentUser();
        if (!user.getType().equals("分管领导")) {
            return JsonResponse.failure("权限不足");
        }
        List<DisciplinaryVO> disciplinaryVOList = new ArrayList<>();
        List<Disciplinary> disciplinaryList = disciplinaryService.list(new QueryWrapper<Disciplinary>().isNull("score"));
        for (Disciplinary disciplinary : disciplinaryList) {
            DisciplinaryVO disciplinaryVO = new DisciplinaryVO();
            disciplinaryVO.setDisciplinaryId(disciplinary.getDisciplinaryId());
            disciplinaryVO.setDormitoryId(disciplinary.getDormitoryId());
            disciplinaryVO.setDormitoryName(userService.getById(disciplinary.getDormitoryId()).getName());
            disciplinaryVO.setStudentId(disciplinary.getStudentId());
            disciplinaryVO.setStudentName(userService.getById(disciplinary.getStudentId()).getName());
            disciplinaryVO.setReason(disciplinary.getReason());
            disciplinaryVO.setTime(DateTimeConverterUtil.convertToChineseDateTime(disciplinary.getCreateTime()));
            disciplinaryVO.setLocation(bedService.getLocationByUserId(disciplinary.getStudentId()));
            disciplinaryVOList.add(disciplinaryVO);
        }
        return JsonResponse.success(disciplinaryVOList);
    }
}
