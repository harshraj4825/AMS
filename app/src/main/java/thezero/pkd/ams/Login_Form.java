package thezero.pkd.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import thezero.pkd.ams.Faculty.FacultyMain;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginStudentResult;
import thezero.pkd.ams.StandardHelper.UserType;
import thezero.pkd.ams.Students.Student_Main;

public class Login_Form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toast toast;
    private int spinner_selected;
    private Retrofit retrofit;
    TextView userId,userPassword;
    private RetrofitRoutesInterface retrofitRoutesInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);//reference of login layout
        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet
        //to get text
        userId=findViewById(R.id.userEmail);
        userPassword=findViewById(R.id.password);

        Button btn_login = findViewById(R.id.login_button);//reference of login button
        btn_login.setOnClickListener(btn_login_listener);//setting onclick on resister button

        TextView do_have_account_signUp = findViewById(R.id.do_have_account_signUp);//reference of signUp
        do_have_account_signUp.setOnClickListener(do_have_account_signUp_listener);//setting onclick on signUp button

        TextView forgot_password = findViewById(R.id.forgot_password);//reference of login button
        forgot_password.setOnClickListener(forgot_password_listener);//setting onclick on forgot_password

        Spinner userSpin = (Spinner) findViewById(R.id.userType_spinner);//reference of user type spinner
        userSpin.setOnItemSelectedListener(this);
        UserType user= new UserType();
        String[] users=user.getUserType();
        ArrayAdapter userAdaptor= new ArrayAdapter(this,android.R.layout.simple_spinner_item,users);
        userAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        userSpin.setAdapter(userAdaptor);
    }

    View.OnClickListener btn_login_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            String password=userPassword.getText().toString();
//            toast=Toast.makeText(getApplicationContext(),"Logining",Toast.LENGTH_SHORT);
//            toast.show();
            if (spinner_selected==1){
                Intent intent=new Intent(Login_Form.this, FacultyMain.class);
                startActivity(intent);
            }
            else {
                String Pass=userPassword.getText().toString();
                String id=userId.getText().toString();
                if(TextUtils.isEmpty(Pass)|| TextUtils.isEmpty(id)){
                    Toast.makeText(Login_Form.this,"Empty field not allowed!",
                    Toast.LENGTH_SHORT).show();
                }else if(!(TextUtils.isDigitsOnly(id))){
                    Toast.makeText(Login_Form.this,"Enter your college id as a username",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    JsonObject jsonObject=new JsonObject();//creating json instance
                    //adding property
                    jsonObject.addProperty("Password",Pass);
                    jsonObject.addProperty("UserId",Integer.parseInt(id));
                    //to call routes
                    Call<LoginStudentResult> call=retrofitRoutesInterface.executeLogin(jsonObject);
                    call.enqueue(new Callback <LoginStudentResult>() {
                        @Override
                        public void onResponse(Call <LoginStudentResult> call, Response <LoginStudentResult> response) {
                        if(response.code()==200){
                            LoginStudentResult result=response.body();
                            Intent intent=new Intent(Login_Form.this,Student_Main.class);
                            startActivity(intent);
                        }else if (response.code()==201){
                            Toast.makeText(Login_Form.this,"Wrong Credential",
                                    Toast.LENGTH_LONG).show();
                        }
                        }
                        @Override
                        public void onFailure(Call <LoginStudentResult> call, Throwable t) {
                            Toast.makeText(Login_Form.this,t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    };
    View.OnClickListener forgot_password_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toast=Toast.makeText(getApplicationContext(),"you clicked on forgot password",Toast.LENGTH_SHORT);
            toast.show();

        }
    };
    View.OnClickListener do_have_account_signUp_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Login_Form.this,SignupForm.class);
            startActivity(intent);
        }
    };

    @Override
    public void onItemSelected(AdapterView <?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        spinner_selected=i;
        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView <?> adapterView) {
        // TODO Auto-generated method stub

    }
}
