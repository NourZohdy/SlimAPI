package com.example.slimapi.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.slimapi.Model.User;

public class SharedPrefManager {
    private static final String PREF_NAME = "MyPref";

    private static SharedPrefManager mInstance;
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("school", user.getSchool());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return (preferences.getInt("id", -1) != -1);
    }

    public User getUser() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        User user = new User(
                preferences.getInt("id", -1),
                preferences.getString("name", null),
                preferences.getString("email", null),
                preferences.getString("school", null));
        return user;
    }

    public void clear() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
