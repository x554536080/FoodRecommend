package com.example.foodrecommend.HttpThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.foodrecommend.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UploadingRatingThread extends Thread {

    String fid;
    String fType;
    String score;
    Handler handler;


    public UploadingRatingThread(String fid, String fType, String score, Handler handler) {
        this.fid = fid;
        this.fType = fType;
        this.score = score;
        this.handler = handler;
    }


    @Override
    public void run() {
        JSONObject jsonObject;
        try {
            BufferedReader reader;
            HttpURLConnection connection;
            URL url = new URL("http://192.168.43.24:8080/FoodRecommend/BasicServices?function=addingRating&username=" + MainActivity.username
                    + "&fType=" + fType + "&score=" + score);


            StringBuilder resultData = new StringBuilder();

            //配置connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
//            connection.setReadTimeout(20000);
            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);


            int code;
            if ((code = connection.getResponseCode()) == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    resultData.append(line);
                }
                reader.close();
            }
            connection.disconnect();
            Log.d("respondCode", code + "");

            jsonObject = new JSONObject(resultData.toString());
            String result = jsonObject.getString("result");

            if (!result.equals("failed")) {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }


        } catch (Exception e) {
            Log.e("xds", e.toString());
        }


    }
}
