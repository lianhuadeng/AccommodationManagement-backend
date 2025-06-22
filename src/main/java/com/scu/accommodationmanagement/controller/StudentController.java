package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Result;
import com.scu.accommodationmanagement.model.po.Student;
import com.scu.accommodationmanagement.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 学生 前端控制器
 * </p>
 *
 * @author author
 * @since 2025-06-22
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping("/login")
    public Result login(@RequestParam Long student_id, @RequestParam String password) {
        Student student = studentService.getById(student_id);
        if (student == null) {
            return Result.error("用户不存在");
        }
        if (student.getPassword().equals(password)) {
            return Result.success();
        }else{
            return Result.error("学号或密码错误");
        }


    }
}
