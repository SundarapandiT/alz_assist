package com.example.alz_assist.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alz_assist.R;
import com.example.alz_assist.adapters.ReminderAdapter;
import java.util.ArrayList;
import java.util.List;

public class CareTakerDashboardActivity extends AppCompatActivity {
    private static final String TAG = "CareTakerDashboard";
    private TextView patientNameTextView;
    private RecyclerView remindersRecyclerView;
    private ReminderAdapter reminderAdapter;
    private List<String> remindersList;  // Store reminders

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_dashboard);

        try {
            // Initialize UI components
            patientNameTextView = findViewById(R.id.patientNameTextView);
            remindersRecyclerView = findViewById(R.id.remindersRecyclerView);

            if (patientNameTextView == null || remindersRecyclerView == null) {
                throw new NullPointerException("UI elements not found in layout!");
            }

            remindersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Initialize list
            remindersList = new ArrayList<>();
            loadDummyReminders();

            // Set up adapter with delete listener
            reminderAdapter = new ReminderAdapter(position -> {
                deleteReminder(position);
            });

            reminderAdapter.setDummyData(remindersList);
            remindersRecyclerView.setAdapter(reminderAdapter);

            // Load test data instead of fetching from the database
            loadPatientDetails();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void loadPatientDetails() {
        patientNameTextView.setText("Name: John Doe (Test Data)");
    }

    private void loadDummyReminders() {
        // Initialize list with test data
        ArrayList<String> dummyReminders = new ArrayList<>();
        dummyReminders.add("Take medicine at 8 AM");
        dummyReminders.add("Doctor appointment at 5 PM");

        reminderAdapter.setDummyData(dummyReminders);
    }

    private void deleteReminder(int position) {
        if (position >= 0 && position < remindersList.size()) {
            remindersList.remove(position);
            reminderAdapter.removeReminder(position);
            Toast.makeText(this, "Reminder deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
