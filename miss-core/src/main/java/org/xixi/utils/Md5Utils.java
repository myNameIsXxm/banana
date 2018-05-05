package org.xixi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Md5Utils {

	public static String getStringMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];
			byte[] md5Bytes = md5.digest(byteArray);
			return bytehex(md5Bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getFileMd5(File file) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			FileInputStream in = new FileInputStream(file);
			// FileChannel ch = in.getChannel();
			// MappedByteBuffer byteBuffer =
			// ch.map(FileChannel.MapMode.READ_ONLY,
			// 0, file.length());
			// alg.update(byteBuffer);
			byte[] buffer = new byte[2048];
			int length = -1;
			while ((length = in.read(buffer)) != -1) {
				md5.update(buffer, 0, length);
			}
			byte[] digesta = md5.digest();
			return byte2hex(digesta);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private static String bytehex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

}
