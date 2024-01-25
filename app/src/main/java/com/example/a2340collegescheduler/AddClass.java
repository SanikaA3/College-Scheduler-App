package com.example.a2340collegescheduler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddClass extends AppCompatActivity {
    private EditText courseText;
    private EditText timeText;
    private EditText instructor;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;
    private Spinner amPm;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_classes);

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

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View myView){
                saveClass();
            }
        });
    }


    private void saveClass(){
        String courseString = courseText.getText().toString();
        String instructorString = instructor.getText().toString();
        String timeString = timeText.getText().toString();
        String amOrPm = amPm.getSelectedItem().toString();

        boolean[] days = new boolean[7];
        days[0] = monday.isChecked();
        days[1] = tuesday.isChecked();
        days[2] = wednesday.isChecked();
        days[3] = thursday.isChecked();
        days[4] = friday.isChecked();
        days[5] = saturday.isChecked();
        days[6] = sunday.isChecked();

        ClassDetails myClass = new ClassDetails(courseString, timeString, instructorString, amOrPm, days);

        SharedPreferences pref = getSharedPreferences("Class Details", MODE_PRIVATE);

        SharedPreferences.Editor theEditor = pref.edit();

        theEditor.putString("Course", courseString);
        theEditor.putString("Instructor", instructorString);
        theEditor.putString("Time", timeString);
        theEditor.putString("AMorPM",amOrPm);
        theEditor.putBoolean("Monday",days[0]);
        theEditor.putBoolean("Tuesday",days[1]);
        theEditor.putBoolean("Wednesday",days[2]);
        theEditor.putBoolean("Thursday",days[3]);
        theEditor.putBoolean("Friday",days[4]);
        theEditor.putBoolean("Saturday",days[5]);
        theEditor.putBoolean("Sunday",days[6]);

        theEditor.apply();
    }
}
