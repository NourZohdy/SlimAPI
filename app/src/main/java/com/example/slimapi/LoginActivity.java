package com.example.slimapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slimapi.Api.RetrofiClient;
import com.example.slimapi.Model.LoginResponse;
import com.example.slimapi.Storage.SharedPrefManager;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email_ed;
    private EditText password_ed;
    private Button sign_btn;
    private Button login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_ed = findViewById(R.id.email_ed);
        password_ed = findViewById(R.id.password_ed);
        sign_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);


    }

    @Override
    protected void onStart() {
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onStart();

    }

    public void signUpClick(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void loginClick(View view) {
        if (validate()) {
            String email = email_ed.getText().toString();
            String password = password_ed.getText().toString();

            Call<LoginResponse> call = RetrofiClient
                    .getInstance()
                    .getApi()
                    .userlogin(email, password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.code() == 200) {
                        LoginResponse loginResponse = response.body();
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage() + "\n" + loginResponse.getUser().getName(), Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(LoginActivity.this).saveUser(response.body().getUser());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (response.code() == 201) {
                        String msg = "User Already Exist!";
                        LoginResponse loginResponse = response.body();
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage() + "asdasdasd", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean validate() {
        boolean valid = true;

        String email = email_ed.getText().toString();
        String password = password_ed.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_ed.setError("enter a valid email address");
            valid = false;
        } else {
            email_ed.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_ed.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_ed.setError(null);
        }


        return valid;
    }


}
