package thezero.pkd.ams.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.C_I_Adapter;
import thezero.pkd.ams.Adapters.facHomeAdapter;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.C_I_Result;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.C_I_recyclerViewList;

public class CourseInformatin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private C_I_Adapter cIAdapter;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private List<C_I_recyclerViewList> c_i_recyclerViewLists;
    private TextView faculty_name,course_code,course_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_informatin);
        Intent intent=getIntent();//this intent from when a item clicked in facHomeAdaptor
        String string=intent.getStringExtra("Course_code");

        faculty_name=findViewById(R.id.c_i_faculty_name);
        course_code=findViewById(R.id.c_i_course_code);
        course_code.setText(string.toUpperCase());
        course_name=findViewById(R.id.c_i_course_name);

        //recyclerView
        recyclerView=findViewById(R.id.c_i_recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",string);
        Call<C_I_Result> call=retrofitRoutesInterface.executeC_I_result(jsonObject);
        call.enqueue(new Callback <C_I_Result>() {
            @Override
            public void onResponse(Call <C_I_Result> call, Response <C_I_Result> response) {
                if (response.code()==200){
                    faculty_name.setText(response.body().getFaculty_name());
                    course_name.setText(response.body().getCourse_name().toUpperCase());
                    c_i_recyclerViewLists=response.body().getStudent_information();
                    if(c_i_recyclerViewLists.size()==0){
                        Toast.makeText(CourseInformatin.this,"No student registered",Toast.LENGTH_SHORT).show();
                    }
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(CourseInformatin.this,DividerItemDecoration.VERTICAL);
                   // dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.line_border));
                    dividerItemDecoration.setDrawable(getDrawable(R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    cIAdapter=new C_I_Adapter(CourseInformatin.this,c_i_recyclerViewLists,course_code.getText().toString());
                    recyclerView.setAdapter(cIAdapter);
                }
            }
            @Override
            public void onFailure(Call <C_I_Result> call, Throwable t) {

            }
        });

    }
}
