package com.example.accounting.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ TIME  2017/5/3 0003.
 * @ DES   ${TODO}.
 */

public class HttpDownloader {
    private URL url = null;



    public String download(String urlStr) {            //download 函数，实现下载后返回字符串。
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            // 创建一个URL对象
            url = new URL(urlStr);

            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            // 使用IO流读取数据
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            Log.i("AAA", "e: " + e);
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
     */
    public int downFile(String urlStr, String path, String fileName) {
        InputStream inputStream = null;
        try {
            FileUtils fileUtils = new FileUtils();      //FileUtils 是自己定义的一个读写sdcard的类

            if (fileUtils.isFileExist(path + fileName)) {
                return 1;
            } else {
                inputStream = getInputStreamFromUrl(urlStr);  //自己定义的一个函数，从给的url网址获取数据流，见下面
                File resultFile = fileUtils.write2SDFromInput(path,fileName, inputStream);  //FileUtils 类里面的一个函数，讲数据流写到sdcard中。
                if (resultFile == null) {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public InputStream getInputStreamFromUrl(String urlStr) throws MalformedURLException, IOException {
        url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConn.getInputStream();
        return inputStream;
    }
}