package com.itchun.service;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.itchun.dao.AppleDao;
import com.itchun.entity.AppleInfoEntity;
import com.itchun.util.CommonUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppleService {

    @Autowired
    private AppleDao appleDao;

    @Transactional
    @LcnTransaction
    public Object insert() {
        AppleInfoEntity apple = new AppleInfoEntity();
        apple.setId(CommonUtil.getUUID());
        apple.setName("苹果-" + CommonUtil.getUUID().substring(0, 5));
        apple.setNumber(RandomStringUtils.randomNumeric(3));
        apple.setSavetime(CommonUtil.getSysTime());
        appleDao.save(apple);
        return "保存成功";
    }

    @Transactional
    @LcnTransaction
    public Object insert_exception_1() {
        AppleInfoEntity userInfo = new AppleInfoEntity();
        userInfo.setId(CommonUtil.getUUID());
        userInfo.setName("小明-" + CommonUtil.getUUID().substring(0, 5));
        userInfo.setNumber("男");
        userInfo.setSavetime(CommonUtil.getSysTime());
        appleDao.save(userInfo);
        throw new RuntimeException("错误");
    }
}
