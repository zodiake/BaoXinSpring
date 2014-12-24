package com.baosight.scc.ec.model;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;

/**
 * Created by zodiake on 2014/7/22.
 */
public class SourceUserInfo implements Serializable {
    private String id;
    private String token;

    public SourceUserInfo() {
    }

    public SourceUserInfo(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
