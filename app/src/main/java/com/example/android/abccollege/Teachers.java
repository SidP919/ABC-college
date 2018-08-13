package com.example.android.abccollege;

public class Teachers {
    String tName;
    String tPost;
    String deptName;

    public Teachers(String tName, String tPost, String deptName) {
        this.tName = tName;
        this.tPost = tPost;

        this.deptName = deptName;
    }

    public Teachers() {
    }

    public String getDeptName() {
        return deptName;
    }

    public String gettName() {
        return tName;
    }

    public String gettPost() {
        return tPost;
    }
}
