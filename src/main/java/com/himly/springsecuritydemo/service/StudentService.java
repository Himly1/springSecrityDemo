package com.himly.springsecuritydemo.service;

import com.himly.springsecuritydemo.dao.StudentDao;
import com.himly.springsecuritydemo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
public interface StudentService {

    public Student getStudentById(Long id) throws Exception;

    public Student getStudentByMobile(Long mobile) throws Exception;
}
