package com.jinhu.lianxi_yuekao_20170530.util;

import com.jinhu.lianxi_yuekao_20170530.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  18:44
 */

public class ImageUtils {
    public static DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        return options;
    }
}
