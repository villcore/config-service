package com.duduyixia.config.server.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * created by WangTao on 2019-10-16
 */
public class ClientConfigInfo implements Serializable {

    private String clientIp;
    private String md5;
    private long reportTimeMillis;

    public ClientConfigInfo() {
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getReportTimeMillis() {
        return reportTimeMillis;
    }

    public void setReportTimeMillis(long reportTimeMillis) {
        this.reportTimeMillis = reportTimeMillis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientConfigInfo that = (ClientConfigInfo) o;
        return reportTimeMillis == that.reportTimeMillis &&
                Objects.equals(clientIp, that.clientIp) &&
                Objects.equals(md5, that.md5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientIp, md5, reportTimeMillis);
    }

    @Override
    public String toString() {
        return "ClientReportInfo{" +
                "clientIp='" + clientIp + '\'' +
                ", md5='" + md5 + '\'' +
                ", reportTimeMillis=" + reportTimeMillis +
                '}';
    }
}
