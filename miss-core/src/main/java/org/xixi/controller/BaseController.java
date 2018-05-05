package org.xixi.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.xixi.model.HeadModel;
import org.xixi.model.User;

public class BaseController {

	public void doHead(HttpServletResponse response){
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers",
				"X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
	}
	
	public void write(HttpServletResponse response, String data) {
		PrintWriter printWriter = null;
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		try {
			printWriter = response.getWriter();
			printWriter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
	
	public List<List<HeadModel>> formatHead(List<HeadModel> list) {
		Collections.sort(list,new Comparator<HeadModel>() {
			@Override
			public int compare(HeadModel arg0, HeadModel arg1) {
				return arg0.getSortNum()-arg1.getSortNum();
			}
		});
		List<List<HeadModel>> result = new ArrayList<List<HeadModel>>();
		int max = 1;
		for(HeadModel h:list){
			if(h.getInlineNum()>max){
				max=h.getInlineNum();
			}
		}
		for(int i=0;i<max;i++){
			result.add(new ArrayList<HeadModel>());
		}
		
		for(HeadModel h:list){
			result.get(h.getInlineNum()-1).add(h);
		}
		return result;
	}
	

}

