package com.alddeul.capstone;

import android.app.Application;


import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application   {
    @Override public void onCreate()
    {
        super.onCreate();
        KakaoSdk.init(this, "521bf7574294e3600fbdf3d1ae00e2c4");
    }

}
