package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Room;
import com.scu.accommodationmanagement.service.IRoomService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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



}
