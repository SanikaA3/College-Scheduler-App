package com.example.a2340collegescheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.Date;


public class WeekAtAGlanceActivity extends AppCompatActivity {
    private ListView listViewAssignments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_at_a_glance);

        listViewAssignments = findViewById(R.id.listViewAssignments);

        Button addClassButton = findViewById(R.id.addClassButton);
        addClassButton.setOnClickListener(view -> {
            Intent intent = new Intent(WeekAtAGlanceActivity.this, AddClass.class);
            startActivity(intent);
        });

        Button btnAddAssignment = findViewById(R.id.btnAddAssignment);
        btnAddAssignment.setOnClickListener(view -> {
            Intent intent = new Intent(WeekAtAGlanceActivity.this, AddAssignmentActivity.class);
            startActivity(intent);
        });

        populateWeekView();
        populateAssignmentListView();
    }

    private void sortClassesByTime(List<ClassDetails> classes) {
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < classes.size() - 1; i++) {
            for (int j = i + 1; j < classes.size(); j++) {
                try {
                    Date time1 = sdf.parse(classes.get(i).getTime());
                    Date time2 = sdf.parse(classes.get(j).getTime());
                    if (time1.after(time2)) {
                        ClassDetails temp = classes.get(i);
                        classes.set(i, classes.get(j));
                        classes.set(j, temp);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void populateWeekView() {
        TableLayout weekTable = findViewById(R.id.weekTable);

        List<ClassDetails> classes = retrieveClasses();
        sortClassesByTime(classes);

        for (int i = weekTable.getChildCount() - 1; i > 0; i--) {
            weekTable.removeViewAt(i);
        }

        for (int i = 0; i < classes.size(); i++) {
            ClassDetails classDetail = classes.get(i);
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            row.setPadding(0, 0, 0, 20); // Add padding below each row for better separation

            row.setTag(classDetail.getCourseName());
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String courseName = (String) v.getTag();
                    confirmAndDeleteClass(courseName);
                    return true;
                }
            });

            for (int j = 0; j < 7; j++) {
                TextView dayView = new TextView(this);
                dayView.setPadding(8, 8, 8, 8);
                dayView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                if (classDetail.getDays()[j]) {
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

    private void populateAssignmentListView() {
        List<Assignment> assignments = retrieveAssignments();
        List<String> assignmentDetails = new ArrayList<>();
        final List<String> assignmentTitles = new ArrayList<>();

        for (Assignment assignment : assignments) {
            String detail = assignment.getTitle() + " - " + assignment.getAssociatedClass() +
                    " - Due: " + assignment.getDueDate() + " " + assignment.getDueTime() + " " + assignment.getAmPm();
            assignmentDetails.add(detail);
            assignmentTitles.add(assignment.getTitle()); // Assuming title is the key
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignmentDetails);
        listViewAssignments.setAdapter(adapter);

        listViewAssignments.setOnItemLongClickListener((parent, view, position, id) -> {
            String assignmentTitle = assignmentTitles.get(position);
            confirmAndDeleteAssignment(assignmentTitle);
            return true;
        });
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

    private List<Assignment> retrieveAssignments() {
        SharedPreferences prefs = getSharedPreferences("Assignments", MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        List<Assignment> assignments = new ArrayList<>();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = entry.getValue().toString();
            Type type = new TypeToken<Assignment>(){}.getType();
            Assignment assignment = gson.fromJson(json, type);
            assignments.add(assignment);
        }

        return assignments;
    }

    private void confirmAndDeleteClass(String courseName) {
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                Intent intent = new Intent(WeekAtAGlanceActivity.this, AddClass.class);
                intent.putExtra("courseName", courseName);
                intent.putExtra("editMode", true);
                startActivity(intent);
            } else if (which == 1) {
                deleteClass(courseName);
            }
        });
        builder.show();
    }

    private void confirmAndDeleteAssignment(String assignmentTitle) {
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                Intent intent = new Intent(WeekAtAGlanceActivity.this, AddAssignmentActivity.class);
                intent.putExtra("assignmentTitle", assignmentTitle);
                intent.putExtra("editMode", true);
                startActivity(intent);
            } else if (which == 1) {
                deleteAssignment(assignmentTitle);
            }
        });
        builder.show();
    }

    private void deleteClass(String courseName) {
        SharedPreferences prefs = getSharedPreferences("ClassDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(courseName);
        editor.apply();
        populateWeekView();
    }

    private void deleteAssignment(String assignmentTitle) {
        SharedPreferences prefs = getSharedPreferences("Assignments", MODE_PRIVATE);
        if (prefs.contains(assignmentTitle)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(assignmentTitle);
            editor.apply();

            populateAssignmentListView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateWeekView();
        populateAssignmentListView();
    }
}
