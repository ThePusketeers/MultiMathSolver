package com.example.multimathsolver.domain.models;

import com.google.gson.annotations.SerializedName;

public class LimitResponse {
    @SerializedName("queryresult")
    private LimitResult limitResult;

    public LimitResult getLimitResult() {
        return limitResult;
    }

    public void setLimitResult(LimitResult limitResult) {
        this.limitResult = limitResult;
    }

    @Override
    public String toString() {
        return "LimitResponse{" +
                "limitResult=" + limitResult +
                '}';
    }
}
