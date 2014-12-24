package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.EcUser;

import java.util.Calendar;

/**
 * Created by sam on 2014/8/19.
 */
public class CommentJson {
    private String id;
    private String name;
    private EcUser user;
    private Calendar createdTime;
    private String content;
    private int hasName;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHasName() {
        return hasName;
    }

    public void setHasName(int hasName) {
        this.hasName = hasName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
