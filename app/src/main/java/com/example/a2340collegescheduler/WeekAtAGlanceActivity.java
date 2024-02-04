package com.example.a2340collegescheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class WeekAtAGlanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_at_a_glance);

        populateWeekView();

        Button addClassButton = findViewById(R.id.addClassButton);
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeekAtAGlanceActivity.this, AddClass.class);
                startActivity(intent);
            }
        });
    }

    private void sortClassesByTime(List<ClassDetails> classes) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Assuming your time format is like "14:00"
        Collections.sort(classes, (class1, class2) -> {
            try {
                Date time1 = sdf.parse(class1.getTime());
                Date time2 = sdf.parse(class2.getTime());
                return time1.compareTo(time2);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle parsing exceptions appropriately
                return 0;
            }
        });
    }



    private void populateWeekView() {
        TableLayout weekTable = findViewById(R.id.weekTable);

        // Retrieve and sort classes
        List<ClassDetails> classes = retrieveClasses();
        sortClassesByTime(classes);

        // Clear existing table rows, except for the header row
        weekTable.removeViews(1, weekTable.getChildCount() - 1);

        for (ClassDetails classDetail : classes) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setPadding(0, 0, 0, 20); // Add padding below each row for better separation

            // Setting tag for identifying the row for deletion
            row.setTag(classDetail.getCourseName());

            // Long press listener for row to initiate class deletion
            row.setOnLongClickListener(v -> {
                String courseName = v.getTag().toString();
                confirmAndDeleteClass(courseName);
                return true; // Indicates the callback consumed the long press
            });

            // Loop through days of the week
            for (int i = 0; i < 7; i++) {
                TextView dayView = new TextView(this);
                dayView.setPadding(8, 8, 8, 8);
                dayView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)); // Equal weight

                // Check if class occurs on this day and set text accordingly
                if (classDetail.getDays()[i]) {
                    String displayText = classDetail.getCourseName() + "\n" + classDetail.getTime() + "\nInstructor: " + classDetail.getInstructor();
                    dayView.setText(displayText);
                } else {
                    dayView.setText("");
                }
                row.addView(dayView);
            }

            weekTable.addView(row);
        }
    }



    private List<ClassDetails> retrieveClasses() {
        SharedPreferences prefs = getSharedPreferences("ClassDetails", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        List<ClassDetails> classDetailsList = new ArrayList<>();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = entry.getValue().toString();
            Type type = new TypeToken<ClassDetails>(){}.getType();
            ClassDetails classDetails = gson.fromJson(json, type);
            classDetailsList.add(classDetails);
        }

        return classDetailsList;
    }

    private void confirmAndDeleteClass(String courseName) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Class")
                .setMessage("Are you sure you want to delete this class?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    deleteClass(courseName);
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteClass(String courseName) {
        SharedPreferences prefs = getSharedPreferences("ClassDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(courseName);
        editor.apply();

        populateWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateWeekView(); // Refresh your week view
    }


}
