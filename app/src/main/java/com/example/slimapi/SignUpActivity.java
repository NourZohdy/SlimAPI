package com.example.slimapi;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.slimapi.Api.RetrofiClient;
import com.example.slimapi.Model.DefaulteResponce;

public class SignUpActivity extends AppCompatActivity {

    private EditText name_ed;
    private EditText email_ed;
    private EditText school_ed;
    private EditText password_ed;
    private EditText confirm_ed;
    private Button sign_btn;
    private Button login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name_ed = findViewById(R.id.name_ed);
        email_ed = findViewById(R.id.email_ed);
        school_ed = findViewById(R.id.school_ed);
        password_ed = findViewById(R.id.password_ed);
        confirm_ed = findViewById(R.id.confirm_ed);
        sign_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);


    }


    public void loginClick(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void click(View view) {
        if (validate()) {
            String name = name_ed.getText().toString();
            String email = email_ed.getText().toString();
            String password = password_ed.getText().toString();
            String school = school_ed.getText().toString();

            Call<DefaulteResponce> call = RetrofiClient
                    .getInstance()
                    .getApi()
                    .createUser(name, email, password, school);

            call.enqueue(new Callback<DefaulteResponce>() {
                @Override
                public void onResponse(Call<DefaulteResponce> call, Response<DefaulteResponce> response) {
                    if (response.code() == 201) {
                        DefaulteResponce defaulteResponce = response.body();
                        Toast.makeText(SignUpActivity.this, defaulteResponce.getMessage(), Toast.LENGTH_LONG).show();
                    } else if (response.code() == 422) {
                        String msg = "User Already Exist!";
//                        DefaulteResponce defaulteResponce = response.body();
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<DefaulteResponce> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, t.getMessage() + "asdasdasd", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = name_ed.getText().toString();
        String email = email_ed.getText().toString();
        String password = password_ed.getText().toString();
        String confirmpasswordText = confirm_ed.getText().toString();
        String school = school_ed.getText().toString();

        if (name.isEmpty() || (name.length() > 12)) {
            name_ed.setError("enter a valid Name");
            valid = false;
        } else {
            name_ed.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_ed.setError("enter a valid email address");
            valid = false;
        } else {
            email_ed.setError(null);
        }

        if (school.isEmpty()) {
            school_ed.setError("This Field is Required");
            valid = false;
        } else {
            school_ed.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_ed.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_ed.setError(null);
        }

        if (confirmpasswordText.isEmpty() || confirmpasswordText.length() < 4 || confirmpasswordText.length() > 10 || !(confirmpasswordText.equals(password))) {
            confirm_ed.setError("Password Do not match");
            valid = false;
        } else {
            confirm_ed.setError(null);
        }

        return valid;
    }


}
