package thezero.pkd.ams.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.S_A_I_Adaptor;
import thezero.pkd.ams.Faculty.CourseInformatin;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.S_A_I_recyclerViewList;
import thezero.pkd.ams.Retrofit.Retrofit_models.S_A_I_Result;

public class Student_attendance_Information extends Activity {
    private String roll_no,name,course_code;
    private TextView tRoll,tName,tCode,tTotal_lec,tTotal_atten;
    private RecyclerView recyclerView;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private RecyclerView.LayoutManager layoutManager;
    private S_A_I_Adaptor sAIAdaptor;
    private List<S_A_I_recyclerViewList> s_a_i_recyclerViewLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance__information);
        Intent intent=getIntent();
        name=intent.getStringExtra("student_name");
        roll_no=intent.getStringExtra("student_roll");
        course_code=intent.getStringExtra("course_code");

        tCode=findViewById(R.id.s_a_i_course_code);
        tCode.setText(course_code);
        tName=findViewById(R.id.s_a_i_student_name);
        tName.setText(name);
        tRoll=findViewById(R.id.s_a_i_userId);
        tRoll.setText(roll_no);
        tTotal_lec=findViewById(R.id.total_lecture);
        tTotal_atten=findViewById(R.id.total_attendance);;

        recyclerView=findViewById(R.id.s_a_i_recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",course_code);
        jsonObject.addProperty("student_id",Integer.parseInt(roll_no));

        Call<S_A_I_Result> call=retrofitRoutesInterface.executeS_A_I_result(jsonObject);
        call.enqueue(new Callback <S_A_I_Result>() {
            @Override
            public void onResponse(Call <S_A_I_Result> call, Response <S_A_I_Result> response) {
                if (response.code()==200){
                    tTotal_lec.setText(response.body().getTotal_lacture());
                    tTotal_atten.setText(response.body().getTotal_Present());
                    s_a_i_recyclerViewLists=response.body().getS_a_i_recyclerViewLists();
                    if (s_a_i_recyclerViewLists.size()==0){
                        Toast.makeText(Student_attendance_Information.this,"no data found",Toast.LENGTH_SHORT).show();
                    }
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(Student_attendance_Information.this,DividerItemDecoration.VERTICAL);
                    dividerItemDecoration.setDrawable(getDrawable(R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    sAIAdaptor=new S_A_I_Adaptor(Student_attendance_Information.this,s_a_i_recyclerViewLists);
                    recyclerView.setAdapter(sAIAdaptor);
                }
            }
            @Override
            public void onFailure(Call <S_A_I_Result> call, Throwable t) {

            }
        });

    }
}
