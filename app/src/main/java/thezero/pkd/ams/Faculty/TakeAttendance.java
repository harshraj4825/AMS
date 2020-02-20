package thezero.pkd.ams.Faculty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.TakeAttend_Adaptor;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.TakeAttendResult;

public class TakeAttendance extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private TakeAttend_Adaptor takeAttend_adaptor;
    private List <TakeAttendResult> takeAttendResults,currentSelectedItems, All_student_list ;
    private TextView tCourse_code;
    private Button btn_take_attend;
    private String reference="TakeAttendance";
    String course_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        Intent intent=getIntent();
        course_code=intent.getStringExtra("Course_code");
        tCourse_code=findViewById(R.id.take_course_code);
        tCourse_code.setText(course_code);
        currentSelectedItems =new ArrayList <>();
        takeAttendResults=new ArrayList <>();
        btn_take_attend=findViewById(R.id.submit_attend);
        btn_take_attend.setOnClickListener(Btn_take_attend_listerner);

        recyclerView=findViewById(R.id.take_view_all_RegisteredCourse);
        layoutManager=new LinearLayoutManager(TakeAttendance.this);
        recyclerView.setLayoutManager(layoutManager);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",course_code);
        Call <List<TakeAttendResult>> call=retrofitRoutesInterface.executeTakeAttend(jsonObject);
        call.enqueue(new Callback <List <TakeAttendResult>>() {
            @Override
            public void onResponse(Call <List <TakeAttendResult>> call, Response <List <TakeAttendResult>> response) {
                if(response.code()==200){
                    takeAttendResults.addAll(response.body());
                    if(takeAttendResults.size()==0){
                        Toast.makeText(TakeAttendance.this,"No student registered",Toast.LENGTH_SHORT).show();
                    }
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(TakeAttendance.this,DividerItemDecoration.VERTICAL);
                    dividerItemDecoration.setDrawable(getDrawable(R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    takeAttend_adaptor=new TakeAttend_Adaptor(TakeAttendance.this, takeAttendResults,reference,new TakeAttend_Adaptor.OnItemCheckListener() {
                        @Override
                        public void onItemCheck(TakeAttendResult item) {
                            currentSelectedItems.add(item);
                        }

                        @Override
                        public void onItemUncheck(TakeAttendResult item) {
                            currentSelectedItems.remove(item);
                        }
                    }) ;

                    recyclerView.setAdapter(takeAttend_adaptor);
                }else {
                    Toast.makeText(TakeAttendance.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call <List <TakeAttendResult>> call, Throwable t) {

            }
        });

    }
    View.OnClickListener Btn_take_attend_listerner =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            All_student_list=new ArrayList <>();
            All_student_list.addAll(takeAttendResults);
            All_student_list.removeAll(currentSelectedItems);
            Intent intent=new Intent(TakeAttendance.this,SubmitAttendence.class);
            intent.putExtra("currentSelectedItem",(ArrayList<TakeAttendResult>)currentSelectedItems);
            intent.putExtra("currentUnelectedItems", (ArrayList<TakeAttendResult>) All_student_list);
            intent.putExtra("all_student_lists",(ArrayList<TakeAttendResult>) takeAttendResults);
            intent.putExtra("Course_code",course_code);
      //      Toast.makeText(TakeAttendance.this,All_student_list.size(),Toast.LENGTH_SHORT);
            startActivity(intent);
        }
    };

}
