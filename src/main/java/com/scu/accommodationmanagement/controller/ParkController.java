package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IParkService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 园区 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/park")
public class ParkController {
    @Autowired
    private IParkService parkService;

    @PostMapping("/add")
    public JsonResponse add(
            @RequestParam(required = true) String name,
            @RequestParam(required = true) String type,
            @RequestParam(required = false) Integer buildingNum,
            @RequestParam(required = false) Integer floorNum,
            @RequestParam(required = false) Integer roomNumPerFloor,
            @RequestParam(required = false) Integer bedNumPerRoom) {
        User user = CurrentUserUtil.getCurrentUser();
        if (user == null || !user.getType().equals("系统管理员")) {
            return JsonResponse.failure("权限不足");
        }
        parkService.add(name, type, buildingNum, floorNum, roomNumPerFloor, bedNumPerRoom);

        return JsonResponse.successMessage("添加成功");
    }

    @GetMapping("/list")
    public JsonResponse list() {
        List<Park> list = parkService.list();
        return JsonResponse.success(list);
    }

}
