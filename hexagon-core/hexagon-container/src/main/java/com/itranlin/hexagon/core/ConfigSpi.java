package com.itranlin.hexagon.core;

/**
 * Config spi.
 */
@SuppressWarnings("unused")
public interface ConfigSpi {

    /**
     * 获得 property.
     *
     * @param key the key
     * @param def the def
     * @return the property
     */
    String getProperty(String key, String def);

    /**
     * 默认 Empty config spi.
     */
    class EmptyConfigSpi implements ConfigSpi {

        @Override
        public String getProperty(String key, String def) {
            return def;
        }

    }
}
