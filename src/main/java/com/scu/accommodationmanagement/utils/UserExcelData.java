package com.scu.accommodationmanagement.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserExcelData {
    @ExcelProperty(index = 0)
    private Long userId;

    @ExcelProperty(index = 1)
    private String name;

    @ExcelProperty(index = 2)
    private String college;

    @ExcelProperty(index = 3)
    private String major;

    @ExcelProperty(index = 4)
    private Long grade;

    @ExcelProperty(index = 5)
    private Long clazz;

    @ExcelProperty(index = 6)
    private String sex;

    @ExcelProperty(index = 7)
    private String contact;

//    @ExcelProperty("学号")
//    private Long userId;
//
//    @ExcelProperty("姓名")
//    private String name;
//
//    @ExcelProperty("学院")
//    private String college;
//
//    @ExcelProperty("专业")
//    private String major;
//
//    @ExcelProperty("年级")
//    private Long grade;
//
//    @ExcelProperty("班级")
//    private Long clazz;
//
//    @ExcelProperty("性别")
//    private String sex;
//
//    @ExcelProperty("联系方式")
//    private String contact;
}