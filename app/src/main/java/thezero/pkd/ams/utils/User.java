package thezero.pkd.ams.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    private String Name;
    private String Password;
    private String UserId;

    Context context;
    SharedPreferences sharedPreferences;
    public  User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("login_information",Context.MODE_PRIVATE);
    }

    public String getName() {
        Name=sharedPreferences.getString("Name","");
        return Name;
    }

    public void setName(String name) {
        Name = name;
        sharedPreferences.edit().putString("Name",name).apply();
    }

    public String getPassword() {
        Password=sharedPreferences.getString("Password","");
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
        sharedPreferences.edit().putString("Password",password).apply();
    }

    public String getUserId() {
        UserId=sharedPreferences.getString("UserId","");
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
        sharedPreferences.edit().putString("UserId",userId).apply();
    }
    public void removeUser(){
        sharedPreferences.edit().clear().apply();
    }
}
