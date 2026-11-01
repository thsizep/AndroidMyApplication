package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecordStorage {
    private static final String FILE = "records";
    private static final String KEY = "data";

    public static void add(Context context, Record record) throws JSONException {
        List<Record> records = getAll(context);
        records.add(record);
        saveAll(context, records);
    }

    public static List<Record> getAll(Context context) throws JSONException {
        SharedPreferences preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        JSONArray data = new JSONArray(preferences.getString(KEY, "[]"));
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            records.add(Record.fromJson(object));
        }
        return records;
    }

    public static void saveAll(Context context, List<Record> records) throws JSONException {
        JSONArray data = new JSONArray();
        for (Record record : records) {
            data.put(record.toJson());
        }
        SharedPreferences preferences = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        preferences.edit().putString(KEY, data.toString()).apply();
    }
}
