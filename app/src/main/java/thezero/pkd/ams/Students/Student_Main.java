package thezero.pkd.ams.Students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import thezero.pkd.ams.R;

public class Student_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mNavDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_nev_drawer_layout);//reference is stu_navigation drawer not activity_student_main


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.stu_toolbar);
        setSupportActionBar(toolbar);
        mNavDrawer = findViewById(R.id.stu_drawer_layout);
        NavigationView navigationView=findViewById(R.id.stu_nav_view);
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
            case R.id.nav_view_attendance:
                //View Attendance fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new ViewAttendanceFragment())
                        .commit();
                break;
            case R.id.nev_change_password:
                //add change password fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,new ChangePasswordFragment())
                        .commit();
                break;
            case R.id.nav_logout:
                // add action
                break;
        }
        mNavDrawer.closeDrawer(GravityCompat.START);
        return true;//true because item is selected
    }
}
