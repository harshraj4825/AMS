package thezero.pkd.ams.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.TakeAttend_Adaptor;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.TakeAttendResult;
import thezero.pkd.ams.Students.HomeFragment;

public class SubmitAttendence extends Activity {
    List<TakeAttendResult> presentAttendResults,all_student_list,absent_attend;
    private String reference="SubmitAttendance",course_code;
    private TextView tPresent,tAbsent,tCourse_code;
    private Button btn_final_submit;
    private TakeAttend_Adaptor submit_Adaptor;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TakeAttendResult takeAttendResult;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_attendence);
        Intent intent=getIntent();
        course_code=intent.getStringExtra("Course_code");
        tCourse_code=findViewById(R.id.s_course_code);
        tCourse_code.setText(course_code);
        absent_attend=new ArrayList <>();
        all_student_list=new ArrayList <>();
        presentAttendResults=new ArrayList <>();
        absent_attend=(ArrayList<TakeAttendResult>)getIntent().getSerializableExtra("currentUnelectedItems");
        all_student_list=(ArrayList<TakeAttendResult>)getIntent().getSerializableExtra("all_student_lists");
        presentAttendResults=(ArrayList<TakeAttendResult>)getIntent().getSerializableExtra("currentSelectedItem");
     //   Integer presentStudents=absent_attend.size();
        tPresent=findViewById(R.id.student_present);
        tPresent.setText("Absent: "+absent_attend.size()+"/"+all_student_list.size());
        tAbsent=findViewById(R.id.student_absent);
        tAbsent.setText("Present: "+presentAttendResults.size());

        btn_final_submit=findViewById(R.id.final_submit_attend);
        btn_final_submit.setOnClickListener(btn_submit_listener);
        recyclerView=findViewById(R.id.list_all_absent_student_recyclerView);

        layoutManager=new LinearLayoutManager(SubmitAttendence.this);
        recyclerView.setLayoutManager(layoutManager);
        submit_Adaptor=new TakeAttend_Adaptor(SubmitAttendence.this, absent_attend, reference, new TakeAttend_Adaptor.OnItemCheckListener() {
            @Override
            public void onItemCheck(TakeAttendResult item) {
                presentAttendResults.add(item);
                absent_attend.remove(item);
                tPresent.setText("Absent: "+absent_attend.size()+"/"+all_student_list.size());
                tAbsent.setText("Present: "+presentAttendResults.size());
            }

            @Override
            public void onItemUncheck(TakeAttendResult item) {
                presentAttendResults.remove(item);
                absent_attend.add(item);
                tPresent.setText("Absent: "+absent_attend.size()+"/"+all_student_list.size());
                tAbsent.setText("Present: "+presentAttendResults.size());
            }
        });
        recyclerView.setAdapter(submit_Adaptor);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
    }

    View.OnClickListener btn_submit_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            jsonArray=new JSONArray();
            for(int i=0;i<all_student_list.size();i++){
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("student_id",all_student_list.get(i).getUserId());
                for(int j=0;j<presentAttendResults.size();j++){
                    if(presentAttendResults.get(j).getUserId().equals(all_student_list.get(i).getUserId())){
                        jsonObject.addProperty("attend",true);
                    }
                }
                for (int k=0;k<absent_attend.size();k++){
                    if (absent_attend.get(k).getUserId().equals(all_student_list.get(i).getUserId())){
                        jsonObject.addProperty("attend",false);
                    }
                }
                jsonArray.put(jsonObject);
            }
            JsonObject jsonCourseCode=new JsonObject();
            jsonCourseCode.addProperty("j_course_code",course_code);
            Call<Void> call=retrofitRoutesInterface.executeSubmitAttendance(jsonCourseCode,jsonArray);
            call.enqueue(new Callback <Void>() {
                @Override
                public void onResponse(Call <Void> call, Response <Void> response) {
                    if (response.code()==200){
                        Toast.makeText(SubmitAttendence.this,"success",Toast.LENGTH_SHORT).show();
                    }
                    else if (response.code()==201 || response.code()==202){
                        Toast.makeText(SubmitAttendence.this,"success",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call <Void> call, Throwable t) {
                    Toast.makeText(SubmitAttendence.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent=new Intent(SubmitAttendence.this, FacultyMain.class);
            startActivity(intent);
        }
    };
}
