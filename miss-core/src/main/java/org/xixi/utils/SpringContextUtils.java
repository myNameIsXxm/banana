package org.xixi.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 *  
 * 获取spring容器，以访问容器中定义的其他bean  
 * @author XIXI
 */ 
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;  
  
    public void setApplicationContext(ApplicationContext applicationContext) {  
        SpringContextUtils.applicationContext = applicationContext;  
    }  
  
    static { 
    	if(applicationContext == null)
			applicationContext = new ClassPathXmlApplicationContext("default.xml");   
    }  
  
    public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    } 

}
