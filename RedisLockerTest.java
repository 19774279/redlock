/**
 * Description:
 * Project Name: redlock
 * Package Name: com.example.demo.redlock
 * Created by: zhusj on 2019/10/11
 * Copyright (c) 2015~ , 西安众盈医疗信息科技有限公司
 */
package com.example.demo.redlock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/aaa")
public class RedisLockerTest {

	@Autowired
	RedisLocker distributedLocker;

	@RequestMapping(value = "/redlock")
	public String testRedlock() throws Exception {

		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(5);
		for (int i = 0; i < 5; ++i) { // create and start threads
			new Thread(new Worker(startSignal, doneSignal)).start();
		}
		startSignal.countDown(); // let all threads proceed
		doneSignal.await();
		System.out.println("All processors done. Shutdown connection");
		return "redlock";
	}

	class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		@Override
		public void run() {
			try {
				startSignal.await();
				distributedLocker.lock("test", new AquiredLockWorker<Object>() {

					@Override
					public Object invokeAfterLockAquire() {
						doTask();
						return null;
					}

				});
			} catch (Exception e) {

			}
		}

		void doTask() {
			System.out.println(Thread.currentThread().getName() + " start");
			Random random = new Random();
			int _int = random.nextInt(200);
			System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
			try {
				Thread.sleep(_int);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " end");
			doneSignal.countDown();
		}
	}
}
