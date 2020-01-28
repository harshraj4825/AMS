package thezero.pkd.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Timer;
import java.util.TimerTask;

import thezero.pkd.ams.Faculty.FacultyMain;
import thezero.pkd.ams.Students.Student_Main;
import thezero.pkd.ams.utils.User;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                User user=new User((SplashActivity.this));
                String m_user_id=user.getUserId();
                if(TextUtils.isEmpty(m_user_id)){
                    startActivity(new Intent(SplashActivity.this, Login_Form.class));
                    finish();
                }else if(TextUtils.isDigitsOnly(m_user_id)){
                    startActivity(new Intent(SplashActivity.this, Student_Main.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, FacultyMain.class));
                }
            }
        },2000);
    }
}
