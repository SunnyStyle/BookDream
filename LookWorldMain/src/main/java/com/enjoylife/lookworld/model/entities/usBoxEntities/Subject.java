package com.enjoylife.lookworld.model.entities.usBoxEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by wangfang on 2017/9/21.
 */

public class Subject implements Serializable{
    @SerializedName("box")
    @Expose
    private String box;

    @SerializedName("new")
    @Expose
    private String new_;

    @SerializedName("rank")
    @Expose
    private String rank;

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getNew_() {
        return new_;
    }

    public void setNew_(String new_) {
        this.new_ = new_;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public SubjectSmall getSubject() {
        return subject;
    }

    public void setSubject(SubjectSmall subject) {
        this.subject = subject;
    }

    @SerializedName("subject")
    @Expose
    private SubjectSmall subject;
}
