package thezero.pkd.ams.Retrofit.Retrofit_models;

import java.util.List;

import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.S_A_I_recyclerViewList;

public class S_A_I_Result {
    String Total_lacture;
    String Total_Present;
    List<S_A_I_recyclerViewList> Attendance_Status;

    public String getTotal_lacture() {
        return Total_lacture;
    }

    public String getTotal_Present() {
        return Total_Present;
    }

    public List <S_A_I_recyclerViewList> getS_a_i_recyclerViewLists() {
        return Attendance_Status;
    }
}
