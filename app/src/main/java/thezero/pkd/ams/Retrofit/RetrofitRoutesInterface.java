package thezero.pkd.ams.Retrofit;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginFacultyResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginStudentResult;

public interface RetrofitRoutesInterface {
    //request for student login
    @POST("/student/login")
    Call <LoginStudentResult> executeLogin(@Body JsonObject body);
    //request for student sign up
    @POST("/student/signup")
    Call<Void> executeStudentSignUp(@Body JsonObject body);
    //to change student password
    @PUT("/student/{userId}")
    Call<Void> executeStudChange_pass(@Path("userId") Integer userId,@Body JsonObject jsonObject);
    //request for faculty login
    @POST("/faculty/login")
    Call<LoginFacultyResult> executeFacultyLogin(@Body JsonObject jsonObject);
    @POST("/faculty/signup")
    Call<Void> executeFacultySignUp(@Body JsonObject body);
    @PUT("/faculty/{email}")
    Call<Void> executeFacChange_pass(@Path("email") String email,@Body JsonObject jsonObject);
}
