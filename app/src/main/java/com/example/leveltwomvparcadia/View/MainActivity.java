package com.example.leveltwomvparcadia.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.leveltwomvparcadia.R;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leveltwomvparcadia.MainInterface;
import com.example.leveltwomvparcadia.Presenter.MainPresenter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MainInterface.View {
    final static String nameSaveViewIndicatorDegreeTV = "nameSaveViewIndicatorDegreeTV";
    final static String nameSaveViewWeatherDetailsTV = "nameSaveViewWeatherDetailsTV";
    final static String nameSaveViewTimeSearchTV = "nameSaveViewTimeSearchTV";
    final static String nameSaveViewEditCityET = "nameSaveViewEditCityET";
    final static String nameSaveViewCityNameTV = "nameSaveViewCityNameTV";
    private final String FILENAME_COUNTER = "counterFile";
    private static final String TAG = "MainActivity";
    private TextView indicatorDegreeTV, measureDegreeTV, weatherDetailsTV, timeSearchTViewTV, noInternetTV, nowCityName, degreeTV;
    private EditText editCityET;
    private ImageView backgroundPic;
    private String _cityStr;
    private MainInterface.Presenter mPresenter;
    private ProgressBar mainProgressBar;
    StartProgressBar SPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorDegreeTV = (TextView) findViewById(R.id.indicatorDegreeTV);
        measureDegreeTV = (TextView) findViewById(R.id.measureDegreeTV);
        degreeTV = (TextView) findViewById(R.id.degreeTextTV);
        weatherDetailsTV = (TextView) findViewById(R.id.weatherDetailsTV);
        timeSearchTViewTV = (TextView) findViewById(R.id.timeSearchTV);
        editCityET = (EditText) findViewById(R.id.editCityET);
        noInternetTV = (TextView) findViewById(R.id.notInternetTV);
        nowCityName = (TextView) findViewById(R.id.cityNameTV);
        mainProgressBar = (ProgressBar) findViewById(R.id.mainProgressBar);
        backgroundPic = (ImageView) findViewById(R.id.backgroundIV);

        mPresenter = new MainPresenter(this);

        /*searchCityPic.setOnClickListener(new View.OnClickListener() {
            @Override
            /public void onClick(View v) {
                if(TextUtils.isEmpty(editCityET.getText().toString())) { return; }
                _cityStr = editCityET.getText().toString();

                SPB = new StartProgressBar();
                SPB.execute();

                startPresenterJob();


                Log.d(TAG, "onClick()");
            }
        });*/
        Log.d(TAG, "onCreate()");
    }

    public void onClick(View v) {
        if(TextUtils.isEmpty(editCityET.getText().toString())) { return; }
        _cityStr = editCityET.getText().toString();


        startPresenterJob();
        SPB = new StartProgressBar();
        SPB.execute();

        //startPresenterJob();

        Log.d(TAG, "onClick()");
    }

    private void startPresenterJob(){
        mPresenter.set_cityName(_cityStr);
        mPresenter.presenterJob();
        Log.d(TAG, "startPresenterJob()");
    }

    @Override
    public void installBackground(String str){
        if((str.contains("mist"))|| (str.contains("haze"))){
            backgroundPic.setImageResource(R.drawable.haze_weather_pic2);
        }else { if (str.contains("sun")) {
            backgroundPic.setImageResource(R.drawable.sun_weather_pic);
        } else { if (str.contains("cloud")) {
            backgroundPic.setImageResource(R.drawable.cloud_weather_pic2);
        } else { if (str.contains("rain")) {
            backgroundPic.setImageResource(R.drawable.rain_weather_pic);
        } else { if (str.contains("rain")) {
            backgroundPic.setImageResource(R.drawable.rain_weather_pic);
        } else { if (str.contains("snow")) {
            backgroundPic.setImageResource(R.drawable.snow_weather_pic);
        }else{
            backgroundPic.setImageResource(R.drawable.main_weather_pic);
        } } } } } }
        Log.d(TAG, "installBackground()");
    }

    @Override
    public void showToastNoInternet(){
        Toast.makeText(this, "Нет интернет-соединения", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void showTextCityName(String str){
        nowCityName.setText(str);
        Log.d(TAG, "showTextCityName()");
    }
    @Override
    public void showTextIndicatorDegree(String str) {
        indicatorDegreeTV.setText(str);
        Log.d(TAG, "showTextIndicatorDegree()");
    }
    @Override
    public void showTextMeasureDegree(String str) {
        measureDegreeTV.setText(str);
        Log.d(TAG, "showTextMeasureDegree()");
    }
    @Override
    public void showTextWeatherDetails(String str) {
        weatherDetailsTV.setText(str);
        Log.d(TAG, "showTextWeatherDetailsTV()");
    }
    @Override
    public void showTextTimeSearchTView(String str) {
        timeSearchTViewTV.setText(str);
        Log.d(TAG, "showTextTimeSearchTView()");
    }
    @Override
    public void hideTVDegree(){
        measureDegreeTV.setVisibility(View.INVISIBLE);
        degreeTV.setVisibility(View.INVISIBLE);
        Log.d(TAG, "hideTVDegree()");
    }
    @Override
    public void showTVDegree(){
        measureDegreeTV.setVisibility(View.VISIBLE);
        degreeTV.setVisibility(View.VISIBLE);
        Log.d(TAG, "showTextTimeSearchTView()");
    }

    public void showProgressBar(){
        mainProgressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "showProgressBar()");
    }
    public void hideProgressBar(){
        mainProgressBar.setVisibility(View.INVISIBLE);
        Log.d(TAG, "hideProgressBar()");
    }
    public void showNoInternetTV(){
        noInternetTV.setVisibility(View.VISIBLE);
        Log.d(TAG, "showNoInternetTV()");
    }
    public void hideNoInternetTV(){
        noInternetTV.setVisibility(View.INVISIBLE);
        Log.d(TAG, "hideNoInternetTV()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(nameSaveViewIndicatorDegreeTV, indicatorDegreeTV.getText().toString());
        outState.putString(nameSaveViewWeatherDetailsTV, weatherDetailsTV.getText().toString());
        outState.putString(nameSaveViewTimeSearchTV, timeSearchTViewTV.getText().toString());
        outState.putString(nameSaveViewEditCityET, editCityET.getText().toString());
        outState.putString(nameSaveViewCityNameTV, nowCityName.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String indicatorDegreeTVtext = savedInstanceState.getString(nameSaveViewIndicatorDegreeTV);
        String weatherDetailsTVtext = savedInstanceState.getString(nameSaveViewWeatherDetailsTV);
        String timeSearchTViewTVtext = savedInstanceState.getString(nameSaveViewTimeSearchTV);
        String editCityETtext = savedInstanceState.getString(nameSaveViewEditCityET);
        String cityStrTVtext = savedInstanceState.getString(nameSaveViewCityNameTV);

        indicatorDegreeTV.setText(indicatorDegreeTVtext);
        weatherDetailsTV.setText(weatherDetailsTVtext);
        timeSearchTViewTV.setText(timeSearchTViewTVtext);
        editCityET.setText(editCityETtext);
        nowCityName.setText(cityStrTVtext);
        installBackground(weatherDetailsTVtext);
    }

    public void writeFile(String counter){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(  // отрываем поток для записи
                    openFileOutput(FILENAME_COUNTER, MODE_PRIVATE)));   // пишем данные
            bw.write(counter);
            bw.close();     // закрываем поток
            Log.d(TAG, "writeFile() --- успешно");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "writeFile() --- IOException");
        }
        Log.d(TAG, "writeFile()");
    }
    public String readFile() {
        String counter = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(   // открываем поток для чтения
                    openFileInput(FILENAME_COUNTER)));
            counter = br.readLine();    // читаем содержимое
            Log.d(TAG, "readFile() --- успешно");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "readFile() --- IOException");
        }
        Log.d(TAG, "writeFile()");
        return counter;
    }

    class StartProgressBar extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
            Log.d(TAG, "StartProgressBar() --- onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            publishProgress();
            try {
                TimeUnit.SECONDS.sleep(2);
                Log.d(TAG, "StartProgressBar() --- doInBackground");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (null);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            showProgressBar();
            Log.d(TAG, "onProgressUpdate() --- showProgressBar()");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hideProgressBar();
            Log.d(TAG, "StartProgressBar() --- onPostExecute");
        }
    }
}