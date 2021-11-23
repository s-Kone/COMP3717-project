package ca.bcit.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    ArrayAdapter<String> adapter;
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

        selectedYearI = Long.parseLong(selectedYear);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        df = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, df);
        adapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        modelSpinner = (Spinner) findViewById(R.id.modelSpinner);
        modelSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                df.clear();
                for (DataSnapshot carSnapShot : dataSnapshot.getChildren()) {
                    if(carSnapShot.child("MAKE").getValue().toString().equals(selectedMake) &&((Long) carSnapShot.child("YEAR").getValue()).equals(selectedYearI)){
                        df.add(carSnapShot.child("MODEL").getValue().toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void OnClick(View view) {
        Intent intent = new Intent(this, Garage.class);

        if(modelSpinner.getSelectedItem() != null){
            intent.putExtra("SelectedModel", modelSpinner.getSelectedItem().toString());
        }
        intent.putExtra("SelectedYear", selectedYear);
        intent.putExtra("SelectedMake", selectedMake);
        startActivity(intent);
    }
}