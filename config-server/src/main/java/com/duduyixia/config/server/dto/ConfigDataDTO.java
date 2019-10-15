package com.duduyixia.config.server.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * created by WangTao on 2019-10-15
 */
public class ConfigDataDTO implements Serializable {

    private String value;
    private String md5;
    private boolean isBeta;
    private boolean markDeleted;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public void setBeta(boolean beta) {
        isBeta = beta;
    }

    public boolean isMarkDeleted() {
        return markDeleted;
    }

    public void setMarkDeleted(boolean markDeleted) {
        this.markDeleted = markDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigDataDTO that = (ConfigDataDTO) o;
        return isBeta == that.isBeta &&
                markDeleted == that.markDeleted &&
                Objects.equals(value, that.value) &&
                Objects.equals(md5, that.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, md5, isBeta, markDeleted);
    }

    @Override
    public String toString() {
        return "ConfigDataDTO{" +
                "value='" + value + '\'' +
                ", md5='" + md5 + '\'' +
                ", isBeta=" + isBeta +
                ", markDeleted=" + markDeleted +
                '}';
    }
}
