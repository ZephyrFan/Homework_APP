package com.example.bighomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bighomework.Service.LoginService;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_tab);
        EditText accountET = (EditText)findViewById(R.id.login_account);
        EditText passwordET = (EditText)findViewById(R.id.login_password);

        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String account = accountET.getText().toString();
                    String password = passwordET.getText().toString();

                    boolean isTea = new LoginService().isTeacher(account);
                    if(isTea){
                        startActivity(new Intent(LoginActivity.this,TeaMainActivity.class));
                    }else{
                        startActivity(new Intent(LoginActivity.this,StuMainActivity.class));
                    }
                } catch (RuntimeException e) {
                    accountET.setText("");
                    passwordET.setText("");
                    e.printStackTrace();
                }
            }
        });

    }

}