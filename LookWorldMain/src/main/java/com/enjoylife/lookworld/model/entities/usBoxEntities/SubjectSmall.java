package com.enjoylife.lookworld.model.entities.usBoxEntities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfang on 2017/9/21.
 */

public class SubjectSmall {
    @SerializedName("rating")
    @Expose
    private Rating rating;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();

    @SerializedName("title")
    @Expose
    private String title;

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    @SerializedName("casts")
    @Expose
    private List<Cast> casts = new ArrayList<Cast>();

    @SerializedName("collect_count")
    @Expose
    private String collect_count;

    @SerializedName("original_title")
    @Expose
    private String original_title;

    @SerializedName("subtype")
    @Expose
    private String subtype;

    @SerializedName("directors")
    @Expose
    private List<Directory> directors = new ArrayList<>();

    @SerializedName("year")
    @Expose
    private String year;

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private Image images;

    @SerializedName("alt")
    @Expose
    private String alt;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public List<Directory> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Directory> directors) {
        this.directors = directors;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private String id;

}
