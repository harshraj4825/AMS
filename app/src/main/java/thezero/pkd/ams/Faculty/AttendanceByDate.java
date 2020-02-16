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
import thezero.pkd.ams.Adapters.A_B_D_Adapter;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.A_B_D_Result;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.A_B_D_recyclerViewList;

public class AttendanceByDate extends AppCompatActivity {
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView faculty_name,course_code,course_name, tLecture;
    private A_B_D_Adapter aBDAdapter;
    private List<A_B_D_recyclerViewList> a_b_d_recyclerViewLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_by_date);
        Intent intent=getIntent();//this intent from when a item clicked in facHomeAdaptor
        String string=intent.getStringExtra("Course_code");

        faculty_name=findViewById(R.id.a_b_d_faculty_name);
        course_code=findViewById(R.id.a_b_d_course_code);
        course_code.setText(string.toUpperCase());
        course_name=findViewById(R.id.a_b_d_course_name);
        tLecture=findViewById(R.id.tLecture);

        recyclerView=findViewById(R.id.a_b_d_recyclerView);
        layoutManager=new LinearLayoutManager(AttendanceByDate.this);
        recyclerView.setLayoutManager(layoutManager);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",string);
        Call<A_B_D_Result> call=retrofitRoutesInterface.executeA_B_D_result(jsonObject);
        call.enqueue(new Callback <A_B_D_Result>() {
            @Override
            public void onResponse(Call <A_B_D_Result> call, Response <A_B_D_Result> response) {
                if(response.code()==200){
                    a_b_d_recyclerViewLists=response.body().getA_b_d_recyclerViewLists();
                    course_name.setText(response.body().getCourse_name());
                    tLecture.setText("Total Lecture:- "+response.body().getTotal_lecture().toString());
                    if(a_b_d_recyclerViewLists.size()==0){
                        Toast.makeText(AttendanceByDate.this,"No data found",Toast.LENGTH_SHORT).show();
                    }
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(AttendanceByDate.this,DividerItemDecoration.VERTICAL);
                    dividerItemDecoration.setDrawable(getDrawable(R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    aBDAdapter=new A_B_D_Adapter(AttendanceByDate.this,a_b_d_recyclerViewLists,string);
                    recyclerView.setAdapter(aBDAdapter);
                }else {
                    Toast.makeText(AttendanceByDate.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call <A_B_D_Result> call, Throwable t) {

            }
        });
    }
}
