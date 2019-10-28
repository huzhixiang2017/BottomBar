package com.frank.chat;

import android.app.Application;

/**
 * Created by frank on 2016/12/14.
 */

public class ChatApplication extends Application {
    private static ChatApplication chatApplication;

    @Override
    public void onCreate() {

        chatApplication = this;
    }

    public static ChatApplication getInstance(){
        return chatApplication;
    }

}
