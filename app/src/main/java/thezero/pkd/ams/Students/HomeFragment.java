package thezero.pkd.ams.Students;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.stu_home_Adapter;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.student_home_course_list;
import thezero.pkd.ams.utils.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<student_home_course_list> student_home_course_lists;
    private stu_home_Adapter stu_home_adapter;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.stu_home_recycler);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet
        String path=new User(getContext()).getUserId();
        Call<List<student_home_course_list>> call=retrofitRoutesInterface.executeStudCourseList(path);
        call.enqueue(new Callback <List <student_home_course_list>>() {
            @Override
            public void onResponse(Call <List <student_home_course_list>> call, Response <List <student_home_course_list>> response) {
                student_home_course_lists=response.body();
                if (student_home_course_lists.size()!=0){
                    DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                    dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_border));
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    stu_home_adapter=new stu_home_Adapter(getContext(),student_home_course_lists);
                    recyclerView.setAdapter(stu_home_adapter);
                }
            }
            @Override
            public void onFailure(Call <List <student_home_course_list>> call, Throwable t) {

            }
        });
        return view;
    }

}
