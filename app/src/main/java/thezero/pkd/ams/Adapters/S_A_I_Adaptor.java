package thezero.pkd.ams.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.S_A_I_recyclerViewList;

public class S_A_I_Adaptor extends RecyclerView.Adapter<S_A_I_Adaptor.S_A_I_Model>{
    private List<S_A_I_recyclerViewList> sAIRecyclerViewLists;
    private Context context;

    public S_A_I_Adaptor(Context context,List<S_A_I_recyclerViewList> sAIRecyclerViewLists){
        this.context=context;
        this.sAIRecyclerViewLists=sAIRecyclerViewLists;
    }

    @NonNull
    @Override
    public S_A_I_Model onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.s_a_i_recycle_list,parent,false);
        return new S_A_I_Model(view);
    }

    @Override
    public void onBindViewHolder(@NonNull S_A_I_Model holder, int position) {
        holder.date.setText(sAIRecyclerViewLists.get(position).getDate());
        holder.status.setText(sAIRecyclerViewLists.get(position).getStatus());
        holder.sl_no.setText(sAIRecyclerViewLists.get(position).getSl_no().toString());
    }

    @Override
    public int getItemCount() {
        return sAIRecyclerViewLists.size();
    }

    public class S_A_I_Model extends RecyclerView.ViewHolder{
        TextView date,status,sl_no;
        public S_A_I_Model(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.s_a_i_date);
            status=itemView.findViewById(R.id.s_a_i_status);
            sl_no=itemView.findViewById(R.id.s_a_i_sl_no);
        }
    }
}
