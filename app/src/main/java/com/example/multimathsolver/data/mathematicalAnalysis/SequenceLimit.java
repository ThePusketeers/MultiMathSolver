package com.example.multimathsolver.data.mathematicalAnalysis;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
    private String appId = BuildConfig.API_KEY;

    public void solve(String sequence) {
        // Формируем URL запроса к Wolfram Alpha API
        String url = "http://api.wolframalpha.com/v2/query?input=lim n->inf " + sequence + "&appid=" + appId;
        new GetData().execute(url);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                return buffer.toString();
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
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);

        }
    }
}
