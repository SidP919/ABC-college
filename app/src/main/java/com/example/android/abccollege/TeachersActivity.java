package com.example.android.abccollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeachersActivity extends AppCompatActivity {

    String deptName;
    DatabaseReference databaseReference;
    private ArrayList<String> teachersList;
    private ArrayAdapter<String> adapter;
    ValueEventListener valEvtListnr = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot TeachersSnapshot : dataSnapshot.getChildren()) {
                String teacherName = (String) TeachersSnapshot.child("tName").getValue();
                String teacherPost = (String) TeachersSnapshot.child("tPost").getValue();
                teachersList.add(teacherName + " Designation: " + teacherPost);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(TeachersActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            deptName = (String) b.get("Dept Name");
        }
        Toast.makeText(this, "Dept Name: " + deptName, Toast.LENGTH_SHORT).show();

        ListView teachers = findViewById(R.id.teachers);
        teachersList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, teachersList);
        teachers.setAdapter(adapter);
        ReadTeachers();
    }

    public void addDeleteTeacher(View view) {
        Intent intn = new Intent(this, AddTeacherActivity.class);
        intn.putExtra("Dept Name", deptName);
        startActivity(intn);
    }

    private void ReadTeachers() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers").child(deptName);
        databaseReference.addValueEventListener(valEvtListnr);
//        deptRef2.addChildEventListener(childEventListener);
    }
}
