package com.example.multimathsolver.domain.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LimitResult {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("inputstring")
    private String inputstring;
    @SerializedName("pods")
    private List<Pod> pods;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getInputstring() {
        return inputstring;
    }

    public void setInputstring(String inputstring) {
        this.inputstring = inputstring;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }

    @Override
    public String toString() {
        return "LimitResult{" +
                "success=" + success +
                ", inputstring='" + inputstring + '\'' +
                ", pods=" + pods +
                '}';
    }
}
