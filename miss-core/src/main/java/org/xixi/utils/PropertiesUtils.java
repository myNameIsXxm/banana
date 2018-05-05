package org.xixi.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * error , need edit
 * @author XXM
 *
 */
public class PropertiesUtils {
	private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

	private static String PATH = "/app.properties";

	private static Properties config;

	private FileOutputStream oFile;

	public void read() {
		System.out.println(1);
		Set keyValue = config.keySet();
		System.out.println(keyValue);
		for (Iterator it = keyValue.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	public Properties create() {
		config = new Properties();
		try {
			oFile = new FileOutputStream(PATH, false);
			FileInputStream iFile = new FileInputStream(PATH);
			config.load(iFile);
		} catch (Exception e) {
			logger.error("error load app configurations," + e);
		}
		return config;
	}

	public void save() {
		try {
			config.store(oFile, null);
			oFile.close();
		} catch (IOException e) {
			logger.error("error save app configurations," + e);
		}
	}

}
