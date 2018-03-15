package com.shane.me.shanedemo.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by luckyshane on 2018/3/15.
 */

public class FileDownloader {
    private static final String TAG = FileDownloader.class.getSimpleName();


    public static boolean donwload(String url, String savePath) {
        File file = new File(savePath);
        if (file.isFile() && file.exists()) {
            return true;
        }
        OutputStream outputStream = null;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL uri = new URL(url);
            connection = (HttpURLConnection) uri.openConnection();

            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                int fileLength = connection.getContentLength();
                if (fileLength > 0) {
                    inputStream = connection.getInputStream();
                    outputStream = new FileOutputStream(savePath);
                    byte data[] = new byte[2048];
                    int count;
                    int total = 0;
                    while ((count = inputStream.read(data)) != -1) {
                        total += count;
                        outputStream.write(data, 0, count);
                    }
                    if (total == fileLength) {
                         return true;
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    public static final String fileCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    public static final String getKey(String url) {
        return url.hashCode() + "";
    }

    public static final String getFileSavePath(Context context, String url) {
        String path = fileCacheDir(context) + getKey(url);
        Log.d(TAG, "getFileSavePath url: " + url + ", path:" + path);
        return path;
    }




}
