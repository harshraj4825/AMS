package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.Faculty.AttendanceByDate;
import thezero.pkd.ams.Faculty.CourseInformatin;
import thezero.pkd.ams.Faculty.TakeAttendance;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.course_registered_model;

public class facHomeAdapter extends RecyclerView.Adapter<facHomeAdapter.modelViewHolder> {
    private List<course_registered_model> course_registered_modelList;
    Context context;
    String ExtraInfo;

    public facHomeAdapter(Context context, List <course_registered_model> course_registered_modelList,String ExtraInfo) {
        this.course_registered_modelList = course_registered_modelList;
        this.context=context;
        this.ExtraInfo=ExtraInfo;
    }

    @NonNull
    @Override
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fac_home_recycler,parent,false);
        return new modelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modelViewHolder holder, int position) {
        holder.Course_code.setText(course_registered_modelList.get(position).getCourse_code().toUpperCase());
        holder.no_of_stu.setText(course_registered_modelList.get(position).getRegistered_Students().toString());
        //to set onclick
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ExtraInfo.equals("Home")){
                    Intent intent =new Intent(context, CourseInformatin.class);
                    intent.putExtra("Course_code",course_registered_modelList.get(position).getCourse_code());
                    context.startActivity(intent);

                }
                else if(ExtraInfo.equals("ViewAttendance")){//from FacViewAttendance.java
                    Intent intent =new Intent(context, AttendanceByDate.class);
                    intent.putExtra("Course_code",course_registered_modelList.get(position).getCourse_code());
                    context.startActivity(intent);
                } else if (ExtraInfo.equals("TakeAttendance")) {
                    Intent intent=new Intent(context, TakeAttendance.class);
                    intent.putExtra("Course_code",course_registered_modelList.get(position).getCourse_code());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return course_registered_modelList.size();
    }

    public class modelViewHolder extends RecyclerView.ViewHolder{

        TextView Course_code,no_of_stu;
     //   private RecyclerViewClickListener mListener;

        public modelViewHolder(@NonNull View itemView) {
            super(itemView);
            Course_code=itemView.findViewById(R.id.course_code);
            no_of_stu=itemView.findViewById(R.id.registered_student);
        }

    }
}
