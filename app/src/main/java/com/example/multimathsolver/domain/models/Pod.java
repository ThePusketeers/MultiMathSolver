package com.example.multimathsolver.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pod {

    @SerializedName("title")
    private String title;
    @SerializedName("subpods")
    private List<Subpod> subpods;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subpod> getSubpods() {
        return subpods;
    }

    public void setSubpods(List<Subpod> subpods) {
        this.subpods = subpods;
    }

    @Override
    public String toString() {
        return "Pod{" +
                "title='" + title + '\'' +
                ", subpods=" + subpods +
                '}';
    }

}
