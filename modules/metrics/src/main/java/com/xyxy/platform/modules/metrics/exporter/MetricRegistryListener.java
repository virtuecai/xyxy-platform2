package com.xyxy.platform.modules.metrics.exporter;

import com.xyxy.platform.modules.metrics.Histogram;
import com.xyxy.platform.modules.metrics.Counter;
import com.xyxy.platform.modules.metrics.Timer;

public interface MetricRegistryListener {

	void onCounterAdded(String name, Counter counter);

	void onHistogramAdded(String name, Histogram histogram);

	void onTimerAdded(String name, Timer timer);
}
