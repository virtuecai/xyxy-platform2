/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.modules.metrics;

public class CounterMetric {
	public long totalCount;
	public double meanRate;
	public long lastCount;
	public double lastRate;

	@Override
	public String toString() {
		return "CounterMetric [totalCount=" + totalCount + ", meanRate=" + meanRate + ", lastCount=" + lastCount
				+ ", lastRate=" + lastRate + "]";
	}
}
