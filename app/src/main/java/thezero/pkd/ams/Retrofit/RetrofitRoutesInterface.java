package thezero.pkd.ams.Retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import thezero.pkd.ams.Retrofit.Retrofit_models.AttenListResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.C_I_Result;
import thezero.pkd.ams.Retrofit.Retrofit_models.A_B_D_Result;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginFacultyResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.LoginStudentResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.S_A_I_Result;
import thezero.pkd.ams.Retrofit.Retrofit_models.TakeAttendResult;
import thezero.pkd.ams.Retrofit.Retrofit_models.course_registered_model;

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
    //request for course register
    @POST("/courses/register")
    Call<Void> executeFacCourse_register(@Body JsonObject jsonObject);
    //to get all courses which faculty registered
    @POST("/courses/get-all")
    Call<List<course_registered_model>> executeFacGetCourses(@Body JsonObject jsonObject);
    //get course/registered student details.
    @POST("/courses/findone")
    Call<C_I_Result> executeC_I_result(@Body JsonObject jsonObject);
    //to get attendance of a student for a particular course
    @POST("/courses/student-attendance")
    Call<S_A_I_Result> executeS_A_I_result(@Body JsonObject jsonObject);

    @POST("/courses/get-course-attendance")
    Call<A_B_D_Result> executeA_B_D_result(@Body JsonObject jsonObject);
    @POST("/courses/get-attendance-students-list")
    Call<List<AttenListResult>> executeAttendanceList(@Body JsonObject jsonObject);
    @POST("/courses/all-registered-student")
    Call<List<TakeAttendResult>> executeTakeAttend(@Body JsonObject jsonObject);
    @PUT("/courses/attendance/{course_code}")
    Call<Void> executeSubmitAttendance(@Path("course_code") JsonObject course_code, @Body JSONArray jsonArray );
}
