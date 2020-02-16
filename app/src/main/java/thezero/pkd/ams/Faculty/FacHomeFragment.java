package thezero.pkd.ams.Faculty;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Adapters.facHomeAdapter;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.Retrofit.Retrofit_models.course_registered_model;
import thezero.pkd.ams.utils.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private facHomeAdapter facHomeAdapter;
    private List<course_registered_model> course_registered_modelList;
    private RetrofitRoutesInterface retrofitRoutesInterface;
    private static String extraInfo="Home";


    public FacHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fac_home, container, false);
        recyclerView=view.findViewById(R.id.get_all_RegisteredCourse);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
      //  textView=view.findViewById(R.id.textViewResult);

        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet
        String path=new User(getContext()).getUserId();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("UserId",path);
        Call<List<course_registered_model>> call=retrofitRoutesInterface.executeFacGetCourses(jsonObject);
        call.enqueue(new Callback <List <course_registered_model>>() {
            @Override
            public void onResponse(Call <List <course_registered_model>> call, Response <List <course_registered_model>> response) {
                course_registered_modelList=response.body();
                if(course_registered_modelList.size()==0){
                    Toast.makeText(getContext(),"No Course available",Toast.LENGTH_SHORT).show();
                }
                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_border));
                recyclerView.addItemDecoration(dividerItemDecoration);
                facHomeAdapter=new facHomeAdapter(getContext(),course_registered_modelList,extraInfo);
                recyclerView.setAdapter(facHomeAdapter);
            }

            @Override
            public void onFailure(Call <List <course_registered_model>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }
}
