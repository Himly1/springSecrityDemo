package com.himly.springsecuritydemo.service.impl;

import com.himly.springsecuritydemo.dao.StudentDao;
import com.himly.springsecuritydemo.model.Student;
import com.himly.springsecuritydemo.service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create_at:MaZheng
 * create_by:${date} ${time}
 */
@Service
public class StudentServiceImpl implements StudentService{

    private static final Logger log = Logger.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentDao studentDao;

    @Override
    public Student getStudentById(Long id) throws Exception{

        log.info("id is=="+id);

        if (null==id||0==id) {
            log.info("id is null or zero");
            throw new Exception("id can not be null or zero");
        }

        Student student = null;

        try{
            student = studentDao.getStudentById(id);
        }catch (Throwable t) {
            log.error("has an error see=="+t.getMessage(),t);
            throw t;
        }


        log.info("student is=="+student);
        return student;
    }

    @Override
    public Student getStudentByMobile(Long mobile) throws Exception{

        log.info("mobile is =="+mobile);

        if (null==mobile) {
            log.info("mobile is null");
            throw new Exception("mobile can not be null");
        }

        Student student = null;

        try{
            student = studentDao.getStudentByMobile(mobile);
        }catch (Throwable t) {
            log.error("has an error,see=="+t.getMessage(),t);
            throw t;
        }

        log.info("student is =="+student);

        return student;
    }
}
