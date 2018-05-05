package org.xixi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public static String EMAIL = "([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+";
	public static String PHONE = "1[3|5|7|8]\\d{9}";
	public static String NUMBER = "[0-9]+";
	public static String CHINESE = "[\u4e00-\u9fa5]+";
	
	/**
	 * 完全匹配返回true
	 */
	public static boolean matcher(String s,String type){
		//return Pattern.compile(type).matcher(s).matches();  
		return s.matches(type);
	}
	
	/**
	 * 有匹配就返回true
	 */
	public static boolean find(String s,String type){
		return Pattern.compile(type).matcher(s).find();  
	}
	
	/**
	 * 取出字符串中满足表达式的内容
	 */
	public static String get(String s,String type){
		Matcher matcher = Pattern.compile(type).matcher(s);
		String result = "";
		while(matcher.find()){
			result+=matcher.group(0);  
		}
		if(result.equals("")){
			return null;
		}else{
			return result;
		}
	}
	
	/**
	 * 取出字符串中满足表达式的内容
	 */
	public static String[] getArray(String s,String type){
		Matcher matcher = Pattern.compile(type).matcher(s);
		List<String> result = new ArrayList<String>();
		while(matcher.find()){
			result.add(matcher.group(0));  
		}
		return result.toArray(new String[result.size()]);  
	}
	
	/**
	 * 取出字符串中满足表达式的内容
	 */
	public static List<String> getList(String s,String type){
		Matcher matcher = Pattern.compile(type).matcher(s);
		List<String> result = new ArrayList<String>();
		while(matcher.find()){
			result.add(matcher.group(0));  
		}
		return result;
	}
	
	/**
	 * 去掉字符串中满足表达式的内容
	 */
	public static String remove(String s,String type){
		Matcher matcher = Pattern.compile(type).matcher(s);
		return matcher.replaceAll("").trim();
	}
	
	public static void main(String[] args) {
		String s = "154671116048";
		System.out.println(matcher(s, PHONE));
	}
}
