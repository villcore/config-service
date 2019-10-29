package com.duduyixia.config.server.bean;

import java.util.Objects;

public class WatchConfig {

    private String configFlatKey;
    private String md5;

    public WatchConfig() {}

    public WatchConfig(String configFlatKey, String md5) {
        this.configFlatKey = configFlatKey;
        this.md5 = md5;
    }

    public String getConfigFlatKey() {
        return configFlatKey;
    }

    public void setConfigFlatKey(String configFlatKey) {
        this.configFlatKey = configFlatKey;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WatchConfig that = (WatchConfig) o;
        return Objects.equals(configFlatKey, that.configFlatKey) &&
                Objects.equals(md5, that.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configFlatKey, md5);
    }

    @Override
    public String toString() {
        return "WatchConfig{" +
                "configFlatKey='" + configFlatKey + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }
}
