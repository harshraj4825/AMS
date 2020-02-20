package thezero.pkd.ams.Faculty;

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
import thezero.pkd.ams.Adapters.AttendanceListAdapter;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.AttenListResult;

public class AttendanceList extends Activity {
    TextView tCourseCode,tDate;
    private RecyclerView recyclerView;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private RecyclerView.LayoutManager layoutManager;
    private AttendanceListAdapter attendanceListAdapter;
    private List<AttenListResult> attenListResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        Intent intent=getIntent();
        String course_code=intent.getStringExtra("course_code");
        String date=intent.getStringExtra("date");
        tCourseCode=findViewById(R.id.a_l_course_code);
        tCourseCode.setText(course_code);
        tDate=findViewById(R.id.a_i_date);
        tDate.setText(date);

        recyclerView=findViewById(R.id.a_l_recyclerView);
        layoutManager=new LinearLayoutManager(AttendanceList.this);
        recyclerView.setLayoutManager(layoutManager);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",course_code);
        jsonObject.addProperty("date",date);

        Call<List<AttenListResult>> call=retrofitRoutesInterface.executeAttendanceList(jsonObject);
        call.enqueue(new Callback <List <AttenListResult>>() {
            @Override
            public void onResponse(Call <List <AttenListResult>> call, Response <List <AttenListResult>> response) {
                if (response.code()==200){
                    attenListResults=response.body();
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(AttendanceList.this,DividerItemDecoration.VERTICAL);
                    dividerItemDecoration.setDrawable(getDrawable(R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    attendanceListAdapter=new AttendanceListAdapter(AttendanceList.this,attenListResults);
                    recyclerView.setAdapter(attendanceListAdapter);
                }else {
                    Toast.makeText(AttendanceList.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call <List <AttenListResult>> call, Throwable t) {

            }
        });


    }
}
