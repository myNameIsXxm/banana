package org.xixi.view;

import java.util.Locale;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

@Deprecated
public class XmlViewResolver implements ViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MarshallingView view = new MarshallingView();
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //marshaller.setPackagesToScan("com.es.entity");
        //marshaller.setClassesToBeBound(JnhBmZclbN.class);
        view.setMarshaller(marshaller);
        return view;
    }

}