package com.example.a2340collegescheduler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.gson.Gson;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class AddExam extends AppCompatActivity {
    private EditText courseText, timeText, location;
    private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private Spinner amPm;
    private Button addButton;
    private String originalCourseName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        courseText = findViewById(R.id.editCourseName);
        timeText = findViewById(R.id.editTime);
        location = findViewById(R.id.editLocation);
        monday = findViewById(R.id.checkMonday);
        tuesday = findViewById(R.id.checkTuesday);
        wednesday = findViewById(R.id.checkWednesday);
        thursday = findViewById(R.id.checkThursday);
        friday = findViewById(R.id.checkFriday);
        saturday = findViewById(R.id.checkSaturday);
        sunday = findViewById(R.id.checkSunday);
        amPm = findViewById(R.id.spinnerAMPM);
        addButton = findViewById(R.id.addExamButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ampm_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amPm.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            saveExam();
            finish();
        });

        if (getIntent().getBooleanExtra("editMode", false)) {
            originalCourseName = getIntent().getStringExtra("courseName");
            loadExamDetailsForEdit(originalCourseName);
        }
    }

    private void loadExamDetailsForEdit(String courseName) {
        SharedPreferences prefs = getSharedPreferences("Exam", MODE_PRIVATE);
        String json = prefs.getString(courseName, null);
        if (json != null) {
            Gson gson = new Gson();
            Exam exam = gson.fromJson(json, Exam.class);

            courseText.setText(exam.getCourseName());
            location.setText(exam.getLocation());
        }
    }

    private void saveExam() {
        String newExamName = courseText.getText().toString().trim();
        String time = timeText.getText().toString().trim() + " " + amPm.getSelectedItem().toString();
        String locationName = location.getText().toString().trim();
        boolean[] date = {
                monday.isChecked(),
                tuesday.isChecked(),
                wednesday.isChecked(),
                thursday.isChecked(),
                friday.isChecked(),
                saturday.isChecked(),
                sunday.isChecked()
        };

        Exam exam = new Exam(newExamName, date, time, locationName);

        SharedPreferences prefs = getSharedPreferences("Exam", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exam);


        if (originalCourseName != null && !originalCourseName.equals(newExamName)) {
            editor.remove(originalCourseName);
        }
        editor.putString(newExamName, json);
        editor.apply();
        finish();
    }

    private void clearFields() {
        courseText.setText("");
        timeText.setText("");
        location.setText("");
        monday.setChecked(false);
        tuesday.setChecked(false);
        wednesday.setChecked(false);
        thursday.setChecked(false);
        friday.setChecked(false);
        saturday.setChecked(false);
        sunday.setChecked(false);
        amPm.setSelection(0);
    }


}
