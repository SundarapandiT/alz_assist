package com.example.alz_assist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alz_assist.R;
import com.example.alz_assist.adapters.ReminderAdapter;

public class PatientDashboardActivity extends AppCompatActivity {
    private static final String TAG = "PatientDashboard";
    private Button addReminderButton, emergencyContactsButton, photoGalleryButton;
    private RecyclerView remindersRecyclerView;
    private ReminderAdapter reminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        try {
            // Initialize UI components
            addReminderButton = findViewById(R.id.addReminderButton);
            emergencyContactsButton = findViewById(R.id.emergencyContactsButton);
            photoGalleryButton = findViewById(R.id.photoGalleryButton);
            remindersRecyclerView = findViewById(R.id.remindersRecyclerView);

            // Setup RecyclerView
            setupRecyclerView();

            // Button click listeners
            addReminderButton.setOnClickListener(v -> openAddReminderActivity());
            emergencyContactsButton.setOnClickListener(v -> openEmergencyContactsActivity());
            photoGalleryButton.setOnClickListener(v -> openPhotoGalleryActivity());

            Log.d(TAG, "PatientDashboardActivity started successfully!");

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerView() {
        remindersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reminderAdapter = new ReminderAdapter(position -> deleteReminder(position));
        remindersRecyclerView.setAdapter(reminderAdapter);
    }

    private void deleteReminder(int position) {
        if (position >= 0 && position < reminderAdapter.getItemCount()) {
            reminderAdapter.removeReminder(position);
            Toast.makeText(this, "Reminder deleted", Toast.LENGTH_SHORT).show();
        }
    }

    private void openAddReminderActivity() {
        startActivity(new Intent(this, AddReminderActivity.class));
    }

    private void openEmergencyContactsActivity() {
        startActivity(new Intent(this, EmergencyContactsActivity.class));
    }

    private void openPhotoGalleryActivity() {
        startActivity(new Intent(this, PhotoGalleryActivity.class));
    }
}
