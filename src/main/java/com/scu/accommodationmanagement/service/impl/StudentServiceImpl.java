package com.scu.accommodationmanagement.service.impl;

import com.scu.accommodationmanagement.model.po.Student;
import com.scu.accommodationmanagement.mapper.StudentMapper;
import com.scu.accommodationmanagement.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生 服务实现类
 * </p>
 *
 * @author author
 * @since 2025-06-22
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
