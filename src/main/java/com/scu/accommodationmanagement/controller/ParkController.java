package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.service.IParkService;
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

    //TODO: 增加权限控制，加入park同时加入对应的building，room，bed等信息
    @PostMapping("/add")
    public JsonResponse add(@RequestBody Park park) {
        parkService.add(park);

        return JsonResponse.success("添加成功");
    }

    @GetMapping("/list")
    public JsonResponse list() {
        List<Park> list = parkService.list();
        return JsonResponse.success(list);
    }

}
