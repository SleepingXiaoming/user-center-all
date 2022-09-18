package com.ming.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.usercenter.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceTest {

    @Resource
    private StudentService studentService;

    @Test
    void  queryById() {
        Student student = studentService.getById("A19190162");
        assertEquals("ming", student.getUsername());
    }

    @Test
    void insertUserTest() {
        Student student = new Student("A19190258", "鹏");
        boolean save = studentService.save(student);
        assertTrue(save);
    }

}