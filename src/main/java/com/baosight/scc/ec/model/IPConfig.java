package com.baosight.scc.ec.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by sam on 2014/9/4.
 */
@Entity
@Table(name="t_ec_ipConfig")
public class IPConfig {
    @Id
    private Integer id;
    private String ip;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;
    private int flag = 0; //设置机器标签，0：表示定时任务不在该机器运行，1：表示运行

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
