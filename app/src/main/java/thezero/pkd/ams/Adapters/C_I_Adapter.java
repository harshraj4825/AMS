package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.C_I_recyclerViewList;
import thezero.pkd.ams.Students.Student_attendance_Information;

public class C_I_Adapter extends RecyclerView.Adapter<C_I_Adapter.CourseInformationModelViewAdaptor> {
     private List<C_I_recyclerViewList> c_i_recyclerViewLists;
     Context context;
     String course_code;

     public C_I_Adapter(Context context,List<C_I_recyclerViewList> c_i_recyclerViewLists,String course_code){
         this.context=context;
         this.c_i_recyclerViewLists=c_i_recyclerViewLists;
         this.course_code=course_code;
     }

    @NonNull
    @Override
    public CourseInformationModelViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.c_i_recycler_list,parent,false);
        return new CourseInformationModelViewAdaptor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseInformationModelViewAdaptor holder, int position) {
         holder.student_name.setText(c_i_recyclerViewLists.get(position).getStudent_name());
         holder.roll_no.setText(c_i_recyclerViewLists.get(position).getStudent_roll_number());
         String Persentage=c_i_recyclerViewLists.get(position).getPersentage_of_attendance()+" %";
         holder.percentage_attendance.setText(Persentage);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(context, Student_attendance_Information.class);
                 intent.putExtra("ref",1);
                 intent.putExtra("student_roll",c_i_recyclerViewLists.get(position).getStudent_roll_number());
                 intent.putExtra("student_name",c_i_recyclerViewLists.get(position).getStudent_name());
                 intent.putExtra("course_code",course_code);
                 context.startActivity(intent);

             }
         });

    }

    @Override
    public int getItemCount() {
        return c_i_recyclerViewLists.size();
    }

    public class CourseInformationModelViewAdaptor extends RecyclerView.ViewHolder{
         TextView roll_no,student_name,percentage_attendance;

        public CourseInformationModelViewAdaptor(@NonNull View itemView) {
            super(itemView);
            roll_no=itemView.findViewById(R.id.c_i_studentRoll);
            student_name=itemView.findViewById(R.id.c_i_student_Name);
            percentage_attendance=itemView.findViewById(R.id.c_i_attendance);
        }
    }
}
