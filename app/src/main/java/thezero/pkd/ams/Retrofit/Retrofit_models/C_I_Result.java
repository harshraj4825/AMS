package thezero.pkd.ams.Retrofit.Retrofit_models;

import java.util.List;

import thezero.pkd.ams.Retrofit.Retrofit_models.Helper.C_I_recyclerViewList;

public class C_I_Result {
    String course_name;
    String faculty_name;
    Integer total_students;
    List<C_I_recyclerViewList> student_information;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public List <C_I_recyclerViewList> getStudent_information() {
        return student_information;
    }

    public void setStudent_information(List <C_I_recyclerViewList> student_information) {
        this.student_information = student_information;
    }

    public Integer getTotal_students() {
        return total_students;
    }

    public void setTotal_students(Integer total_students) {
        this.total_students = total_students;
    }
}
