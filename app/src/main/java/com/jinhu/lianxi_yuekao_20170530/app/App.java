package com.jinhu.lianxi_yuekao_20170530.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  18:42
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800)
                .build();
        ImageLoader.getInstance().init(configuration);

        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
