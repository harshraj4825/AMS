package thezero.pkd.ams.Faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import thezero.pkd.ams.Login_Form;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Students.ChangePasswordFragment;
import thezero.pkd.ams.Students.ViewAttendanceFragment;
import thezero.pkd.ams.utils.User;

public class FacultyMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mNavDrawer;
    private TextView uName,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fac_nev_drawer_layout);

        androidx.appcompat.widget.Toolbar fac_toolbar = findViewById(R.id.fac_toolbar);
        setSupportActionBar(fac_toolbar);
        mNavDrawer = findViewById(R.id.fac_drawer_layout);
        NavigationView navigationView=findViewById(R.id.fac_nav_view);
        //setting name and  id in nev header
        View headerView=navigationView.getHeaderView(0);
        uName=headerView.findViewById(R.id.uName);
        uName.setText(new User(FacultyMain.this).getName());
        uid=headerView.findViewById(R.id.uId);
        uid.setText(new User(FacultyMain.this).getUserId());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mNavDrawer, fac_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fac_fragment_container, new FacHomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_faculty_Home);
        }

    }
    @Override
    public void onBackPressed(){
        if (mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_faculty_Home:
                //Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new FacHomeFragment())
                        .commit();
                break;
            case R.id.nav_faculty_students_list:
                //Home fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new StudentsListFragment())
                        .commit();
                break;
            case R.id.nav_faculty_take_attendance:
                //View Attendance fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new TakeAttendanceFragment())
                        .commit();
                break;
            case R.id.nav_faculty_view_attendance:
                //View Attendance fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new FacViewAttendanceFragment())
                        .commit();
                break;
            case R.id.nav_faculty_register_course:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new Register_course())
                        .commit();
                break;
            case R.id.nev_faculty_change_password:
                //add change password fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fac_fragment_container,new ChangePasswordFragment())
                        .commit();
                break;
            case R.id.nav_faculty_logout:
                new User(FacultyMain.this).removeUser();
                startActivity(new Intent(FacultyMain.this, Login_Form.class));
                break;
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
