package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 本地读取配置文件
 */
public class ApplicationFilePropertiesConfigSource implements ConfigSource {
    
    private static final String APPLICATION_FILE_LOCATION = "META-INF/application.properties";

    /**
     * Java 系统属性最好通过本地变量保存，使用 Map 保存，尽可能运行期不去调整
     * -Dapplication.name=user-web
     */
    private final Map<String, String> properties;

    public ApplicationFilePropertiesConfigSource() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL propertiesFileURL = classLoader.getResource(APPLICATION_FILE_LOCATION);
        Properties properties = new Properties();
        try {
            properties.load(propertiesFileURL.openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.properties = new HashMap<>((Map)properties);
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "Application File Properties";
    }
}
