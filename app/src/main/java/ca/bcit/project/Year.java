package ca.bcit.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Year extends AppCompatActivity {

    Spinner spinnerYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);

    }

    public void OnClick(View view) {
        spinnerYear = findViewById(R.id.yearSpinner);
        Intent intent = new Intent(this, Make.class);
        intent.putExtra( "SelectedYear", String.valueOf(spinnerYear.getSelectedItem()));
        startActivity(intent);
    }
}