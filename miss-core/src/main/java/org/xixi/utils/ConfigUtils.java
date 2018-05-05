package org.xixi.utils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ConfigUtils {
	private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

	private static String DEFAULT_CONFIG = "/app.properties";

	private static Properties config;

	static {
		config = new Properties();
		try {
			InputStream is = ConfigUtils.class.getResourceAsStream(DEFAULT_CONFIG);
			config.load(is);
		} catch (Exception e) {
			logger.error("error load message configurations," + e);
		}
	}

	private ConfigUtils() {
	}

	public static void boot() {
	}

	public static String getProperty(String key) {
		String value = config.getProperty(key);
		return StringUtils.isBlank(value) ? value : value.trim();
	}

	public static String getProperty(String key, String defaultValue) {
		String value = config.getProperty(key);
		if (StringUtils.isBlank(value))
			return defaultValue;

		return value.trim();
	}

	public static boolean getBooleanProperty(String name) {
		return getBooleanProperty(name, false);
	}

	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String value = ConfigUtils.getProperty(name);

		if (StringUtils.isBlank(value))
			return defaultValue;

		return Boolean.valueOf(value);
	}

	public static int getIntProperty(String name) {
		return getIntProperty(name, 0);
	}

	public static int getIntProperty(String name, int defaultValue) {
		String value = ConfigUtils.getProperty(name);

		if (StringUtils.isBlank(value))
			return defaultValue;

		try {
			return new Integer(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Enumeration keys() {
		return config.keys();
	}

	@SuppressWarnings("rawtypes")
	public static Properties getPropertiesStartingWith(String... startingWith) {
		Properties props = new Properties();
		for (Enumeration it = config.keys(); it.hasMoreElements();) {
			String key = (String) it.nextElement();
			for (String str : startingWith) {
				if (key.startsWith(str)) {
					props.put(key, config.get(key));
				}
			}
		}
		return props;
	}
	
}
