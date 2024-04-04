package com.example.multimathsolver.data.mathematicalAnalysis;

import com.example.multimathsolver.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SequenceLimitThread extends Thread{
    public SequenceLimitThread(String sequence) {
        this.sequence = sequence;
    }

    public String getXmlText() {
        return xmlText;
    }

    private String xmlText;
    private String sequence;
    private final String wolfram_url = "http://api.wolframalpha.com/v2/query?input=lim n->inf " + sequence + "&appid=" + BuildConfig.API_KEY;
    public void run() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(wolfram_url);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            xmlText = buffer.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
