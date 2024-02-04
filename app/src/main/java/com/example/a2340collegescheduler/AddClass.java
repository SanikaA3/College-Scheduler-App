package com.example.a2340collegescheduler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

public class AddClass extends AppCompatActivity {
    private EditText courseText, timeText, instructor;
    private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private Spinner amPm;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

        // Initialize UI components
        courseText = findViewById(R.id.editCourseName);
        timeText = findViewById(R.id.editTime);
        instructor = findViewById(R.id.editInstructor);
        monday = findViewById(R.id.checkMonday);
        tuesday = findViewById(R.id.checkTuesday);
        wednesday = findViewById(R.id.checkWednesday);
        thursday = findViewById(R.id.checkThursday);
        friday = findViewById(R.id.checkFriday);
        saturday = findViewById(R.id.checkSaturday);
        sunday = findViewById(R.id.checkSunday);
        amPm = findViewById(R.id.spinnerAMPM);
        addButton = findViewById(R.id.addClassButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClass();
            }
        });
    }

    private void saveClass() {
        String courseName = courseText.getText().toString();
        String time = timeText.getText().toString() + " " + amPm.getSelectedItem().toString();
        String instructorName = instructor.getText().toString();
        boolean[] days = new boolean[]{
                monday.isChecked(),
                tuesday.isChecked(),
                wednesday.isChecked(),
                thursday.isChecked(),
                friday.isChecked(),
                saturday.isChecked(),
                sunday.isChecked()
        };

        ClassDetails classDetails = new ClassDetails(courseName, time, instructorName, days);

        SharedPreferences prefs = getSharedPreferences("ClassDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(classDetails);
        editor.putString(courseName, json);
        editor.apply();

        Log.d("AddClass", "Class added successfully: " + json);

        Toast.makeText(AddClass.this, "Class added successfully!", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        courseText.setText("");
        timeText.setText("");
        instructor.setText("");
        monday.setChecked(false);
        tuesday.setChecked(false);
        wednesday.setChecked(false);
        thursday.setChecked(false);
        friday.setChecked(false);
        saturday.setChecked(false);
        sunday.setChecked(false);
        amPm.setSelection(0); // Assuming your Spinner is properly set up with an ArrayAdapter
    }
}
