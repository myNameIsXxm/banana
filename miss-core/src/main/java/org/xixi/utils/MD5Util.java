package org.xixi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static void main(String[] args) {
		try {
			System.out.println(MD5Util.md5Of32Upper("Abcd1234").toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String md5Of32Upper(String src) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(src.getBytes());
		byte[] b = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for(int offset=0;offset<b.length;offset++){
			i=b[offset];
			if(i<0){
				i+=256;
			}
			if(i<16){
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}
}
