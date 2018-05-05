/*package com.es.jetty;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

import org.apache.jasper.runtime.TldScanner;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.es.core.AppInitializer;

public class EmbeddedJetty {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedJetty.class);
    
    private static final int PORT = 9290;
    private static final String CONTEXT_PATH = "/";
    
    public static void main(String[] args) throws Exception {
        new EmbeddedJetty().startJetty(PORT);
    }

    @SuppressWarnings("rawtypes")
	private void startJetty(int port) throws Exception {
    	
    	Field f = TldScanner.class.getDeclaredField("systemUris");
    	f.setAccessible(true);
    	((Set)f.get(null)).clear();
    	
        LOGGER.info("Starting server at port {}", port);
        Server server = new Server(port);
        
        server.setHandler(getServletContextHandler());
        
        addRuntimeShutdownHook(server);
        
        server.start();
        LOGGER.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler() throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS); 
        contextHandler.setErrorHandler(null);
        
        contextHandler.setResourceBase("src/main/webapp");        
        contextHandler.setContextPath(CONTEXT_PATH);
        
        // JSP
        contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader()); 
        contextHandler.addServlet(JspServlet.class, "*.jsp");
        
        // Spring
        WebApplicationContext context = AppInitializer.getWebContext();
        contextHandler.addServlet(new ServletHolder("mvc-dispatcher", new DispatcherServlet(context)), "/");
        contextHandler.addEventListener(new ContextLoaderListener(context));
        
        return contextHandler;
    }
    
    private static void addRuntimeShutdownHook(final Server server) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if (server.isStarted()) {
                	server.setStopAtShutdown(true);
                    try {
                    	server.stop();
                    } catch (Exception e) {
                        System.out.println("Error while stopping jetty server: " + e.getMessage());
                        LOGGER.error("Error while stopping jetty server: " + e.getMessage(), e);
                    }
                }
            }
        }));
	}

}
*/