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

public class AddTeacherActivity extends AppCompatActivity {

    EditText ed1, ed2;
    String s, s1, deptName;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        Intent intn = getIntent();
        Bundle b = intn.getExtras();
        if (b != null) {
            deptName = (String) b.get("Dept Name");
        }
        Toast.makeText(this, "Dept Name: " + deptName, Toast.LENGTH_SHORT).show();
        ed1 = findViewById(R.id.editText6);
        ed2 = findViewById(R.id.editText7);
    }

    public void addTeacher(View view) {
        s1 = ed1.getText().toString();
        s = ed2.getText().toString();
        databaseReference = (FirebaseDatabase.getInstance()).getReference("Teachers").child(deptName);
        Teachers t = new Teachers(s1, s, deptName);
        databaseReference.child(s1).setValue(t);

        databaseReference.child(s1).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(AddTeacherActivity.this, "New Teacher Registered", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddTeacherActivity.this, TeachersActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddTeacherActivity.this, "Unable to Register teacher", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteTeacher(View view) {

        s1 = ed1.getText().toString();
        s = ed2.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
        DatabaseReference ref = databaseReference.child(deptName);
        DatabaseReference ref2 = ref.child(s1);
        ref2.removeValue();
        Intent intent = new Intent(this, TeachersActivity.class);
        startActivity(intent);
    }
}
