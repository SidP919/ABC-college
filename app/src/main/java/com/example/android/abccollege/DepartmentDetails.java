package com.example.android.abccollege;

class DepartmentDetails {

    String deptName;
    String deptLoc;

    public DepartmentDetails(String s1, String s) {
        this.deptName = s1;
        this.deptLoc = s;
    }

    public DepartmentDetails() {
    }

    public String getDeptName() {
        return deptName;
    }

    public String getDeptLoc() {
        return deptLoc;
    }


}
