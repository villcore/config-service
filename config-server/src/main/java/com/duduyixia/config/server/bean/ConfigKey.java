package com.duduyixia.config.server.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * created by WangTao on 2019-09-20
 */
public class ConfigKey implements Serializable {

    private String namespace;
    private String env;
    private String app;
    private String group;
    private String config;


    public static ConfigKey valueOf(String namespace, String evn, String app, String group, String config) {
        return new ConfigKey(namespace, evn, app, group, config);
    }

    private ConfigKey(String namespace, String env, String app, String group, String config) {
        this.namespace = namespace;
        this.env = env;
        this.app = app;
        this.group = group;
        this.config = config;
    }

    private ConfigKey() {}

    public String getNamespace() {
        return namespace;
    }

    public String getEnv() {
        return env;
    }

    public String getApp() {
        return app;
    }

    public String getGroup() {
        return group;
    }

    public String getConfig() {
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigKey that = (ConfigKey) o;
        return namespace.equals(that.namespace) &&
                env.equals(that.env) &&
                app.equals(that.app) &&
                group.equals(that.group) &&
                config.equals(that.config);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, env, app, group, config);
    }

    @Override
    public String toString() {
        return "ConfigDataKey{" +
                "namespace='" + namespace + '\'' +
                ", env='" + env + '\'' +
                ", app='" + app + '\'' +
                ", group='" + group + '\'' +
                ", config='" + config + '\'' +
                '}';
    }

    public static String toFlatKey(ConfigKey configKey) {
        return new StringBuilder()
                .append(configKey.namespace).append(".")
                .append(configKey.env).append(".")
                .append(configKey.app).append(".")
                .append(configKey.group).append(".")
                .append(configKey.config)
                .toString();
    }

    public static ConfigKey formFlatKey(String flatKey) {
        Objects.requireNonNull(flatKey);
        String[] flatKeyParts = flatKey.split(".");
        if (flatKeyParts.length != 5) {
            throw new IllegalArgumentException("Illegal flat config key : " + flatKey);
        }

        return ConfigKey.valueOf(flatKeyParts[0], flatKeyParts[1], flatKeyParts[2], flatKeyParts[3], flatKeyParts[4]);
    }
}
