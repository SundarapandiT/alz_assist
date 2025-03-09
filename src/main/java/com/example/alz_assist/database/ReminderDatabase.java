package com.example.alz_assist.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Reminder.class}, version = 2) // ✅ Updated version for migration
public abstract class ReminderDatabase extends RoomDatabase {
    private static volatile ReminderDatabase INSTANCE;

    public abstract ReminderDao reminderDao();

    public static ReminderDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ReminderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ReminderDatabase.class, "reminder_db")
                            .fallbackToDestructiveMigration() // ✅ Handles schema changes
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
