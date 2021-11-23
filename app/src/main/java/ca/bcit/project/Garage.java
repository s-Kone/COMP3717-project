package ca.bcit.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Garage extends AppCompatActivity {

    DatabaseReference reference;
    String selectedMake;
    Long selectedYearI;
    String selectedModel;
    String emissions;
    ArrayList<String> garage;
    TextView car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        Intent intent = getIntent();
        selectedMake = intent.getStringExtra("SelectedMake");
        String selectedYear = intent.getStringExtra("SelectedYear");
        selectedModel = intent.getStringExtra("SelectedModel");

        selectedYearI = Long.parseLong(selectedYear);

        reference = FirebaseDatabase.getInstance().getReference();
        car = findViewById(R.id.garage);

        car.setText(emissions);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot carSnapShot : dataSnapshot.getChildren()) {
                    if(carSnapShot.child("MAKE").getValue().toString().equals(selectedMake) && ((Long) carSnapShot.child("YEAR").getValue()).equals(selectedYearI)){
                        if(carSnapShot.child("MODEL").getValue().toString().equals(selectedModel)){
                            emissions = (carSnapShot.child("EMISSIONS").getValue().toString());
                        }
                    }
                }
                car.setText("Gallons per kilometer" + emissions);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}