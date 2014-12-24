package com.baosight.scc.ec.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by Charles on 2014/7/18.
 */
@Entity
@Table(name="T_ec_infoContent")
public class InfoContent {
    @Id
    private String id;

    private String content;

    @OneToOne(mappedBy = "infoContent")
    private Information info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Information getInfo() {

        return info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }
}
