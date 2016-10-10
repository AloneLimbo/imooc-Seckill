package com.limbo.seckill.exception;

import com.limbo.seckill.dto.SeckillExecution;

/**
 * 重复秒杀异常
 * Created by limbo on 16-9-27.
 */
public class RepeatKillExceotion extends SeckillException{


    public RepeatKillExceotion(String message){
        super(message);
    }


    public RepeatKillExceotion(String message, Throwable cause) {
        super(message, cause);
    }
}
