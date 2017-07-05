package com.shine.service;

import com.shine.dao.AdminDao;
import com.shine.constant.UserType;
import com.shine.mapper.AdminUserMapper;
import com.shine.model.AdminUser;
import com.shine.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import resource.MyResource;

/**
 * @author 7le
 * @since 2017/4/3 0003.
 */
@Service
public class LoginService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @MyResource
    AdminDao adminDao;

    public String doAdminRegister(String loginName,String password) {

        //查看数据库是否有重复姓名
        /*AdminUser adminUser1 =adminUserMapper.

        if(adminUser1 != null){
            return "用户已存在";
        }*/
        AdminUser adminUser=new AdminUser();
        adminUser.setLoginName(loginName);
        adminUser.setPassword(MD5Util.doImaoMd5(loginName, password));
        adminUser.setType(UserType.MEMBER.key());
        adminUser.setCrTime(System.currentTimeMillis());
        adminUser.setLastTime(adminUser.getCrTime());
        int flag=adminUserMapper.insert(adminUser);

        if(flag==0){
            return "注册失败";
        }

        return null;
    }

    public void OK(){
        adminDao.yoyo();
    }
}
