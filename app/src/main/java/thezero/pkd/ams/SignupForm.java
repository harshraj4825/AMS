package thezero.pkd.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import thezero.pkd.ams.Faculty.FacultyMain;
import thezero.pkd.ams.StandardHelper.UserType;
import thezero.pkd.ams.Students.Student_Main;

public class SignupForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView have_an_account_signIn;
    private int spinner_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        have_an_account_signIn=findViewById(R.id.have_an_account_Sign_in);
        have_an_account_signIn.setOnClickListener(have_an_account_signIn_listener);

        Button register_btn=findViewById(R.id.register_btn);
        register_btn.setOnClickListener(register_btn_listener);


        Spinner userSpin = (Spinner) findViewById(R.id.userType);//spinner link to xml
        userSpin.setOnItemSelectedListener(this);
        UserType user= new UserType();// User type object
        String[] users=user.getUserType();//getting all users
        //adding user to spinner
        ArrayAdapter userAdaptor= new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        userAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        userSpin.setAdapter(userAdaptor);
    }
    View.OnClickListener register_btn_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (spinner_selected==1){
                Intent intent=new Intent(SignupForm.this, FacultyMain.class);
                startActivity(intent);
            }
            else {
                Intent intent=new Intent(SignupForm.this, Student_Main.class);
                startActivity(intent);
            }
        }
    };

    View.OnClickListener have_an_account_signIn_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(SignupForm.this,Login_Form.class);
            startActivity(intent);
        }
    };

    @Override
    public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        spinner_selected=i;

    }

    @Override
    public void onNothingSelected(AdapterView <?> adapterView) {

    }
}
