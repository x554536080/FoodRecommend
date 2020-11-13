package com.example.foodrecommend.HttpThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.foodrecommend.AddingTagActivity;
import com.example.foodrecommend.Entities.MyUser;
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

public class AddingTagThread extends Thread {

    List<String> tags;
    Handler handler;


    public AddingTagThread(Handler handler, List<String> tags) {
        this.handler = handler;
        this.tags = tags;
    }

    @Override
    public void run() {
        JSONObject jsonObject;
        try {
            BufferedReader reader;
            HttpURLConnection connection;
            URL url = new URL("http://192.168.43.24:8080/FoodRecommend/BasicServices?function=addingTags&username="+MainActivity.username);
//            URL url = new URL("http://192.168.43.191:8080/PhotoInMapBackEnd/activity");

            JSONArray jsonArray = new JSONArray();
            for (String tag : tags) {
                jsonArray.put(tag);
            }
            String outputData = jsonArray.toString();
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
            connection.setRequestProperty("Content-Type", "application/json");


            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(outputData);
            writer.flush();
            outputStream.flush();
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

            jsonObject = new JSONObject(resultData.toString());
            String result = jsonObject.getString("result");

            if (!result.equals("failed")) {
                handler.sendMessage(new Message());
            }


        } catch (Exception e) {
            Log.e("xds", e.toString());
        }


    }
}
