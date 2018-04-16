package com.hanjinliang.template.module.base.activity;

//by summer on 2018-04-16.

import android.os.Bundle;

public interface BaseUICallBack {

    public void onCreate(Bundle savedInstanceState);

    public void onDestroy();

    public void onPause();

    public void onBackPressed();

    public void onStart();
}
