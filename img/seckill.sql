-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;

-- 创建秒杀库存表
CREATE TABLE seckill(
	`seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	`name` VARCHAR(120) NOT NULL COMMENT '商品名称',
	`number` INT NOT NULL COMMENT '库存数量',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀创建时间',
	`start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
	`end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
	PRIMARY KEY (`seckill_id`),
	/*创建时间索引是为了以后时间查询的业务提供方便*/
	KEY `idx_start_time` (`start_time`),
	KEY `idx_end_time` (`end_time`),
	KEY `idx_create_time` (`create_time`)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'

-- 初始化数据
INSERT INTO 
	seckill(name, number, start_time, end_time)
VALUES
	('1000元秒杀iphone6', 100, '2019-11-01 00:00:00', '2020-11-02 00:00:00'),
	('500元秒杀ipad2', 200, '2017-11-01 00:00:00', '2020-11-02 00:00:00'),
	('300元秒杀小米4', 300, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
	('200元秒杀红米note', 400, '2015-11-01 00:00:00', '2015-11-02 00:00:00')

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed(
	`seckill_id` BIGINT NOT NULL COMMENT '商品库存id',
	`user_phone` BIGINT NOT NULL COMMENT '用户手机号',
	`state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态信息：-1无效，0成功，1已付款，2已发货',
	`create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`seckill_id`, `user_phone`),/*联合主键*/
	KEY `idx_create_time` (`create_time`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀库存表'


select s.seckill_id,name,number,s.create_time,start_time,end_time,
	user_phone,state,sk.create_time
	from seckill s,success_killed sk where s.seckill_id = sk
.seckill_id;



-- 秒杀执行存储过程
DELIMITER $$ -- onsole ; 转换为 $$
-- 定义存储过程
-- 参数：in 输入参数; out 输出参数
-- row_count():返回上一条修改类型sql(delete,insert,upodate)的影响行数
-- row_count: 0:未修改数据; >0:表示修改的行数; <0:sql错误/未执行修改sql
CREATE PROCEDURE `seckill`.`execute_seckill`
(IN v_seckill_id bigint, IN v_phone BIGINT,
IN v_kill_time TIMESTAMP, OUT r_result INT)
	BEGIN
		DECLARE insert_count INT DEFAULT 0;
		START TRANSACTION;
		INSERT ignore INTO success_killed (seckill_id, user_phone, create_time)
		VALUES(v_seckill_id, v_phone, v_kill_time);
		SELECT ROW_COUNT() INTO insert_count;
		IF (insert_count = 0) THEN
			ROLLBACK;
			SET r_result = -1;
		ELSEIF (insert_count < 0) THEN
			ROLLBACK ;
			SET r_result = -2;
		ELSE
			UPDATE seckill SET number = number - 1
			WHERE seckill_id = v_seckill_id AND end_time > v_kill_time
			AND start_time < v_kill_time AND number > 0;
			SELECT ROW_COUNT() INTO insert_count;
			IF (insert_count = 0) THEN
				ROLLBACK;
				SET r_result = 0;
			ELSEIF (insert_count < 0) THEN
				ROLLBACK;
				SET r_result = -2; 
			ELSE
				COMMIT;
			SET r_result = 1;
			END IF;
		END IF;
	END;
$$
-- 代表存储过程定义结束

DELIMITER ;

SET @r_result = -3;
-- 执行存储过程
call execute_seckill(1001, 13631231234, now(), @r_result);
-- 获取结果
SELECT @r_result;

-- 存储过程
-- 1.存储过程优化：事务行级锁持有的时间
-- 2.不要过度依赖存储过程
-- 3.简单的逻辑可以应用存储过程
-- 4.QPS:一个秒杀单6000/qps



