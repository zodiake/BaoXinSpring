package com.baosight.scc.ec.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by sam on 2014/6/27.
 */
@Entity
@Table(name="T_ec_insideLetter")
public class InsideLetter implements Serializable {
    @Id
    private String id;
    private Integer letterType = 1; //站内信类型，0：系统通知，1：其他，默认其他
    private Integer letterStatus=0; //站内信状态，0：未读，1：已读，2：已删除
    private String content; //站内信内容
    private String title;  //站内信标题
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private EcUser sendUser; //发件人
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private EcUser receiveUser;//收件人
    private int flag; //标记 默认值为0,交流记录最新
    private String url;
    private String refers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLetterType() {
        return letterType;
    }

    public void setLetterType(Integer letterType) {
        this.letterType = letterType;
    }

    public Integer getLetterStatus() {
        return letterStatus;
    }

    public void setLetterStatus(Integer letterStatus) {
        this.letterStatus = letterStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public EcUser getSendUser() {
        return sendUser;
    }

    public void setSendUser(EcUser sendUser) {
        this.sendUser = sendUser;
    }

    public EcUser getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(EcUser receiveUser) {
        this.receiveUser = receiveUser;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsideLetter that = (InsideLetter) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    public String getRefers() {
        return refers;
    }

    public void setRefers(String refers) {
        this.refers = refers;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "InsideLetter{" +
                "id='" + id + '\'' +
                '}';
    }

}
