package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.FileService;
import com.scu.accommodationmanagement.service.IBedService;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.scu.accommodationmanagement.utils.CurrentUserUtil.getCurrentUser;

/**
 * <p>
 * 床位 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/bed")
public class BedController {
    @Autowired
    private IBedService bedService;
    @Autowired
    private FileService fileService;

    // 批量宿舍操作
    //TODO: 待实现
    @PostMapping("/multiSchedule")
    public JsonResponse multiSchedule(
            @RequestParam("file") MultipartFile file,
            @RequestParam Long buildingId,
            @RequestParam String groupBy
    ) {
        return JsonResponse.success(null);
    }

}
