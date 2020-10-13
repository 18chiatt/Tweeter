package com.example.tweeter.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.tweeter.R;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.presenter.LoginPresenter;
import com.example.tweeter.view.Tasks.LoginTask;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity implements LoginTask.Observer {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        tabLayout = findViewById(R.id.login_tabs);
        viewPager = findViewById(R.id.login_viewPager);

        LoginPagerAdapter adapter = new LoginPagerAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        System.out.println("Logged in successfully!");
        Intent intent = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(LoginResponse.LOGIN_RESPONSE_KEY,loginResponse);
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this,"Unable to login!",Toast.LENGTH_SHORT);
    }

    @Override
    public void handleException(Exception ex) {

    }
}