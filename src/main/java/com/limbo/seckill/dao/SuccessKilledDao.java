package com.limbo.seckill.dao;

import com.limbo.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by limbo on 16-9-25.
 */
public interface SuccessKilledDao {


    /**
     *插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);


    /**
     * 根据id查询SuccessKilled并携带秒杀对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
