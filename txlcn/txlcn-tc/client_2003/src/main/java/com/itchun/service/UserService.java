package com.itchun.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.itchun.dao.UserDao;
import com.itchun.entity.UserInfoEntity;
import com.itchun.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    @LcnTransaction
    public Object insert() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);
        return "保存成功";
    }

    @Transactional
    @LcnTransaction
    public Object insert_exception_1() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);
        throw new RuntimeException("错误");
    }

    @Transactional
    @LcnTransaction
    public Object insert_union_1() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);

        // 调取
        restTemplate.getForObject("http://txlcn-tc-2/save.do",String.class);
        return "保存成功";
    }

    @Transactional
    @LcnTransaction
    public Object insert_union_exception_1() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);

        // 调取
        String message = restTemplate.getForObject("http://txlcn-tc-2/save.do",String.class);
        throw new RuntimeException("错误");
    }

    @Transactional
    @LcnTransaction
    public Object insert_union_exception_2() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);

        // 调取
        String message = restTemplate.getForObject("http://txlcn-tc-2/exception/save.do",String.class);
        return "保存成功";
    }

    @Transactional
    @LcnTransaction
    public Object insert_union_exception_3() {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setSex("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        userDao.save(userInfo);

        // 调取
        // 这种情况下本事务不会回滚
        try {
            String message = restTemplate.getForObject("http://txlcn-tc-2/exception/save.do",String.class);
        }catch (Exception e){
        }
        return "保存成功";
    }
}
