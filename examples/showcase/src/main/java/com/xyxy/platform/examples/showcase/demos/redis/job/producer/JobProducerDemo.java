/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.examples.showcase.demos.redis.job.producer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.xyxy.platform.examples.showcase.demos.redis.job.dispatcher.SimpleJobDispatcherDemo;
import com.xyxy.platform.modules.nosql.redis.pool.JedisPool;
import com.xyxy.platform.modules.nosql.redis.pool.JedisPoolBuilder;
import com.xyxy.platform.modules.nosql.redis.service.scheduler.JobProducer;
import com.xyxy.platform.modules.extension.test.benchmark.BenchmarkTask;
import com.xyxy.platform.modules.extension.test.benchmark.ConcurrentBenchmark;

/**
 * 运行JobProducer产生新的Job。
 * 
 * 可用系统参数重置测试规模，@see RedisCounterBenchmark
 * 
 *
 */
public class JobProducerDemo extends ConcurrentBenchmark {
	private static final int DEFAULT_THREAD_COUNT = 5;
	private static final long DEFAULT_TOTAL_COUNT = 500000;

	private static AtomicLong expiredMills = new AtomicLong(System.currentTimeMillis()
			+ (SimpleJobDispatcherDemo.DELAY_SECONDS * 1000));
	private static AtomicLong idGenerator = new AtomicLong(0);

	private long expectTps;
	private JedisPool pool;
	private JobProducer jobProducer;

	public static void main(String[] args) throws Exception {
		JobProducerDemo benchmark = new JobProducerDemo();
		benchmark.execute();
	}

	public JobProducerDemo() {
		super(DEFAULT_THREAD_COUNT, DEFAULT_TOTAL_COUNT);
		this.expectTps = Long.parseLong(System.getProperty("benchmark.tps",
				String.valueOf(SimpleJobDispatcherDemo.EXPECT_TPS)));
	}

	@Override
	protected void setUp() {
		pool = new JedisPoolBuilder().setDirectHostAndPort("localhost", "6379").setPoolSize(threadCount).buildPool();
		jobProducer = new JobProducer("ss", pool);
	}

	@Override
	protected void tearDown() {
		pool.destroy();
	}

	@Override
	protected BenchmarkTask createTask() {
		return new JobProducerTask();
	}

	public class JobProducerTask extends BenchmarkTask {
		@Override
		public void execute(final int requestSequence) {
			long jobId = idGenerator.getAndIncrement();
			jobProducer
					.schedule("job:" + jobId, expiredMills.get() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);

			// 达到期望的每秒的TPS后，expireTime往后滚动一秒
			if ((jobId % (expectTps)) == 0) {
				expiredMills.addAndGet(1000);
			}
		}
	}
}
