package com.example.alz_assist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alz_assist.R;

public class PatientDashboardActivity extends AppCompatActivity {

    private Button addReminderButton, emergencyContactsButton, photoGalleryButton;
    private TextView remindersTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        // Initialize UI elements
        addReminderButton = findViewById(R.id.addReminderButton);
        emergencyContactsButton = findViewById(R.id.emergencyContactsButton);
        photoGalleryButton = findViewById(R.id.photoGalleryButton);
        remindersTextView = findViewById(R.id.remindersTextView);

        // Display reminders from SharedPreferences
        displayReminders();

        // Set onClick listeners
        addReminderButton.setOnClickListener(v -> openAddReminderActivity());
        emergencyContactsButton.setOnClickListener(v -> openEmergencyContactsActivity());
        photoGalleryButton.setOnClickListener(v -> openPhotoGalleryActivity());
    }

    private void displayReminders() {
        SharedPreferences prefs = getSharedPreferences("Reminders", MODE_PRIVATE);
        StringBuilder reminderText = new StringBuilder("Reminders:\n");

        // Loop through the reminders stored in SharedPreferences and display them
        for (String key : prefs.getAll().keySet()) {
            String reminder = prefs.getString(key, "");
            String[] reminderData = reminder.split(",");
            if (reminderData.length >= 6) {
                String medicineName = reminderData[0];
                String dosage = reminderData[1];
                String medicineType = reminderData[2];
                String message = reminderData[3];
                String time = reminderData[4] + ":" + reminderData[5];
                String days = reminderData[6]; // The days will be a list of integers in a string format

                // Format the reminder string to display it more clearly
                reminderText.append("Medicine: ").append(medicineName).append("\n")
                        .append("Dosage: ").append(dosage).append("\n")
                        .append("Type: ").append(medicineType).append("\n")
                        .append("Message: ").append(message).append("\n")
                        .append("Time: ").append(time).append("\n")
                        .append("Days: ").append(days).append("\n\n");
            }
        }

        // If no reminders are stored, show a default message
        if (reminderText.toString().equals("Reminders:\n")) {
            remindersTextView.setText("No reminders set");
        } else {
            remindersTextView.setText(reminderText.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the user has added a reminder, update the displayed reminders
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // After returning from AddReminderActivity, refresh the reminders list
            displayReminders();
        }
    }

    private void openAddReminderActivity() {
        // Start the activity to add a new reminder
        Intent intent = new Intent(PatientDashboardActivity.this, AddReminderActivity.class);
        startActivityForResult(intent, 1); // Start AddReminderActivity for result
    }

    private void openEmergencyContactsActivity() {
        // Start the emergency contacts activity
        Intent intent = new Intent(PatientDashboardActivity.this, EmergencyContactsActivity.class);
        startActivity(intent);
    }

    private void openPhotoGalleryActivity() {
        // Start the photo gallery activity
        Intent intent = new Intent(PatientDashboardActivity.this, PhotoGalleryActivity.class);
        startActivity(intent);
    }
}
