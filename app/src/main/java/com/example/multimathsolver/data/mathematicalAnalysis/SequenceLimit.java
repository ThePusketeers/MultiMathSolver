package com.example.multimathsolver.data.mathematicalAnalysis;

import androidx.annotation.WorkerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.example.multimathsolver.BuildConfig;



public class SequenceLimit {
    // Идентификатор приложения Wolfram Alpha API
    static String result;

    public static String solve(String sequence) {
        // Формируем URL запроса к Wolfram Alpha API
        String url = "http://api.wolframalpha.com/v2/query?input=lim n->inf " + sequence + "&appid=" + BuildConfig.API_KEY;
        SequenceLimitThread thread = new SequenceLimitThread(sequence);
        thread.start();
        try {
            thread.join();
            return thread.getXmlText();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}