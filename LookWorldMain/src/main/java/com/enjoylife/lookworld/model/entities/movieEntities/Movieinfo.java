package com.enjoylife.lookworld.model.entities.movieEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class Movieinfo implements Serializable {
    @SerializedName("count")
    @Expose
    private String count;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("total")
    @Expose
    private String total;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<SubjectMovie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectMovie> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("subjects")
    @Expose
    private List<SubjectMovie> subjects = new ArrayList<>();

    @SerializedName("title")
    @Expose
    private String title;
}
