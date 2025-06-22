package com.scu.accommodationmanagement.controller;


import com.scu.accommodationmanagement.model.po.Student;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("add")
    public void addStudent(@RequestBody Student student) {
        System.out.println(student);
    }
}
