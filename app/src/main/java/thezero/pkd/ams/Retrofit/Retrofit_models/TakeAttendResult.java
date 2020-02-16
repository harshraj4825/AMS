package thezero.pkd.ams.Retrofit.Retrofit_models;

public class TakeAttendResult {
    private Integer si_no,userId;
    private String name;

    public TakeAttendResult(Integer si_no, Integer userId, String name) {
        this.si_no = si_no;
        this.userId = userId;
        this.name = name;
    }

    public Integer getSl_no() {
        return si_no;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setSi_no(Integer si_no) {
        this.si_no = si_no;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
