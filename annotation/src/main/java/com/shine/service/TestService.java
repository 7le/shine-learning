package com.shine.service;

import com.shine.annotation.spring.MyResource;
import com.shine.dao.TestDao;
import org.springframework.stereotype.Service;

/**
 * @author 7le
 * @since 2017/4/3 0003.
 */
@Service
public class TestService {


    @MyResource
    TestDao testDao;

    public void OK(){
        testDao.yoyo();
    }
}
