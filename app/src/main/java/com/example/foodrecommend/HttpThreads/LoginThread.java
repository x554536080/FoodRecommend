package com.example.foodrecommend.HttpThreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.foodrecommend.MainActivity;
import com.example.foodrecommend.Entities.MyUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginThread extends Thread {

    String function;
    MyUser user;
    Handler handler;


    public LoginThread(boolean isLogin, MyUser user, Handler handler) {
        this.handler = handler;
        if (isLogin)
            function = "login";
        else function = "register";
        this.user = user;
    }

    @Override
    public void run() {
        int resultCode = 0;
        JSONObject jsonObject;
        try {
            BufferedReader reader;
            HttpURLConnection connection;
            URL url = new URL("http://192.168.43.24:8080/FoodRecommend/BasicServices");
//            URL url = new URL("http://192.168.43.191:8080/PhotoInMapBackEnd/activity");
            String outputData = "function=" + function + "&username=" + user.username +
                    "&password=" + user.password;
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
            if (function.equals("register")) {
                if (!result.equals("failed")) {
                    resultCode = 1;
                    MainActivity.username = user.username;
                    MainActivity.hasLogged = true;
                }
            } else if (function.equals("login")) {
                if (!result.equals("failed")) {
                    resultCode = 1;
                    MainActivity.username = user.username;
                    MainActivity.hasLogged = true;
                }
            }


        } catch (Exception e) {
            Log.e("xds", e.toString());
        }
        Message message = new Message();
        message.what = resultCode;
        handler.sendMessage(message);


    }
}
