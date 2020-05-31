package com.example.slimapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.slimapi.Model.User;
import com.example.slimapi.Storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user =  SharedPrefManager.getInstance(this).getUser();
        TextView textView = findViewById(R.id.username_tv);
        textView.setText(user.getName());
    }
}
