package com.example.leveltwomvparcadia;

public interface MainInterface {
    interface View{
        void writeFile(String counter);
        String readFile();
        void showTextIndicatorDegree(String str);
        void showTextMeasureDegree(String str);
        void showTextWeatherDetails(String str);
        void showTextTimeSearchTView(String str);
        void showNoInternetTV();
        void hideNoInternetTV();
        void showTextCityName(String str);
        void showProgressBar();
        void hideProgressBar();
        void installBackground(String str);
        void hideTVDegree();
        void showTVDegree();
        void showToastNoInternet();
    }

    interface Presenter{
        void presenterJob();
        void set_cityName(String _cityName);
    }

    interface ModelAndLogic{
        boolean isOnline();
        String get_temp();
        String get_main();
        String get_description();
        String get_windSpeed();
        void jobWeather(String newCityName);
        String get_jsonString();
        String get_cityName();
        void parsingJsonString(String jsonString);
        void set_isOnlineFlag(boolean _isOnlineFlag);
    }
}