package ca.bcit.project;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Make extends AppCompatActivity {

    DatabaseReference firebase;
    ArrayList<String> df;
    Long selectedYearI;
    String selectedYear;
    Spinner makeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        Intent intent = getIntent();
        selectedYear = intent.getStringExtra("SelectedYear");

        selectedYearI = Long.parseLong(selectedYear);
        df = new ArrayList<String>();
        firebase = FirebaseDatabase.getInstance().getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item, df);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.makeSpinner);
        spinner.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                df.clear();
                for (DataSnapshot carSnapShot : dataSnapshot.getChildren()) {
                    if(((Long) carSnapShot.child("YEAR").getValue()).equals(selectedYearI)){
                        boolean e = false;
                        for(int i=0;i<df.size();i++){
                            if(df.get(i).equals(carSnapShot.child("MAKE").getValue())){
                                e = true;
                            }
                        }
                        if(!e){
                            df.add(carSnapShot.child("MAKE").getValue().toString());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void OnClick(View view) {
        makeSpinner = findViewById(R.id.makeSpinner);
        Intent intent = new Intent(this, Model.class);
        intent.putExtra("SelectedMake", String.valueOf(makeSpinner.getSelectedItem()));
        intent.putExtra("SelectedYear", selectedYear);
        startActivity(intent);
    }
}