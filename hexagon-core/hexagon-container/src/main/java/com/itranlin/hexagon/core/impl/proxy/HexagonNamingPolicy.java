package com.itranlin.hexagon.core.impl.proxy;

import org.springframework.cglib.core.DefaultNamingPolicy;

/**
 * Hexagon naming policy.
 */
public class HexagonNamingPolicy extends DefaultNamingPolicy {

    private final String pluginId;

    /**
     * Instantiates a new Hexagon naming policy.
     *
     * @param pluginId the plugin id
     */
    public HexagonNamingPolicy(String pluginId) {
        this.pluginId = pluginId;
    }

    @Override
    protected String getTag() {
        return super.getTag() + "$$HEXAGON$$" + pluginId;
    }
}
