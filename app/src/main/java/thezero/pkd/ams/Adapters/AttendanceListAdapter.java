package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.AttenListResult;

public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.AttendListViewModel>{
    List<AttenListResult> attenListResults;
    Context context;

    public AttendanceListAdapter(Context context,List <AttenListResult> attenListResults) {
        this.attenListResults = attenListResults;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendListViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attend_recycler_list,parent,false);
        return new AttendListViewModel(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AttendListViewModel holder, int position) {
        if(attenListResults.get(position).getAttend().equals("Absent")){
            int currentColor = Color.rgb(255,153,153);
            holder.parent.setBackgroundColor(currentColor);
        }
        holder.tStatus.setText(attenListResults.get(position).getAttend());
        holder.tRoll.setText(attenListResults.get(position).getStudent_id());
    }

    @Override
    public int getItemCount() {
        return attenListResults.size();
    }

    public class AttendListViewModel extends RecyclerView.ViewHolder{
        TextView tRoll,tStatus;
        RelativeLayout parent;

        public AttendListViewModel(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.attendRecyclerParent);
            tRoll=itemView.findViewById(R.id.attend_roll);
            tStatus=itemView.findViewById(R.id.attend_status);
        }
    }
}
