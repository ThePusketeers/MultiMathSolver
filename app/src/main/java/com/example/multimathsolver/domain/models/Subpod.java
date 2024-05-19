package com.example.multimathsolver.domain.models;

import com.google.gson.annotations.SerializedName;

public class Subpod {
    @SerializedName("plaintext")
    private String result;

    public String getPlaintext() {
        return result;
    }
    public void setPlaintext(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Subpod{" +
                "result='" + result + '\'' +
                '}';
    }
}
