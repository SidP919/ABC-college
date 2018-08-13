package com.example.android.abccollege;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDeptActivity extends AppCompatActivity {

    EditText editText, loc_editText; //editText2, editText3, editText4, editText5;

    FirebaseDatabase db;
    String s1, s;//s2, s3, s4, s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dept);

        db = FirebaseDatabase.getInstance();
    }

    public void addDept(View view) {

        editText = findViewById(R.id.editText);
        loc_editText = findViewById(R.id.location_et);
//        editText2 = findViewById(R.id.editText2);
//        editText3 = findViewById(R.id.editText3);
//        editText4 = findViewById(R.id.editText4);
//        editText5 = findViewById(R.id.editText5);

        s1 = (editText.getText().toString());
        s = loc_editText.getText().toString();
//        s2 = (editText2.getText().toString());
//        s3 = (editText3.getText().toString());
//        s4 = (editText4.getText().toString());
//        s5 = (editText5.getText().toString());

        DatabaseReference deptRef = db.getReference("Departments");
        DepartmentDetails d = new DepartmentDetails(s1, s);//s2, s3, s4, s5);
        deptRef.child(s1).setValue(d);
        deptRef.child(s1).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(AddDeptActivity.this, "New Department Registered", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddDeptActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddDeptActivity.this, "Unable to Register Department", Toast.LENGTH_LONG).show();
            }
        });

    }
}
