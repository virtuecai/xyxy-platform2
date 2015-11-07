/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.examples.showcase.demos.redis.job.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.xyxy.platform.modules.nosql.redis.pool.JedisPoolBuilder;
import com.xyxy.platform.modules.nosql.redis.service.scheduler.AdvancedJobConsumer;
import com.xyxy.platform.modules.nosql.redis.service.scheduler.SimpleJobConsumer;
import com.xyxy.platform.modules.extension.test.benchmark.ConcurrentBenchmark;
import com.xyxy.platform.modules.core.utils.Threads;

/**
 * 多线程运行reliable的JobConsumer，从"ss.job:ready" list中popup job进行处理。
 * 
 * 可用系统参数-Dthread.count 改变线程数.
 * 
 *
 */
public class AdvancedJobConsumerSinglePopDemo extends SimpleJobConsumerDemo {

	private AdvancedJobConsumer consumer;

	public static void main(String[] args) throws Exception {

		threadCount = Integer.parseInt(System.getProperty(ConcurrentBenchmark.THREAD_COUNT_NAME,
				String.valueOf(THREAD_COUNT)));

		pool = new JedisPoolBuilder().setDirectHostAndPort("localhost", "6379").setPoolSize(threadCount).buildPool();

		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		for (int i = 0; i < threadCount; i++) {
			AdvancedJobConsumerSinglePopDemo demo = new AdvancedJobConsumerSinglePopDemo();
			threadPool.execute(demo);
		}

		System.out.println("Hit enter to stop");
		try {
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					System.out.println("Shutting down");
					threadPool.shutdownNow();
					boolean shutdownSucess = threadPool.awaitTermination(
							SimpleJobConsumer.DEFAULT_POPUP_TIMEOUT_SECONDS + 1, TimeUnit.SECONDS);

					if (!shutdownSucess) {
						System.out.println("Forcing exiting.");
						System.exit(-1);
					}

					return;
				}
			}
		} finally {
			pool.destroy();
		}
	}

	public AdvancedJobConsumerSinglePopDemo() {
		consumer = new AdvancedJobConsumer("ss", pool);
		consumer.setReliable(true);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				String job = consumer.popupJob();
				if (job != null) {
					handleJob(job);
				} else {
					Threads.sleep(100);
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void handleJob(String job) {
		super.handleJob(job);
		consumer.ackJob(job);
	}
}