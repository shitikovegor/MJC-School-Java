package com.epam.esm.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Class {@code ApplicationInitializer} uses to initialize the application.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(context));
        context.setServletContext(servletContext);


        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcherServlet", new DispatcherServlet(context));
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
