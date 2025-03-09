package com.example.alz_assist.database;

import androidx.room.TypeConverter;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public static String fromList(List<String> list) {
        if (list == null) return "[]"; // ✅ Prevents null errors
        return new JSONArray(list).toString();
    }

    @TypeConverter
    public static List<String> toList(String value) {
        List<String> list = new ArrayList<>();
        if (value == null || value.isEmpty()) return list; // ✅ Prevents crashes
        try {
            JSONArray jsonArray = new JSONArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
