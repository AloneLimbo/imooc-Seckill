package com.limbo.seckill.service.impl;

import com.limbo.seckill.dao.SeckillDao;
import com.limbo.seckill.dao.SuccessKilledDao;
import com.limbo.seckill.dto.Exposer;
import com.limbo.seckill.dto.SeckillExecution;
import com.limbo.seckill.entity.Seckill;
import com.limbo.seckill.entity.SuccessKilled;
import com.limbo.seckill.enums.SeckillStatEnum;
import com.limbo.seckill.exception.RepeatKillExceotion;
import com.limbo.seckill.exception.SeckillCloseException;
import com.limbo.seckill.exception.SeckillException;
import com.limbo.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


/**
 * Created by limbo on 16-9-27.
 */
//@Component @Service @Dao @Conroller
@Service
public class SeckillServiceImpl implements SeckillService{


    private Logger logger= LoggerFactory.getLogger(this.getClass());

    //注入Servie依赖
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆MD54
    private final String salt="asdfasfsgsdaf892kl2351.;.';3412";


    /**
     * 查询所有的秒杀记录
     *
     * @return
     */
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 秒杀开启时输出秒杀地址，
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill=seckillDao.queryById(seckillId);
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        //获取系统时间
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime() ||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }

        //转化特定字符串的过程，不可逆
        String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    /**
     * MD5函数
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId){
        String base=seckillId + "/"+salt;

        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }


    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */

    /**
     * 使用注解控制事物方法的优缺点
     * 1:开发团队达成一致约定，明确标注事物方法的编程风格。
     * 2:保证事物方法的执行时间尽可能的短，不要穿插其他网络的操作，RPC/HTTP请求或者剥离到事物方法外部。
     * 3:不是所有的方法都需要事物，如只有一条修改操作，只读操作不需要事物。
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillExceotion, SeckillCloseException {

        try {

            if (md5 == null || ! md5.equals(getMD5(seckillId))) {
                throw new SeckillException("seckill data rewrite");
            }
            //执行秒杀逻辑：减库存+记录购买行为
            Date nowTime = new Date();
            int updataCount = seckillDao.reduceNumber(seckillId, nowTime);

            if (updataCount <= 0) {
                //没有更新记录，秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //唯一：seckill,userPhone
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillExceotion("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillExceotion e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所以编译期异常，转化为运行期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }

    }
}
