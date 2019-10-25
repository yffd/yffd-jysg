package com.yffd.jysg.springboot.demo.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "api_user_infos")
public class UserInfoEntity implements Serializable {
    private static final long serialVersionUID = 2558528298555926806L;
    @Id
    @Column(name = "aui_app_id")
    private String appId;

    @Column(name = "aui_app_secret")
    private byte[] appSecret;

    @Column(name = "aui_status")
    private int status;

    @Column(name = "aui_day_request_count")
    private int dayRequestCount;

    @Column(name = "aui_ajax_bind_ip")
    private String ajaxBindIp;

    @Column(name = "aui_mark")
    private String mark;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public byte[] getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(byte[] appSecret) {
        this.appSecret = appSecret;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDayRequestCount() {
        return dayRequestCount;
    }

    public void setDayRequestCount(int dayRequestCount) {
        this.dayRequestCount = dayRequestCount;
    }

    public String getAjaxBindIp() {
        return ajaxBindIp;
    }

    public void setAjaxBindIp(String ajaxBindIp) {
        this.ajaxBindIp = ajaxBindIp;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
