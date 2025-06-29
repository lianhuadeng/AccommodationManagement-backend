package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.service.IRoomService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 房间 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @GetMapping("/list")
    public JsonResponse list(
            @RequestParam(required = false) Long parkId,
        @RequestParam(required = false) Long buildingId,
        @RequestParam(required = false) Long floor
    ) {
        return JsonResponse.success(roomService.getlist(parkId, buildingId, floor));
    }


}
