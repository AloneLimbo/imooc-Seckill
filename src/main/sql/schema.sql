
CREATE DATABASE seckill;

use seckill;

CREATE TABLE seckill(
  seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  name VARCHAR(120) NOT NULL COMMENT '商品名称',
  number INT NOT NULL COMMENT '库存数量',
  start_time DATETIME NOT NULL  COMMENT '秒杀开始时间',
  end_time DATETIME NOT NULL  COMMENT '秒杀结束时间',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time (start_time),
  KEY idx_end_time (end_time),
  KEY idx_create_time (create_time)
)ENGINE =InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET =utf8 COMMENT '秒杀商品库';

  INSERT INTO seckill(name, number, start_time, end_time)
  VALUES('1000元秒杀iphone',100,'2016-9-23 00:00:00','2016-9-24 00:00:00');
  INSERT INTO seckill(name, number, start_time, end_time)
  VALUES('5000元秒杀iphone4',200,'2016-9-23 00:00:00','2016-9-24 00:00:00');
INSERT INTO seckill(name, number, start_time, end_time)
  VALUES('6000元秒杀iphone4s',300,'2016-9-23 00:00:00','2016-9-24 00:00:00');
INSERT INTO seckill(name, number, start_time, end_time)
  VALUES('2000元秒杀iphone5',200,'2016-9-23 00:00:00','2016-9-24 00:00:00');

CREATE TABLE success_killed(
  seckill_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  user_phone BIGINT NOT NULL COMMENT '用户手机号码',
  state TINYINT NOT NULL DEFAULT -1 COMMENT '标识 -1：无效 0:成功 1:已付款 2:已发货 ',
  create_time TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_creat_time(create_time)

)ENGINE =InnoDB  DEFAULT CHARSET =utf8 COMMENT '秒杀商品库';