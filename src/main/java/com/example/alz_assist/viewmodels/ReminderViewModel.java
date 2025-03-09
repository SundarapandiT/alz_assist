package com.example.alz_assist.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.alz_assist.database.Reminder;
import com.example.alz_assist.database.ReminderDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderViewModel extends AndroidViewModel {
    private ReminderDatabase database;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReminderViewModel(Application application) {
        super(application);
        database = ReminderDatabase.getInstance(application);
    }

    public LiveData<List<Reminder>> getAllReminders() {
        return database.reminderDao().getAllReminders();
    }

    public void insertReminder(Reminder reminder) {
        executor.execute(() -> database.reminderDao().insertReminder(reminder));
    }
}
