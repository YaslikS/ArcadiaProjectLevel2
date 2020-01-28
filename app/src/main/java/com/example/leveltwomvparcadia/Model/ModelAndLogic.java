package com.example.leveltwomvparcadia.Model;

import android.util.Log;
import com.example.leveltwomvparcadia.MainInterface;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ModelAndLogic implements MainInterface.ModelAndLogic {
    private static final String TAG = "MainModelAndLogic";
    private static final String part1URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String part2URL = "&units=metric&appid=eee45cd39160ed2faf2a2e7a0715c4e6";
    private static String finalURL = "";
    private String _main = "";
    private String _description = "";
    private String _temp = "";
    private String _windSpeed = "";
    private String _jsonString = "";
    private String _cityName = "";
    private boolean _isOnlineFlag;

    public String get_cityName() {
        return _cityName;
    }
    public String get_jsonString() {
        return _jsonString;
    }
    public String get_main() {
        return _main;
    }
    public String get_temp() {
        return _temp;
    }
    public String get_description() {
        return _description;
    }
    public String get_windSpeed() {
        return _windSpeed;
    }
    public void set_isOnlineFlag(boolean _isOnlineFlag) {
        this._isOnlineFlag = _isOnlineFlag;
    }

    public void jobWeather(String newCityName){
        finalURL = part1URL + newCityName + part2URL;
        GetWeather weather = new GetWeather();
        try {
            _jsonString = weather.execute(finalURL).get();
            Log.i("jsonString", _jsonString);
            parsingJsonString(_jsonString);
            Log.d(TAG, "jobWeather() --- успешно");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "jobWeather() --- Exception\n");
        }
        Log.d(TAG, "jobWeather()");
    }

    public void parsingJsonString(String jsonString) {
        try{
            if(!_isOnlineFlag) {
                _jsonString = jsonString;
                Log.d(TAG, "parsingJsonString() --- оффлайн режим");
            }
            JSONObject jsonObject = new JSONObject(_jsonString);
            String weatherData = jsonObject.getString("weather");
            _temp = jsonObject.getString("main");
            _windSpeed = jsonObject.getString("wind");
            _cityName = jsonObject.getString("name");
            Log.d(TAG, "parsingJsonString() --- запись (_temp, _windSpeed, _cityName)");

            JSONArray array = new JSONArray(weatherData);
            for(int i = 0; i < array.length(); ++i){
                JSONObject weatherPart = array.getJSONObject(i);
                _main = weatherPart.getString("main");
                _description = weatherPart.getString("description");
            }
            Log.d(TAG, "parsingJsonString() --- запись (_main, _description)");

            JSONObject mainPart = new JSONObject(_temp);
            _temp = mainPart.getString("temp");

            JSONObject windPart = new JSONObject(_windSpeed);
            _windSpeed = windPart.getString("speed");
            Log.d(TAG, "parsingJsonString() --- запись (_windSpeed)");

            Log.i("wind", _windSpeed);
            Log.i("temperature", _temp);
            Log.i("main", _main);
            Log.i("description", _description);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "parsingJsonString() --- Exception");
        }
        Log.d(TAG, "parsingJsonString()");
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        Log.d(TAG, "isOnline()");
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            Log.d(TAG, "isOnline() --- успешно");
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "isOnline() --- IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "isOnline() --- InterruptedException");
        }
        return false;
    }
}
