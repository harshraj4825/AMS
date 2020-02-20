package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.TakeAttendResult;

public class SubmitAttendAdaptor extends RecyclerView.Adapter<SubmitAttendAdaptor.SubmitAttendModel> {
    List <TakeAttendResult> takeAttendResults;
    Context context;

    public SubmitAttendAdaptor(Context context,List <TakeAttendResult> takeAttendResults) {
        this.takeAttendResults = takeAttendResults;
        this.context = context;
    }

    @NonNull
    @Override
    public SubmitAttendModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.take_attendance_recycler,parent,false);
        return new SubmitAttendModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubmitAttendModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SubmitAttendModel extends RecyclerView.ViewHolder {
        TextView sl_no,name,roll_no;
        CheckBox takeCheckBox;
        public SubmitAttendModel(@NonNull View itemView) {
            super(itemView);
            sl_no=itemView.findViewById(R.id.take_sa_no);
            roll_no=itemView.findViewById(R.id.take_stu_roll);
            name=itemView.findViewById(R.id.take_stu_name);
            takeCheckBox=itemView.findViewById(R.id.take_checkbox);
            takeCheckBox.setChecked(true);
            takeCheckBox.setClickable(false);
        }

    }
}
