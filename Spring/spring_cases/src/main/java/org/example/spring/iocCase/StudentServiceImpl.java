package org.example.spring.iocCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liushaoya
 * @since 2024-12-10 13:35
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public String getMessage() {
        return studentDao.getMessage();
    }
}
