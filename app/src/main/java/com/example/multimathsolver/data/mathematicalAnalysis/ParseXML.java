package com.example.multimathsolver.data.mathematicalAnalysis;

public class ParseXML {
    private String xml;
    public ParseXML(String xml) {
        this.xml = xml;
    }

    public String parse() {
        return getResult(getPlainText());
    }

    //lim_(n->∞) ... = ...
    private String getResult(String plainText) {
        int startIndex = plainText.indexOf("=") + 2;
        return plainText.substring(startIndex);
    }


    //<plaintext>........</plaintext>
    private String getPlainText() {
        int startIndex = xml.indexOf("<plaintext>")+11;
        int endIndex = xml.indexOf("</plaintext>");
        return xml.substring(startIndex, endIndex);
    }

}
