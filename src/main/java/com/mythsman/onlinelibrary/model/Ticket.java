package com.mythsman.onlinelibrary.model;

import java.util.Date;

/**
 * Created by myths on 5/20/17.
 */
public class Ticket {
    int id;
    int uid;
    String ticket;
    Date expire;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
