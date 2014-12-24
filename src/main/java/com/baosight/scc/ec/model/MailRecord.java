package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Charles on 2014/10/14.
 */
@Entity
@Table(name="T_EC_MAILRECORD")
//地址
public class MailRecord implements Serializable{
    @Id
    private String id;

    @NotNull
    private String mailToId;

    @NotNull
    private String mailTo;

    private String content;

    @NotNull
    private String billType;

    @NotNull
    private String subject;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar sendTime;

    private int isSuccess;

    public String getMailToId() {
        return mailToId;
    }

    public void setMailToId(String mailToId) {
        this.mailToId = mailToId;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Calendar getSendTime() {
        return sendTime;
    }

    @PrePersist
    public void setSendTime() {
        DateTime dt = new DateTime();
        this.sendTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MailRecord)) return false;

        MailRecord that = (MailRecord) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
