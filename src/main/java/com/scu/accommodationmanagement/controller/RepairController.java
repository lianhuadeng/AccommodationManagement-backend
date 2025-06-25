package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Repair;
import com.scu.accommodationmanagement.service.IRepairService;
import com.scu.accommodationmanagement.utils.CurrentUserUtil;
import com.scu.accommodationmanagement.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 维修申请 前端控制器
 * </p>
 *
 * @author scu
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/repair")
public class RepairController {
    @Autowired
    private IRepairService repairService;

    //TODO:待测试
    @PostMapping("/add")
    public JsonResponse add(@RequestBody Repair repair) {
        repair.setStudentId(CurrentUserUtil.getCurrentUser().getUserId());
        repair.setStatus("待分配");
        repairService.save(repair);
        return JsonResponse.successMessage("申请成功");
    }
    //TODO:待测试
    @PostMapping("/cancel")
    public JsonResponse cancel(@RequestParam Long repairId) {
        Repair repair = repairService.getById(repairId);
        if (repair == null) {
            return JsonResponse.failure("申请不存在");
        }
        if (!repair.getStatus().equals("待分配")){
            return JsonResponse.failure("申请已处理, 无法取消");
        }
        repair.setIsDeleted(true);
        repairService.updateById(repair);
        return JsonResponse.successMessage("取消成功");
    }
    //TODO:待测试
    @PostMapping("/update")
    public JsonResponse update(@RequestBody Repair repair) {
        Repair oldRepair = repairService.getById(repair.getRepairId());
        if (oldRepair == null) {
            return JsonResponse.failure("申请不存在");
        }
        if (!oldRepair.getStatus().equals("待处理")){
            return JsonResponse.failure("申请已处理, 无法修改");
        }
        repairService.updateById(repair);
        return JsonResponse.successMessage("修改成功");
    }

    //TODO:待测试
    @GetMapping("/myRepairs")
    public JsonResponse myRepairs() {

        Long userId = CurrentUserUtil.getCurrentUser().getUserId();
        return JsonResponse.success(repairService.getByUserId(userId));
    }

}
