package com.enjoylife.lookworld.model.entities.usBoxEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class UsBoxEntity implements Serializable {

    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("subjects")
    @Expose
    List<Subject> subjects = new ArrayList<Subject>();

    @SerializedName("title")
    @Expose
    private String title;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjectList() {
        return subjects;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjects = subjectList;
    }

}
