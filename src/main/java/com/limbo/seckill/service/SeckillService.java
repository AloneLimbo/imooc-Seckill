package com.limbo.seckill.service;

import com.limbo.seckill.dto.Exposer;
import com.limbo.seckill.dto.SeckillExecution;
import com.limbo.seckill.entity.Seckill;
import com.limbo.seckill.exception.RepeatKillExceotion;
import com.limbo.seckill.exception.SeckillCloseException;
import com.limbo.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在”使用者“的角度设计借口
 * 三个方面：方法定义粒度，参数（越简练越好），返回类型（return 类型/异常）
 * Created by limbo on 16-9-27.
 */
public interface SeckillService {


    /**
     * 查询所有的秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */

    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws
            SeckillException,RepeatKillExceotion,SeckillCloseException;
}
