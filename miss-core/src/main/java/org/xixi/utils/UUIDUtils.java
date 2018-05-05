package org.xixi.utils;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

public final class UUIDUtils {
	public static String uuid() {
		return UUID.randomUUID().toString();
	}
	public static Long longId(){
		return (new Date().getTime()+RandomUtils.nextLong(1,100))*RandomUtils.nextLong(1,9);
	}
	public static void main(String[] args) {
		System.out.println(longId());
	}
}
	