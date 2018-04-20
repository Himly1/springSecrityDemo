package com.himly.springsecuritydemo.dao;

import com.himly.springsecuritydemo.model.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public interface StudentDao {

    public Student getStudentById(Long id);

    public Student getStudentByMobile(Long mobile);
}
