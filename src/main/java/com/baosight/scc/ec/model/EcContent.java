package com.baosight.scc.ec.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by zodiake on 2014/7/16.
 */
@Entity
@Table(name="t_ec_content")
public class EcContent implements Serializable{
    @Id
    private String id;

    private String content;

    @OneToOne(mappedBy = "content")
    private Item item;

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
