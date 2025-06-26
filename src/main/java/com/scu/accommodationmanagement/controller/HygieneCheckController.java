package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.HygieneCheck;
import com.scu.accommodationmanagement.model.po.User;
import com.scu.accommodationmanagement.service.IHygieneCheckService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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


}
