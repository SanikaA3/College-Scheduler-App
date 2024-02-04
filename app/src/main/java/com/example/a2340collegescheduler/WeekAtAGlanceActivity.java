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
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private void populateWeekView() {
        TableLayout weekTable = findViewById(R.id.weekTable);
        List<ClassDetails> classes = retrieveClasses();

        for (ClassDetails classDetail : classes) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            // Create a TextView for each day of the week; adjust as needed for your data
            for (int i = 0; i < 7; i++) {
                TextView dayView = new TextView(this);
                dayView.setPadding(8, 8, 8, 8);

                // Assuming you have a method in ClassDetails to check if a class occurs on a given day
                if (classDetail.getDays()[i]) {
                    String displayText = classDetail.getCourseName() + "\n" + classDetail.getTime();
                    dayView.setText(displayText);
                } else {
                    dayView.setText("");
                }

                row.addView(dayView);
            }

            weekTable.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
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
}
