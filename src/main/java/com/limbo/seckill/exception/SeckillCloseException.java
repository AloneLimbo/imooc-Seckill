package com.limbo.seckill.exception;

import com.limbo.seckill.dto.SeckillExecution;

/**
 * 秒杀关闭异常
 * Created by limbo on 16-9-27.
 */
public class SeckillCloseException extends SeckillException{



    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
