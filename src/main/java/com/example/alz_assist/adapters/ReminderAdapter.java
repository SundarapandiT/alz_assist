package com.example.alz_assist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alz_assist.R;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<String> reminders = new ArrayList<>();
    private final OnReminderDeleteListener deleteListener;

    // Interface for handling delete clicks
    public interface OnReminderDeleteListener {
        void onDelete(int position);
    }

    public ReminderAdapter(OnReminderDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setDummyData(List<String> dummyReminders) {
        this.reminders = new ArrayList<>(dummyReminders); // Avoid modifying original list
        notifyDataSetChanged();
    }

    public void removeReminder(int position) {
        if (position >= 0 && position < reminders.size()) {
            reminders.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, reminders.size()); // Update positions
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reminderText.setText(reminders.get(position));

        // Set delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            deleteListener.onDelete(holder.getAdapterPosition()); // Call the delete function
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reminderText;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            reminderText = itemView.findViewById(R.id.reminderText);
            deleteButton = itemView.findViewById(R.id.deleteReminderButton);
        }
    }
}
