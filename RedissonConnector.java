/**
 * Description:
 * Project Name: redlock
 * Package Name: com.example.demo.redlock
 * Created by: zhusj on 2019/10/11
 * Copyright (c) 2015~ , 西安众盈医疗信息科技有限公司
 */
package com.example.demo.redlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * RedissonConnector连接类
 */
@Component
public class RedissonConnector {
	RedissonClient redisson;

	@PostConstruct
	public void init() {
		// 使用带密码的远程redis
//		Config config = new Config();
//		config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456");
//		redisson = Redisson.create(config);
		redisson = Redisson.create();
	}

	public RedissonClient getClient() {
		return redisson;
	}

}
