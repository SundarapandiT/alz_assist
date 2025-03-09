package com.example.alz_assist.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alz_assist.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.List;

public class AddReminderActivity extends AppCompatActivity {
    private EditText medicineNameEditText, dosageEditText, messageEditText;
    private Spinner medicineTypeSpinner;
    private ChipGroup dayChipGroup;
    private TimePicker timePicker;
    private Switch alarmSwitch;
    private Button scheduleButton;

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
        alarmSwitch = findViewById(R.id.alarmSwitch);
        scheduleButton = findViewById(R.id.scheduleButton);

        scheduleButton.setOnClickListener(v -> saveReminder());
    }

    private void saveReminder() {
        String name = medicineNameEditText.getText().toString().trim();
        String type = medicineTypeSpinner.getSelectedItem().toString();
        String dosage = dosageEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();
        boolean isAlarmOn = alarmSwitch.isChecked();
        int selectedHour = timePicker.getHour();
        int selectedMinute = timePicker.getMinute();
        String time = String.format("%02d:%02d", selectedHour, selectedMinute);
        List<String> selectedDays = new ArrayList<>();

        for (int i = 0; i < dayChipGroup.getChildCount(); i++) {
            Chip chip = (Chip) dayChipGroup.getChildAt(i);
            if (chip.isChecked()) {
                selectedDays.add(chip.getText().toString());
            }
        }

        if (name.isEmpty() || dosage.isEmpty() || message.isEmpty() || selectedDays.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select days", Toast.LENGTH_SHORT).show();
            return;
        }

        // Just show a toast message instead of saving to database
        Toast.makeText(this, "Reminder Details Saved (Simulation)", Toast.LENGTH_SHORT).show();

        // Simulating success response
        setResult(RESULT_OK);
        finish();
    }
}
