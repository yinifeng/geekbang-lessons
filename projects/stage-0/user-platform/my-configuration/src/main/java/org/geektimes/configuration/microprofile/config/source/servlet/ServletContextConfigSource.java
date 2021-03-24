package org.geektimes.configuration.microprofile.config.source.servlet;

import org.eclipse.microprofile.config.spi.ConfigSource;
import org.geektimes.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class ServletContextConfigSource implements ConfigSource {

    private final ServletContext servletContext;
    
    private final MapBasedConfigSource configSource;

    public ServletContextConfigSource(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.configSource=new MapBasedConfigSource("ServletContext Init Parameters", 500){
            @Override
            protected void prepareConfigData(Map configData) throws Throwable {
                Enumeration<String> parameterNames = servletContext.getInitParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String parameterName = parameterNames.nextElement();
                    configData.put(parameterName, servletContext.getInitParameter(parameterName));
                }
            }
        };
    }

    @Override
    public final String getName() {
        return this.configSource.getName();
    }

    @Override
    public final int getOrdinal() {
        return this.configSource.getOrdinal();
    }

    @Override
    public Set<String> getPropertyNames() {
        return this.configSource.getPropertyNames();
    }

    @Override
    public String getValue(String propertyName) {
        return this.configSource.getValue(propertyName);
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
