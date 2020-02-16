package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.Faculty.TakeAttendance;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.TakeAttendResult;

public class TakeAttend_Adaptor extends RecyclerView.Adapter<TakeAttend_Adaptor.TakeAttendViewModel> {
    OnItemCheckListener onItemClick;
    List<TakeAttendResult> takeAttendResults;
    Context context;
   // private final boolean[] mCheckedStateA;

    public interface OnItemCheckListener {
        void onItemCheck(TakeAttendResult item);
        void onItemUncheck(TakeAttendResult item);
    }
    @NonNull
    public OnItemCheckListener onItemCheckListener;

    public TakeAttend_Adaptor(Context context, List <TakeAttendResult> takeAttendResults,@NonNull OnItemCheckListener onItemCheckListener) {
        this.takeAttendResults = takeAttendResults;
        this.context = context;
        this.onItemClick = onItemCheckListener;
//        mCheckedStateA = new boolean[takeAttendResults.size()];
    }



    @NonNull
    @Override
    public TakeAttendViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.take_attendance_recycler,parent,false);
        return new TakeAttendViewModel(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TakeAttendViewModel holder, int position) {
        final  TakeAttendResult currentItem = takeAttendResults.get(position);
        holder.name.setText(takeAttendResults.get(position).getName());
        holder.roll_no.setText(takeAttendResults.get(position).getUserId().toString());
        holder.sl_no.setText(takeAttendResults.get(position).getSl_no().toString());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.takeCheckBox.setChecked(!holder.takeCheckBox.isChecked());
                if (((TakeAttendViewModel) holder).takeCheckBox.isChecked()) {
                    onItemClick.onItemCheck(currentItem);
                } else {
                    onItemClick.onItemUncheck(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return takeAttendResults.size();
    }

    public class TakeAttendViewModel extends RecyclerView.ViewHolder{
        TextView sl_no,name,roll_no;
        CheckBox takeCheckBox;

        public TakeAttendViewModel(@NonNull View itemView) {
            super(itemView);
            sl_no=itemView.findViewById(R.id.take_sa_no);
            roll_no=itemView.findViewById(R.id.take_stu_roll);
            name=itemView.findViewById(R.id.take_stu_name);
            takeCheckBox=itemView.findViewById(R.id.take_checkbox);
            takeCheckBox.setChecked(true);
            takeCheckBox.setClickable(false);
        }
        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
