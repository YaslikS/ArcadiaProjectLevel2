package com.example.leveltwomvparcadia.Model;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetWeather extends AsyncTask<String, Void, String> {
    private static final String TAG = "Weather";
    private String _cityName;
    private String _main;
    private String _description;
    private String _temp;

    @Override
    protected String doInBackground(String... address) {
        try {
            URL url = new URL(address[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d(TAG, "doInBackground() --- HttpURLConnection\n");

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            Log.d(TAG, "jobWeather() --- InputStream, InputStreamReader\n");

            int data = isr.read();
            StringBuilder content = new StringBuilder();
            char ch;
            while (data != -1) {
                ch = (char) data;
                content.append(ch);
                data = isr.read();
            }
            Log.d(TAG, "doInBackground() --- Exception\n");
            return content.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground() --- MalformedURLException\n");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground() --- IOException\n");
        }
        return null;
    }
}
