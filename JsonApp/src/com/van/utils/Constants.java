package com.van.utils;

import android.os.Environment;

public class Constants {
    public static final String ACTION_ABOUT_US_ACTIVITY = "com.bc.big.ABOUT_US";
    public static final String ACTION_EPISODE_ACTIVITY = "com.bc.big.EPISODE";
    public static final String ACTION_HELP_ACTIVITY = "com.bc.big.HELP";
    public static final String ACTION_HOME_ACTIVITY = "com.bc.big.HOME";
    public static final String ACTION_MAIN_ACTIVITY = "com.van.jsonapp.MainActivity";
    public static final String ACTION_SERIES_EDIT_ACTIVITY = "com.bc.big.SERIES_EDIT";
    public static final String ANDROID_CACHE_DIRECTORY_NAME = "cache";
    public static final String ANDROID_SD_CARD_ROOT_DIRECTORY = "JsonApp";
    public static final String ANDROID_SD_CARD_ROOT_DIRECTORY_SECOND = "data";
    public static final String APPID = "appid";
    public static final String APPID_VALUE = "6";
    public static final String APPLICATION_PACKAGE = "com.van.jsonapp";
    public static final String ASSETS_DIRECTORY_NAME = "assets";
    public static final String BUY_SEARCH_URL2 = "http://bcleg-2114693072.eu-west-1.elb.amazonaws.com/bcstorefront/search/PP0001?appid=3&versiontype=1&appversion=1.2";
    public static final String IMAGES = "images";
    public static final String FEEDBACK_EMAIL_ADDRESS = "van77@hotmail.ru";
    public static final String FILE_EXTENSION = ".jpg";
    public static final String LANGUAGE_ENGLISH = "english";
    public static final String LANGUAGE_JAPANESE = "japanese";
    public static final String LANGUAGE_SPANISH = "spanish";
    public static final String MEDIA_AUDIO = "audio";
    public static final int MEDIA_PLAYER_INITIAL_SESSION_TIME = 0x0;
    public static final String MEDIA_VIDEO = "video";
    public static final String PATH_TO_SERVER = "http://my-files.ru/DownloadSave/917vcg/contents.json";
//    http://my-files.ru/917vcg
//    public static final String PATH_TO_SERVER = "http://192.168.0.12/my/contents.json";
    
//    public static final String MWB_PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.imf";
    public static final String ANDROID_DATA_PATH = Environment.getExternalStorageDirectory() + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY_SECOND + "/";
    public static final String ANDROID_DATA_PATH_MEDIA_IMAGES = Environment.getExternalStorageDirectory() + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY_SECOND + "/" + IMAGES +"/";
    public static final String ANDROID_DATA_PATH_MEDIA_AUDIO = Environment.getExternalStorageDirectory() + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY_SECOND + "/" + MEDIA_AUDIO +"/";
    public static final String ANDROID_DATA_PATH_MEDIA_VIDEO = Environment.getExternalStorageDirectory() + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY + "/" + ANDROID_SD_CARD_ROOT_DIRECTORY_SECOND + "/" + MEDIA_VIDEO +"/";
//    public static Map<String, Map<String, ProgressData>> dataMap = new HashMap();
}
