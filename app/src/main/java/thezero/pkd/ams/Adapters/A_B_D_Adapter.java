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

import thezero.pkd.ams.Faculty.AttendanceList;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.A_B_D_recyclerViewList;

public class A_B_D_Adapter extends RecyclerView.Adapter<A_B_D_Adapter.A_B_D_ModelViewAdapter> {
    private String course_code;
    Context context;
    private List<A_B_D_recyclerViewList> a_b_d_recyclerViewLists;


    public A_B_D_Adapter(Context context,List<A_B_D_recyclerViewList> a_b_d_recyclerViewLists,String course_code){
        this.context=context;
        this.a_b_d_recyclerViewLists=a_b_d_recyclerViewLists;
        this.course_code=course_code;
    }
    @NonNull
    @Override
    public A_B_D_ModelViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.a_b_d_recyclerlist,parent,false);
        return new A_B_D_ModelViewAdapter(view);
    }
    @Override
    public void onBindViewHolder(@NonNull A_B_D_ModelViewAdapter holder, int position) {
        holder.Total_stu_present.setText(a_b_d_recyclerViewLists.get(position).getTotal_stu_present().toString());
        holder.Date.setText(a_b_d_recyclerViewLists.get(position).getDate());
        holder.sl_no.setText(a_b_d_recyclerViewLists.get(position).getSl_no().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AttendanceList.class);
                intent.putExtra("date",a_b_d_recyclerViewLists.get(position).getDate());
                intent.putExtra("course_code",course_code);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return a_b_d_recyclerViewLists.size();
    }

    public class A_B_D_ModelViewAdapter extends RecyclerView.ViewHolder{
        TextView sl_no,Date,Total_stu_present;
        public A_B_D_ModelViewAdapter(@NonNull View itemView) {
            super(itemView);
            sl_no=itemView.findViewById(R.id.a_b_d_sl_no);
            Date=itemView.findViewById(R.id.a_b_d_date);
            Total_stu_present=itemView.findViewById(R.id.a_b_d_attend_stu);
        }
    }
}
