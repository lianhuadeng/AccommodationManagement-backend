package com.scu.accommodationmanagement.controller;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.service.IBuildingService;
import com.scu.accommodationmanagement.service.IParkService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 楼栋 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private IBuildingService buildingService;

    @PostMapping("/add")
    public JsonResponse addBuilding(@RequestParam(required = true) Long parkId,
                                    @RequestParam(required = false) Integer floorNum,
                                    @RequestParam(required = false) Integer roomNumPerFloor,
                                    @RequestParam(required = false) Integer bedNumPerRoom) {
        buildingService.add(parkId, floorNum, roomNumPerFloor, bedNumPerRoom);
        return JsonResponse.successMessage("添加成功");
    }

}
