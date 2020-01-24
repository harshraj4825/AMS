package thezero.pkd.ams.Retrofit;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginFacultyResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginStudentResult;

public interface RetrofitRoutesInterface {
    //request for student login
    @POST("/student/login")
    Call <LoginStudentResult> executeLogin(@Body JsonObject body);

    //request for faculty login
    @POST("/faculty/login")
    Call<LoginFacultyResult> executeFacultyLogin(@Body JsonObject jsonObject);
}
