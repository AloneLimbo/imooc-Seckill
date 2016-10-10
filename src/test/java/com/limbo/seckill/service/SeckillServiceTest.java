package com.limbo.seckill.service;

import com.limbo.seckill.dto.Exposer;
import com.limbo.seckill.dto.SeckillExecution;
import com.limbo.seckill.entity.Seckill;
import com.limbo.seckill.exception.RepeatKillExceotion;
import com.limbo.seckill.exception.SeckillCloseException;
import com.limbo.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by limbo on 16-9-27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {


private final Logger logger = LoggerFactory.getLogger(this.getClass());

   @Autowired
   private SeckillService seckillService;


    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);

    }

    @Test
    public void getById() throws Exception {
        long id =1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void testSeckillLogic() throws Exception {
            long id=1000;
            long phone=15761695093L;
            Exposer exposer =seckillService.exportSeckillUrl(id);

            if(exposer.isExposed()){
                logger.info("##########exposer={}",exposer);
                try{
                    SeckillExecution execution = seckillService.executeSeckill(id,phone,exposer.getMd5());
                    logger.info("$$$$$$$$$$$Resule={}",execution);
                }catch (RepeatKillExceotion e){
                    logger.error(e.getMessage());
                }catch (SeckillCloseException e){
                    logger.error(e.getMessage());
                }
            }

    }



}