package shine.spring.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import shine.spring.dao.VideoMapper;

/**
 * Created by 7le on 2017/11/6
 */
public class BaseServiceImpl {

    protected static final Logger videoLogger = LoggerFactory.getLogger("video");

    @Autowired
    protected VideoMapper videoMapper;
}
