package com.example.tweeter.view.fragments.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tweeter.R;
import com.example.tweeter.model.Response.LoginResponse;
import com.example.tweeter.model.request.LoginRequest;
import com.example.tweeter.presenter.LoginPresenter;
import com.example.tweeter.view.MainActivity;
import com.example.tweeter.view.Tasks.LoginTask;


public class LoginFragment extends Fragment implements LoginTask.Observer{
    EditText username;
    EditText password;
    Button submit;
    private static EditText[] list;

    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        submit = view.findViewById(R.id.login_submit);
        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        list = new EditText[]{username, password};
        submit.setEnabled(false);

        username.setHint("Username");
        password.setHint("Password");
        submit.setText("Login");

        for(EditText e : list){
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean seenUnfilled = false;
                    for(EditText check : list){
                        if(check.getText().toString().equals("")){
                            seenUnfilled = true;
                        }
                    }
                    submit.setEnabled(!seenUnfilled);
                }
            });


        }

        submit.setOnClickListener((c)-> {
            String usernameString = username.getText().toString();
            String passwordString = password.getText().toString();
            LoginRequest req = new LoginRequest(usernameString,passwordString);
            LoginTask task = new LoginTask(new LoginPresenter(),this);
            task.execute(req);

        });

        return view;
    }

    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        System.out.println("Logged in successfully!");
        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(LoginResponse.LOGIN_RESPONSE_KEY,loginResponse);
        intent.putExtras(extras);
        startActivity(intent);

    }

    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(getContext(),"Unable to login",Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception ex) {

    }
}