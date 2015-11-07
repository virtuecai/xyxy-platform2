/*******************************************************************************
 * Copyright (c) 2005, 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.modules.metrics.reporter;

import java.util.Map;

import com.xyxy.platform.modules.metrics.Histogram;
import com.xyxy.platform.modules.metrics.Counter;
import com.xyxy.platform.modules.metrics.Timer;

/**
 * Reporter的公共接口，被ReportScheduler定时调用。
 * 
 *
 * 
 */
public interface Reporter {
	void report(Map<String, Counter> counters, Map<String, Histogram> histograms, Map<String, Timer> timers);
}
