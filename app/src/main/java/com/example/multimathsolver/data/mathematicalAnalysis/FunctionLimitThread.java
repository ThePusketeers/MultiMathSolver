package com.example.multimathsolver.data.mathematicalAnalysis;

import com.example.multimathsolver.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FunctionLimitThread extends Thread{
    public FunctionLimitThread(String sequence, Double strivesFor) {
        if (strivesFor == Double.POSITIVE_INFINITY)
            wolfram_url = "https://api.wolframalpha.com/v2/query?input=lim x->inf (" + sequence + ")&appid=" + BuildConfig.API_KEY;
        else if (strivesFor == Double.NEGATIVE_INFINITY)
            wolfram_url = "https://api.wolframalpha.com/v2/query?input=lim x->(-inf) (" + sequence + ")&appid=" + BuildConfig.API_KEY;
        else
            wolfram_url = "https://api.wolframalpha.com/v2/query?input=lim x->" + strivesFor + "(" + sequence + ")&appid=" + BuildConfig.API_KEY;
    }

    public String getXmlText() {
        return xmlText;
    }
    private String xmlText;
    private final String wolfram_url;
    public void run() {
        final URL url;
        try {
            url = new URL(wolfram_url);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                final StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                xmlText = content.toString();
            } catch (final Exception ex) {
                ex.printStackTrace();
                xmlText = "";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
