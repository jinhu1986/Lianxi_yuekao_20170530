package com.jinhu.lianxi_yuekao_20170530.util;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  18:33
 */

public class HttpUtils {
    static final int GET_EXCUTE = 111;
    static final int GET_ENQUEUE = 222;
    static final int POST_EXCUTE = 333;
    static final int POST_ENQUEUE = 444;

    private HttpUtils() {
    }

    private static OkHttpClient mClient;
    //设置标题头格式
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 单例模式
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        if (mClient == null) {
            synchronized (OkHttpClient.class) {
                if (mClient == null) {
                    mClient = new OkHttpClient.Builder().build();
                }
            }
        }
        return mClient;
    }


    /**
     * okHttp get 同步请求方式
     *
     * @param url      get方式的网络路径
     * @param map      params 的 key ,values值
     * @param callback 接口回调
     */
    public static void getExcute(final String url, final Map<String, String> map, final MyCallback callback) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == GET_EXCUTE) {
                    String json = (String) msg.obj;
                    if (null != json) {
                        callback.onSuccess(json);
                    } else {
                        callback.onError(json);
                    }
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
                if (null != map) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builder.addQueryParameter(entry.getKey(), entry.getValue());
                    }
                }
                HttpUrl httpUrl = builder.build();

                Request request = new Request.Builder()
                        .url(httpUrl)
                        .build();

                Response response = null;
                try {
                    response = getClient().newCall(request).execute();
                    String json = response.body().string();
                    if (null != json) {
                        Message message = Message.obtain();
                        message.obj = json;
                        message.what = GET_EXCUTE;
                        mHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * okHttp get 异步 请求方式
     *
     * @param url      get方式的网络路径
     * @param map      params 的 key ,values值
     * @param callback 接口回调
     */
    public static void getEnqueue(final String url, final Map<String, String> map, final MyCallback callback) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == GET_ENQUEUE) {
                    String json = (String) msg.obj;
                    if (null != json) {
                        callback.onSuccess(json);
                    } else {
                        callback.onError(json);
                    }
                }
            }
        };
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpUrl httpUrl = builder.build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String json = response.body().string();
                if (null != json) {
                    Message message = Message.obtain();
                    message.obj = json;
                    message.what = GET_ENQUEUE;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * 接口回调
     */
    public interface MyCallback {
        void onSuccess(String result);

        void onError(String errorMsg);
    }
}
