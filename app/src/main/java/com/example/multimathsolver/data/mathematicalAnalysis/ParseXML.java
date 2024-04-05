package com.example.multimathsolver.data.mathematicalAnalysis;

public class ParseXML {
    private String xml;
    public ParseXML(String xml) {
        this.xml = xml;
    }

    public Double parse() {
        return getResult(getPlainText());
    }

    //lim_(n->∞) ... = ...
    private Double getResult(String plainText) {
        int startIndex = plainText.indexOf("=");
        return Double.parseDouble(plainText.substring(startIndex));
    }


    //<plaintext>........</plaintext>
    private String getPlainText() {
        int startIndex = xml.indexOf("<plaintext>")+11;
        int endIndex = xml.indexOf("</plaintext>");
        return xml.substring(startIndex, endIndex);
    }

}
