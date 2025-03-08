package com.example.alz_assist.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ReminderAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String medicineName = intent.getStringExtra("medicineName");
        String dosage = intent.getStringExtra("dosage");
        String medicineType = intent.getStringExtra("medicineType");
        String message = intent.getStringExtra("message");

        // Display a toast message when the reminder alarm goes off
        Toast.makeText(context, "Reminder: " + medicineName + " - " + dosage + "\n" + message, Toast.LENGTH_LONG).show();
    }
}
