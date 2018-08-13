package com.example.android.abccollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DepartmentDetails d;
    private ListView deptList;
    private ArrayList<String> arr;
    private ArrayAdapter<String> adapter;
    ValueEventListener valEvtListnr = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot DepartmentSnapshot : dataSnapshot.getChildren()) {
                String deptName = (String) DepartmentSnapshot.child("deptName").getValue();
                arr.add(deptName);
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        deptList = findViewById(R.id.dept_list);
        adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arr);
        deptList.setAdapter(adapter);
        ReadDepts();
        deptList.setOnItemClickListener(new MyListener());
    }

    public void goToAddDept(View view) {
        Intent intent = new Intent(this, AddDeptActivity.class);
        startActivity(intent);
    }

    private void ReadDepts() {
        DatabaseReference deptRef = db.getReference("Departments");
        deptRef.addValueEventListener(valEvtListnr);
//        deptRef2.addChildEventListener(childEventListener);
    }

//    ChildEventListener childEventListener = new ChildEventListener() {
//        @Override
//        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//            Department d = new Department();
//            d = dataSnapshot.getValue(Department.class);
//            String DeptName = null;
//            if (d != null) {
//                DeptName = d.getDeptName();
//            }
//            arr.add(DeptName);
//            adapter.notifyDataSetChanged();
//            // ...
//        }
//        @Override
//        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//        }
//        @Override
//        public void onChildRemoved(DataSnapshot dataSnapshot) {
//        }
//        @Override
//        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//        }
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//        }
//    };

    public class MyListener implements AdapterView.OnItemClickListener {

        @Override

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            final String deptName = arr.get(i);
            DatabaseReference deptRef = db.getReference("Departments");
            deptRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot DepartmentSnapshot : dataSnapshot.getChildren()) {
//                        String dept = (String)DepartmentSnapshot.child("deptName").getValue();
                        d = DepartmentSnapshot.getValue(DepartmentDetails.class);
                        if (d != null && d.getDeptName().equals(deptName)) {
                            Intent intent = new Intent(MainActivity.this, TeachersActivity.class);
                            intent.putExtra("Dept Name", d.getDeptName());
                            Toast.makeText(MainActivity.this, "Dept Name: " + d.getDeptName(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
