package thezero.pkd.ams.Students;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thezero.pkd.ams.Faculty.FacHomeFragment;
import thezero.pkd.ams.R;
import thezero.pkd.ams.Retrofit.ClientApi;
import thezero.pkd.ams.Retrofit.RetrofitRoutesInterface;
import thezero.pkd.ams.utils.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
    private TextView old_password,new_password,cn_new_password;
    Button change_btn;
    private RetrofitRoutesInterface retrofitRoutesInterface;



    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_password, container, false);
        retrofitRoutesInterface= ClientApi.getInstance().getApi();//connecting to the internet
        change_btn=view.findViewById(R.id.new_change_password);
        old_password=view.findViewById(R.id.old_password);
        new_password=view.findViewById(R.id.m_new_password);
        cn_new_password=view.findViewById(R.id.confirm_new_password);
        change_btn.setOnClickListener(change_btn_listener);
        // Inflate the layout for this fragment
        return view;
    }
    View.OnClickListener change_btn_listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String old_pass=old_password.getText().toString();
            String new_pass=new_password.getText().toString();
            String cn_new_pass=cn_new_password.getText().toString();
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("Password",old_pass);
            jsonObject.addProperty("NewPassword",new_pass);
            String path=new User(getContext()).getUserId();
            if(TextUtils.isEmpty(old_pass)||TextUtils.isEmpty(new_pass)||TextUtils.isEmpty(cn_new_pass)){
                Toast.makeText(getContext(),"All field required",Toast.LENGTH_SHORT).show();
            }else if(!new_pass.equals(cn_new_pass)){
                Toast.makeText(getContext(),"Both password must be same",Toast.LENGTH_SHORT).show();
            }
            //for student
            else if(TextUtils.isDigitsOnly(path)){
                if(!new_pass.equals(old_pass)){
                    Call<Void> call=retrofitRoutesInterface.executeStudChange_pass(Integer.parseInt(path),jsonObject);
                    call.enqueue(new Callback <Void>() {
                        @Override
                        public void onResponse(Call <Void> call, Response <Void> response) {
                            if(response.code()==200){
                                Toast.makeText(getContext(),"Password has successfully changed",Toast.LENGTH_SHORT).show();
                                FragmentTransaction fragmentManager=getFragmentManager().beginTransaction();
                                fragmentManager.replace(R.id.fragment_container,new HomeFragment());
                                fragmentManager.commit();
                            }else if (response.code()==201){
                                Toast.makeText(getContext(),"Your old password not correct",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call <Void> call, Throwable t) {
                            Toast.makeText(getContext(),t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(),"New password must not be same to old password",Toast.LENGTH_SHORT).show();
                }
            }
            //for faculty
            else{
                if(!new_pass.equals(old_pass)){
                    Call<Void> call=retrofitRoutesInterface.executeFacChange_pass(path,jsonObject);
                    call.enqueue(new Callback <Void>() {
                        @Override
                        public void onResponse(Call <Void> call, Response <Void> response) {
                            if (response.code()==200){
                                Toast.makeText(getContext(),"Password has successfully changed",Toast.LENGTH_SHORT).show();
                                FragmentTransaction fragmentManager=getFragmentManager().beginTransaction();
                                fragmentManager.replace(R.id.fac_fragment_container,new FacHomeFragment());
                                fragmentManager.commit();

                            }else if(response.code()==201){
                                Toast.makeText(getContext(),"Your old password not correct",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call <Void> call, Throwable t) {
                            Toast.makeText(getContext(),t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getContext(),"New password must not be same to old password",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
