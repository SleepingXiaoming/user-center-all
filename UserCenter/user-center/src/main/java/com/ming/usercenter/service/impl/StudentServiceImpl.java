package com.ming.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.usercenter.mapper.StudentMapper;
import com.ming.usercenter.pojo.Student;
import com.ming.usercenter.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description 针对表【student】的数据库操作Service实现
 * @createDate 2022-09-06 15:05:05
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}




