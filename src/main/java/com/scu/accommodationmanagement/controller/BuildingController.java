package com.scu.accommodationmanagement.controller;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.scu.accommodationmanagement.model.po.Building;
import com.scu.accommodationmanagement.model.po.Park;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IBuildingService;
import com.scu.accommodationmanagement.service.IParkService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list")
    public JsonResponse list(@RequestParam Long parkId) {
        return JsonResponse.success(buildingService.listByParkId(parkId));
    }
    @GetMapping("/list2")
    public JsonResponse list2() {
        User user = CurrentUserUtil.getCurrentUser();
        Long dorId = user.getUserId();
        return JsonResponse.success(buildingService.listByDormitoryId(dorId));
    }

    @GetMapping("/getFloorNum")
    public JsonResponse getFloorNum(@RequestParam Long buildingId) {
        Building building = buildingService.getById(buildingId);
        if (building == null) {
            return JsonResponse.failure("楼栋不存在");
        }
        return JsonResponse.success(building.getFloorNum());
    }

}
