package org.xixi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils {

	@SuppressWarnings("rawtypes")
	public static void unzip(File zipfile, File dest) {
		byte b[] = new byte[1024];
		int length;
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(zipfile);
			Enumeration enumeration = zipFile.entries();
			ZipEntry zipEntry = null;
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();

				File loadFile = new File(dest, zipEntry.getName());

				if (zipEntry.isDirectory()) {
					loadFile.mkdirs();
				} else {
					if (!loadFile.getParentFile().exists()) {
						loadFile.getParentFile().mkdirs();
					}
					OutputStream outputStream = new FileOutputStream(loadFile);
					InputStream inputStream = zipFile.getInputStream(zipEntry);
					if (inputStream == null)
						continue;
					while ((length = inputStream.read(b)) > 0)
						outputStream.write(b, 0, length);
					outputStream.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
