package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * environment
 */
public class JavaEnvironmentPropertiesConfigSource implements ConfigSource {

    private final Map<String, String> properties;

    public JavaEnvironmentPropertiesConfigSource() {
        Map environmentProperties = System.getenv();
        this.properties = new HashMap<>(environmentProperties);
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
        return "Java Environment Properties";
    }
}
