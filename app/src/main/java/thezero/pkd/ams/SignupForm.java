package thezero.pkd.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Faculty.FacultyMain;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.StandardHelper.UserType;
import thezero.pkd.ams.Students.Student_Main;
import thezero.pkd.ams.utils.User;

public class SignupForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView have_an_account_signIn,name,username,password,cn_password;
    private int spinner_selected;
    private RetrofitRoutesInterface retrofitRoutesInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet

        have_an_account_signIn=findViewById(R.id.have_an_account_Sign_in);
        have_an_account_signIn.setOnClickListener(have_an_account_signIn_listener);
        //reference of all edit view
        name=findViewById(R.id.userName);
        username=findViewById(R.id.signUp_userEmail);
        password=findViewById(R.id.password);
        cn_password=findViewById(R.id.confirm_password);

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
            String m_name=name.getText().toString();
            String m_pass=password.getText().toString();
            String m_cn_pass=cn_password.getText().toString();
            String m_userId=username.getText().toString();
            User user=new User(SignupForm.this);
            if (spinner_selected==1){
                if(TextUtils.isEmpty(m_cn_pass)||TextUtils.isEmpty(m_cn_pass)||TextUtils.isEmpty(m_name)||TextUtils.isEmpty(m_userId)) {
                    Toast.makeText(SignupForm.this, "Empty field not allowed!",
                            Toast.LENGTH_SHORT).show();
                }else if (!m_cn_pass.equals(m_pass)) {
                    Toast.makeText(SignupForm.this, "Both Password are not same", Toast.LENGTH_SHORT).show();
                }else{
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("Name",m_name);
                    jsonObject.addProperty("Password",m_pass);
                    jsonObject.addProperty("Email",m_userId);
                    Call call=retrofitRoutesInterface.executeFacultySignUp(jsonObject);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code()==200){
                                Toast.makeText(SignupForm.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                                user.setUserId(m_userId);
                                user.setPassword(m_pass);
                                user.setName(m_name);
                                Intent intent=new Intent(SignupForm.this,FacultyMain.class);
                                startActivity(intent);
                                finish();
                            }else if (response.code()==201){
                                Toast.makeText(SignupForm.this,"Username already exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(SignupForm.this,t.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
            else {
                if(TextUtils.isEmpty(m_cn_pass)||TextUtils.isEmpty(m_cn_pass)||TextUtils.isEmpty(m_name)||TextUtils.isEmpty(m_userId)){
                    Toast.makeText(SignupForm.this,"Empty field not allowed!",
                            Toast.LENGTH_SHORT).show();
                }else if (!(TextUtils.isDigitsOnly(m_userId))){
                    Toast.makeText(SignupForm.this,"Enter your college id as a username",
                            Toast.LENGTH_SHORT).show();
                }else if (!m_cn_pass.equals(m_pass)){
                    Toast.makeText(SignupForm.this,"Both Password are not same",Toast.LENGTH_SHORT).show();
                }else {
                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("Name",m_name);
                    jsonObject.addProperty("Password",m_pass);
                    jsonObject.addProperty("UserId",Integer.parseInt(m_userId));
                    Call call=retrofitRoutesInterface.executeStudentSignUp(jsonObject);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code()==200){
                                Toast.makeText(SignupForm.this,"SignUp Successful",Toast.LENGTH_SHORT).show();
                                user.setUserId(m_userId);
                                user.setPassword(m_pass);
                                user.setName(m_name);
                                Intent intent=new Intent(SignupForm.this, Student_Main.class);
                                startActivity(intent);
                            }else if (response.code()==201){
                                Toast.makeText(SignupForm.this,"Username already exist",Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(SignupForm.this,t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
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
