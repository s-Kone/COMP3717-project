package ca.bcit.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Model extends AppCompatActivity {

    DatabaseReference databaseReference;
    String selectedMake;
    String selectedYear;
    ArrayList<String> df;
    Long selectedYearI;
    Spinner modelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        //filler code for spinner

        Intent intent = getIntent();
        selectedMake = intent.getStringExtra("SelectedMake");
        selectedYear = intent.getStringExtra("SelectedYear");

        System.out.println(selectedMake);

        selectedYearI = Long.parseLong(selectedYear);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        df = new ArrayList<String>();



    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                df.clear();
                for (DataSnapshot carSnapShot : dataSnapshot.getChildren()) {
//                    if(carSnapShot.child("MAKE").getValue().toString().equals(selectedMake) &&((Long) carSnapShot.child("YEAR").getValue()).equals(selectedYearI)){
//                        df.add(carSnapShot.child("MODEL").getValue().toString());
//                    }
                }
                System.out.println(df);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void OnClick(View view) {

    }
}