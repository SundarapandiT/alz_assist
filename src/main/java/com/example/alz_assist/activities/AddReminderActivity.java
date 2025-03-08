package com.example.alz_assist.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alz_assist.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    private EditText medicineNameEditText, dosageEditText, messageEditText;
    private Spinner medicineTypeSpinner;
    private ChipGroup dayChipGroup;
    private TimePicker timePicker;
    private Button saveReminderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        medicineNameEditText = findViewById(R.id.medicineNameEditText);
        dosageEditText = findViewById(R.id.dosageEditText);
        messageEditText = findViewById(R.id.messageEditText);
        medicineTypeSpinner = findViewById(R.id.medicineTypeSpinner);
        dayChipGroup = findViewById(R.id.dayChipGroup);
        timePicker = findViewById(R.id.timePicker);
        saveReminderButton = findViewById(R.id.scheduleButton);

        saveReminderButton.setOnClickListener(v -> scheduleReminder());
    }

    private void scheduleReminder() {
        String medicineName = medicineNameEditText.getText().toString().trim();
        String dosage = dosageEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();
        String medicineType = medicineTypeSpinner.getSelectedItem().toString();

        if (medicineName.isEmpty() || dosage.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Integer> selectedDays = new ArrayList<>();
        for (int i = 0; i < dayChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) dayChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedDays.add(i); // 0 = Sunday, 1 = Monday, etc.
            }
        }

        if (selectedDays.isEmpty()) {
            Toast.makeText(this, "Please select at least one day", Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Save reminder in SharedPreferences
        saveReminder(medicineName, dosage, medicineType, message, hour, minute, selectedDays);

        // Schedule alarms for selected days
        for (int day : selectedDays) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.DAY_OF_WEEK, day + 1);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
            }

            setAlarm(calendar, medicineName, dosage, medicineType, message);
        }

        // Prepare data to send back to the parent activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("medicineName", medicineName);
        resultIntent.putExtra("dosage", dosage);
        resultIntent.putExtra("medicineType", medicineType);
        resultIntent.putExtra("message", message);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Reminder set successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void saveReminder(String medicineName, String dosage, String medicineType,
                              String message, int hour, int minute, ArrayList<Integer> days) {
        SharedPreferences prefs = getSharedPreferences("Reminders", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Use the medicine name and current time to ensure the key is unique
        String reminderKey = medicineName + "_" + System.currentTimeMillis();
        String reminderValue = medicineName + "," + dosage + "," + medicineType + "," + message + "," + hour + "," + minute + "," + convertDaysToString(days);

        // Save the reminder in SharedPreferences
        editor.putString(reminderKey, reminderValue);
        editor.apply();
    }

    private String convertDaysToString(ArrayList<Integer> days) {
        StringBuilder daysString = new StringBuilder();
        for (int day : days) {
            daysString.append(getDayString(day)).append(", ");
        }
        if (daysString.length() > 0) {
            daysString.setLength(daysString.length() - 2); // Remove last comma and space
        }
        return daysString.toString();
    }

    private String getDayString(int day) {
        switch (day) {
            case 0: return "Sunday";
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            default: return "";
        }
    }

    private void setAlarm(Calendar calendar, String medicineName, String dosage, String medicineType, String message) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderAlarmReceiver.class);
        intent.putExtra("medicineName", medicineName);
        intent.putExtra("dosage", dosage);
        intent.putExtra("medicineType", medicineType);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
