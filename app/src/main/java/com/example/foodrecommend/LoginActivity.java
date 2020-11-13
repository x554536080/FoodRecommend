package com.example.foodrecommend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrecommend.Entities.MyUser;
import com.example.foodrecommend.HttpThreads.LoginThread;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton loginButton;
    Button backButton;
    TextView registerText;
    TextView loginButtonText;
    EditText username;
    EditText password;

    boolean isLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidgets();
        initVariables();
    }

    void initVariables() {
        isLogin = true;
    }

    void initWidgets() {
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        loginButtonText = findViewById(R.id.login_text);
        backButton = findViewById(R.id.login_back);
        backButton.setOnClickListener(this);
        registerText = findViewById(R.id.login_register);
        registerText.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                new LoginThread(isLogin, new MyUser(username.getText().toString(), password.getText().toString()), handler).start();
                break;
            case R.id.login_back:
                finish();
                break;
            case R.id.login_register:
                if (registerText.getText().equals("注册")) {
                    loginButtonText.setText("注册");
                    registerText.setText("登录");
                    isLogin = false;
                } else {
                    loginButtonText.setText("登陆");
                    registerText.setText("注册");
                    isLogin = true;
                }
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(LoginActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    finish();
                    Intent intent = new Intent(LoginActivity.this,AddingTagActivity.class);
                    if(registerText.getText().equals("登录"))
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };


}
