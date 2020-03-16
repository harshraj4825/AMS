package thezero.pkd.ams.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import thezero.pkd.ams.Faculty.FacultyMain;
import thezero.pkd.ams.Login_Form;
import thezero.pkd.ams.R;
import thezero.pkd.ams.utils.User;

public class Student_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mNavDrawer;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_nev_drawer_layout);//reference is stu_navigation drawer not activity_student_main


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
        setSupportActionBar(toolbar);
        mNavDrawer = findViewById(R.id.stu_drawer_layout);
        navigationView=findViewById(R.id.stu_nav_view);
        //setting name and  id in nev header
        View headerView=navigationView.getHeaderView(0);
        TextView uName=headerView.findViewById(R.id.uName);
        uName.setText(new User(Student_Main.this).getName());
        TextView uid=headerView.findViewById(R.id.uId);
        uid.setText(new User(Student_Main.this).getUserId());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mNavDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.navHome);
        }
    }
//    this override method is for when we clicked back button only nav drawer goes close
    @Override
    public void onBackPressed(){
        if (mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    //below code section will handle the navigation drawer selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navHome:
                //Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new HomeFragment())
                        .commit();
                break;
            case R.id.nav_register_here:
                //View Attendance fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new Register_here())
                        .commit();
                break;
            case R.id.nev_change_password:
                //add change password fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new ChangePasswordFragment())
                        .commit();
                break;
            case R.id.nav_logout:
                new User(Student_Main.this).removeUser();
                startActivity(new Intent(Student_Main.this, Login_Form.class));
                break;
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;//true because item is selected
    }
}
