package thezero.pkd.ams.Retrofit.Retrofit_models;

import java.util.List;

import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.A_B_D_recyclerViewList;

public class A_B_D_Result {
    String course_name;
    Integer total_lecture;
    List<A_B_D_recyclerViewList> attenndanceInfo;

    public String getCourse_name() {
        return course_name;
    }

    public Integer getTotal_lecture() {
        return total_lecture;
    }

    public List <A_B_D_recyclerViewList> getA_b_d_recyclerViewLists() {
        return attenndanceInfo;
    }
}