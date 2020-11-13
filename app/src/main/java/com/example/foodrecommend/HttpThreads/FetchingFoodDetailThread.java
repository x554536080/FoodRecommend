package com.example.foodrecommend.HttpThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.foodrecommend.FoodDetailActivity;
import com.example.foodrecommend.TypeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchingFoodDetailThread extends Thread {
    Handler handler;
    String fId;

    public FetchingFoodDetailThread(Handler handler, String fId) {
        this.fId = fId;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader;
            HttpURLConnection connection;
            URL url = new URL("http://192.168.43.24:8080/FoodRecommend/BasicServices");
            String outputData = "function=" + "fetchingFoodDetail&foodId=" + fId;
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

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(outputData.getBytes());
            outputStream.close();

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

            FoodDetailActivity.foodInfo = new JSONObject(resultData.toString());
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);


        } catch (Exception e) {
            Log.e("xds", e.toString());
        }
    }
}
