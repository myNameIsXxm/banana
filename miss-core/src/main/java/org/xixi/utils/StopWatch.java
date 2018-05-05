package org.xixi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StopWatch {

	private static final Logger logger = LoggerFactory.getLogger(StopWatch.class);

	private long start = System.currentTimeMillis();

	public long reset() {
		long now = System.currentTimeMillis();
		try {
			return now - start;
		} finally {
			start = now;
		}
	}

	public void resetAndLog(String label) {
		logger.debug(label + ": " + reset() + "ms");
	}
}
