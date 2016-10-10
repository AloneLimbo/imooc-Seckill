package com.limbo.seckill.dao;

import com.limbo.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by limbo on 16-9-26.
 */


/**
 * 配置spring和junit整合，junit启动时加载springIoc容器
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit，spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {

        long seckillId=1001L;
        long userPhone=13310435699L;
       int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {

        long seckillId=1001L;
        long userPhone=13310435699L;
        SuccessKilled successKilled= successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println("SuccessKillled="+successKilled);
        System.out.println("Successkilled.seckill="+successKilled.getSeckill());
    }

}