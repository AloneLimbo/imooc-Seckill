package com.limbo.seckill.dao;

import com.limbo.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIoc容器
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit，spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {


//    注入DAO实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id=1000;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /**
         * Seckill{seckillId=1000,
         * name='2000元秒杀iphone5',
         * number=200,
         * startTime=Fri Sep 23 00:00:00 CST 2016,
         * endTime=Sat Sep 24 00:00:00 CST 2016,
         * createTime=Sun Sep 25 18:32:26 CST 2016}
         */
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for(Seckill seckill:seckills){
            System.out.println(seckill);
        }

    }

    @Test
    public void testReduceNumber() throws Exception {

        Date killTime=new Date();
        int updateCount=seckillDao.reduceNumber(1000L,killTime);
        System.out.println("UpdateCount="+updateCount);
    }




}