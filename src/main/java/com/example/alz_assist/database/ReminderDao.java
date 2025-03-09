package com.example.alz_assist.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {
    @Query("SELECT * FROM reminders")
    LiveData<List<Reminder>> getAllReminders(); // ✅ Now returns LiveData

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    LiveData<Reminder> getReminderById(int id); // ✅ Now returns LiveData

    @Insert
    void insertReminder(Reminder reminder); // Runs in background

    @Update
    void updateReminder(Reminder reminder);

    @Delete
    void deleteReminder(Reminder reminder);
}
