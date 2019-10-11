/**
 * Description:
 * Project Name: redlock
 * Package Name: com.example.demo.redlock
 * Created by: zhusj on 2019/10/11
 * Copyright (c) 2015~ , 西安众盈医疗信息科技有限公司
 */
package com.example.demo.redlock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLocker implements DistributedLocker {

	private final static String LOCKER_PREFIX = "lock:";

	@Autowired
	RedissonConnector redissonConnector;

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws InterruptedException, UnableToAquireLockException, Exception {

		return lock(resourceName, worker, 100);
	}

	@Override
	public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
		RedissonClient redisson = redissonConnector.getClient();
		RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
		// Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
		boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
		if (success) {
			try {
				return worker.invokeAfterLockAquire();
			} finally {
				lock.unlock();
			}
		} else {
			System.out.println("tName:" + Thread.currentThread().getName());
		}
		throw new UnableToAquireLockException();
	}
}
