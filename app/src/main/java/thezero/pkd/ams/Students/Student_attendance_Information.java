package thezero.pkd.ams.Students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import thezero.pkd.ams.utils.User;

public class Student_attendance_Information extends Activity {
    private String roll_no,name,course_code,t_present,t_lecture;
    private TextView tRoll,tName,tCode,tTotal_lec,tTotal_atten;
    private RecyclerView recyclerView;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private RecyclerView.LayoutManager layoutManager;
    private S_A_I_Adaptor sAIAdaptor;
    private List<S_A_I_recyclerViewList> s_a_i_recyclerViewLists;
    private Integer integer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance__information);
        Intent intent=getIntent();

        integer=intent.getIntExtra("ref",0);
        if(integer==1){
            name=intent.getStringExtra("student_name");
            roll_no=intent.getStringExtra("student_roll");
            course_code=intent.getStringExtra("course_code");
        }else if (integer==2){

           name=new User(Student_attendance_Information.this).getName();
           roll_no=new User(Student_attendance_Information.this).getUserId();
           course_code=intent.getStringExtra("course_code");
        }


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

        FloatingActionButton fab = findViewById(R.id.stu_float);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Student_attendance_Information.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                createPDf();
            }
        });
        //fab.setOnClickListener(view -> Toast.makeText(Student_attendance_Information.this,"working fine",Toast.LENGTH_SHORT).show());

        retrofitRoutesInterface= ClientApi.getInstance().getApi();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("course_code",course_code);
        jsonObject.addProperty("student_id",Integer.parseInt(roll_no));

        Call<S_A_I_Result> call=retrofitRoutesInterface.executeS_A_I_result(jsonObject);
        call.enqueue(new Callback <S_A_I_Result>() {
            @Override
            public void onResponse(Call <S_A_I_Result> call, Response <S_A_I_Result> response) {
                if (response.code()==200){
                    t_lecture=response.body().getTotal_lacture();
                    t_present=response.body().getTotal_Present();
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
    public void createPDf(){
        int pageWidth=595;
        int pageHeight=842;
        int Course_code_size=22;
        int pdfFont=18;
        int lrBorder= 30;
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint=new Paint();

        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC));

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(Course_code_size);
        canvas.drawText(course_code,pageWidth/2,35,paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(name,pageWidth-lrBorder,55,paint);
        canvas.drawText("Total Present:-"+t_present,pageWidth-lrBorder,80,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(roll_no,lrBorder,55,paint);
        canvas.drawText("Total lectures:-"+t_lecture,lrBorder,80,paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        float lineGap=(float) (pageWidth-2*lrBorder)/2;
        paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));

        int y_height_Line=95;
        canvas.drawLine(lrBorder,y_height_Line,pageWidth-lrBorder,y_height_Line,paint);
        canvas.drawLine(lrBorder,pageHeight-lrBorder,pageWidth-lrBorder,pageHeight-lrBorder,paint);
        for(int j=0;j<=2;j++){
            canvas.drawLine(lrBorder+lineGap*j,y_height_Line,lrBorder+lineGap*j,pageHeight-lrBorder,paint);
        }
        int height=y_height_Line+30;
        int rheight=height;
        int x_coordinate=lrBorder+20;
        int space_bw_two=23;
        for (int i=1;i<=s_a_i_recyclerViewLists.size();i++){
            Paint paint1=new Paint();
            paint1.setTextAlign(Paint.Align.LEFT);
            paint1.setTextSize(pdfFont);
            paint1.setColor(Color.BLACK);
            if (height<=pageHeight-lrBorder){
                if(s_a_i_recyclerViewLists.get(i-1).getStatus().equals("Present")){
                    canvas.drawText(String.valueOf(i),x_coordinate,height,paint1);
                    canvas.drawText(s_a_i_recyclerViewLists.get(i-1).getDate(),x_coordinate+35,height,paint1);
                    canvas.drawText("1",x_coordinate+150,height,paint1);
                }else {
                    paint1.setColor(Color.RED);
                    canvas.drawText(String.valueOf(i),x_coordinate,height,paint1);
                    canvas.drawText("0",x_coordinate+150,height,paint1);
                    canvas.drawText(s_a_i_recyclerViewLists.get(i-1).getDate(),x_coordinate+35,height,paint1);
                }
                height=height+space_bw_two;
            }else if(rheight<pageHeight-lrBorder){
                if(s_a_i_recyclerViewLists.get(i-1).getStatus().equals("Present")){
                    canvas.drawText(String.valueOf(i),x_coordinate+lineGap,height,paint1);
                    canvas.drawText(s_a_i_recyclerViewLists.get(i-1).getDate(),x_coordinate+lineGap+35,height,paint1);
                    canvas.drawText("1",x_coordinate+lineGap+150,height,paint1);
                }else {
                    paint1.setColor(Color.RED);
                    canvas.drawText(String.valueOf(i),x_coordinate+lineGap,height,paint1);
                    canvas.drawText("0",x_coordinate+lineGap+150,height,paint1);
                    canvas.drawText(s_a_i_recyclerViewLists.get(i-1).getDate(),x_coordinate+lineGap+35,height,paint1);
                }
                rheight=rheight+space_bw_two;
            }
        }
        canvas.drawLine(28,44,38,44,paint);

        document.finishPage(page);
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/AMS/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+name+"-attendance-report.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Save at "+directory_path, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        document.close();
    }
}
