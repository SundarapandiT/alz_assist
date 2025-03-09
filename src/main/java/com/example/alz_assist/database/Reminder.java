package com.example.alz_assist.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.List;

@Entity(tableName = "reminders")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String type;
    public String dosage;
    public String message;
    public String time;
    public boolean alarm;

    @TypeConverters(StringListConverter.class)
    public List<String> days; // Store selected days

    public Reminder(String name, String type, String dosage, String message, String time, boolean alarm, List<String> days) {
        this.name = name;
        this.type = type;
        this.dosage = dosage;
        this.message = message;
        this.time = time;
        this.alarm = alarm;
        this.days = days;
    }
}
