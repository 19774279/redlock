/**
 * Description:
 * Project Name: redlock
 * Package Name: com.example.demo.redlock
 * Created by: zhusj on 2019/10/11
 * Copyright (c) 2015~ , 西安众盈医疗信息科技有限公司
 */
package com.example.demo.redlock;

/**
 * 不能获取锁的异常类
 */
public class UnableToAquireLockException extends RuntimeException {

	public UnableToAquireLockException() {
		System.out.println("Thread.currentThread().getName():" + Thread.currentThread().getName());
	}

	public UnableToAquireLockException(String message) {
		super(message);
	}

	public UnableToAquireLockException(String message, Throwable cause) {
		super(message, cause);
	}
}
