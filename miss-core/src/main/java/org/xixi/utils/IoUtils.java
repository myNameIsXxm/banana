package org.xixi.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class IoUtils {

	static byte[] EMPTY_BYTE_ARRAY = new byte[0];

	@SuppressWarnings("unused")
	public static byte[] readBytes(String path) throws FileNotFoundException {
		File js = new File(path);
		InputStream in = new FileInputStream(js);
		if (in == null)
			return EMPTY_BYTE_ARRAY;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			in.close();
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			return EMPTY_BYTE_ARRAY;
		}
	}
}
