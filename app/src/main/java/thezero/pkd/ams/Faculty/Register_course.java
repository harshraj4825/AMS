package thezero.pkd.ams.Faculty;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.utils.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register_course extends Fragment {
    TextView course_code,course_name;
    Button register_course_btn;
    RetrofitRoutesInterface retrofitRoutesInterface;
    public Register_course() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register_course, container, false);
        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet
        course_code=view.findViewById(R.id.course_code_edit);
        course_name=view.findViewById(R.id.course_name_edit);
        register_course_btn=view.findViewById(R.id.register_course_btn);
        register_course_btn.setOnClickListener(register_course_listerner);


        return view;
    }
    View.OnClickListener register_course_listerner=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String mCourse_code=course_code.getText().toString().toUpperCase();
            String mCourse_name=course_name.getText().toString().toUpperCase();
            String mFaculty_email=new User(getContext()).getUserId();
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("Course_code",mCourse_code);
            jsonObject.addProperty("Course_name",mCourse_name);
            jsonObject.addProperty("Faculty_Email",mFaculty_email);

            if(TextUtils.isEmpty(mCourse_code)||TextUtils.isEmpty(mCourse_name)||TextUtils.isEmpty(mFaculty_email)) {
                Toast.makeText(getContext(), "All field required", Toast.LENGTH_SHORT).show();
            }else {
                Call<Void> call=retrofitRoutesInterface.executeFacCourse_register(jsonObject);
                call.enqueue(new Callback <Void>() {
                    @Override
                    public void onResponse(Call <Void> call, Response <Void> response) {
                        if(response.code()==200){
                            Toast.makeText(getContext(),"Course is successfully registered",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getContext(),FacultyMain.class);
                            startActivity(intent);
                        }else if(response.code()==201){
                            Toast.makeText(getContext(),"Course is already registered",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call <Void> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

        }
    };
}
