package com.scu.accommodationmanagement.utils;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserExcelData {
    @ExcelProperty("学号")
    private Long studentId;

    @ExcelProperty("工号")
    private Long teacherId;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("学院")
    private String college;

    @ExcelProperty("专业")
    private String major;

    @ExcelProperty("年级")
    private Long grade;

    @ExcelProperty("班级")
    private Long clazz;

    @ExcelProperty("职称")
    private String title;

    @ExcelProperty("性别")
    private String sex;

    @ExcelProperty("联系方式")
    private String contact;
}