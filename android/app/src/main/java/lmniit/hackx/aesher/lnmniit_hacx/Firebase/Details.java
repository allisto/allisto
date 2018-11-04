package lmniit.hackx.aesher.lnmniit_hacx.Firebase;

public class Details {
    public String dob, patientName, gender, bloodGroup;
    public Details(){}

    public Details(String dob, String patientName, String gender, String bloodGroup){
        this.dob = dob;
        this.patientName = patientName;
        this.gender  = gender;
        this.bloodGroup = bloodGroup;
    }


    public String getGender() {
        return gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getDob() {
        return dob;
    }

    public String getPatientName() {
        return patientName;
    }
}
