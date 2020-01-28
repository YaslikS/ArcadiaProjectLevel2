package com.example.leveltwomvparcadia.Presenter;

import android.util.Log;
import com.example.leveltwomvparcadia.MainInterface;
import com.example.leveltwomvparcadia.Model.ModelAndLogic;
import java.util.Date;

public class MainPresenter implements MainInterface.Presenter {
    private static final String TAG = "MainPresenter";

    private MainInterface.View mView;
    private MainInterface.ModelAndLogic modelAndLogic;

    private String indicatorDegreeTV, measureDegreeTV, weatherDetailsTV, timeSearchTViewTV;
    private String _cityName = "", _temp, _description, _main, _wind;
    private boolean _isOnlineFlag;

    public void set_cityName(String _cityName) {
        this._cityName = _cityName;
    }

    public MainPresenter(MainInterface.View mView){
        this.mView = mView;
        this.modelAndLogic = new ModelAndLogic();

        presenterJob();

        Log.d(TAG, "ConstructorPresenter");
    }

    public void presenterJob(){
        emptyCity();
        checkingTheInternet();

        if (!_isOnlineFlag) {               //  если инета нет
            Log.d(TAG, "presenterJob() --- инета нет");
            String jsonFromFile = mView.readFile();
            if(jsonFromFile.equals("")) {
                Log.d(TAG, "presenterJob() --- инета нет --- внутренний файл пустой");
                emptyCity();
            }
            else{
                modelAndLogic.parsingJsonString(jsonFromFile);
                Log.d(TAG, "presenterJob() --- инета нет --- внутренний файл пропарсен");
                installViewParameters();
            }
        } else {                            // ...если он есть
            Log.d(TAG, "presenterJob() --- инет есть");
            if(!_cityName.equals("")) {
                Log.d(TAG, "presenterJob() --- инет есть --- первый запуск");
                modelAndLogic.jobWeather(_cityName);
                mView.writeFile(modelAndLogic.get_jsonString());
                installViewParameters();
            }
        }
        Log.d(TAG, "presenterJob()");
    }

    private void installViewParameters(){
        _description = modelAndLogic.get_description();
        _temp = modelAndLogic.get_temp();
        _main = modelAndLogic.get_main();
        _wind = modelAndLogic.get_windSpeed();

        mView.showTVDegree();
        mView.installBackground(_description);
        mView.showTextCityName(modelAndLogic.get_cityName());
        mView.showTextIndicatorDegree(_temp);
        mView.showTextWeatherDetails(_main + ", " + _description + "\nWind " + _wind + " m/s");
        Date date = new Date();
        mView.showTextTimeSearchTView(date.toString());
        Log.d(TAG, "installViewParameters()");
    }

    private void emptyCity(){
        mView.showTextCityName("Введите город");
        mView.showTextIndicatorDegree("");
        mView.showTextWeatherDetails("");
        mView.hideTVDegree();
        Log.d(TAG, "emptyCity()");
    }

    private void checkingTheInternet(){
        if (!modelAndLogic.isOnline()) {    //  если инета нет
            mView.showNoInternetTV();
            mView.showToastNoInternet();
            _isOnlineFlag = false;
            modelAndLogic.set_isOnlineFlag(false);
        } else {                            // ...если он есть
            mView.hideNoInternetTV();
            _isOnlineFlag = true;
            modelAndLogic.set_isOnlineFlag(true);
        }
        Log.d(TAG, "checkingTheInternet()");
    }
}