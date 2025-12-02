package com.example.tripplanningapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskStorageManager {

    private static final String PREF_NAME = "trip_tasks_prefs";   
    private static final String KEY_TASK_LIST = "task_list";      

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public TaskStorageManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<TripTask> loadTasks() {
        String json = sharedPreferences.getString(KEY_TASK_LIST, "");

        if (json == null || json.isEmpty()) {
            return new ArrayList<>(); 
        }

        Type listType = new TypeToken<List<TripTask>>() {}.getType();
        List<TripTask> tasks = gson.fromJson(json, listType);

        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        return tasks;
    }

    public void saveTasks(List<TripTask> tasks) {
        String json = gson.toJson(tasks);
        sharedPreferences.edit()
                .putString(KEY_TASK_LIST, json)
                .apply();
    }

    public void addTask(TripTask task) {
        List<TripTask> current = loadTasks();
        current.add(task);
        saveTasks(current);
    }

    public void updateTask(TripTask updatedTask) {
        List<TripTask> current = loadTasks();

        for (int i = 0; i < current.size(); i++) {
            TripTask t = current.get(i);
            if (t.getId().equals(updatedTask.getId())) {
                current.set(i, updatedTask); 
                break;
            }
        }

        saveTasks(current);
    }

    public void deleteTask(String taskId) {
        List<TripTask> current = loadTasks();
        List<TripTask> newList = new ArrayList<>();

        for (TripTask t : current) {
            if (!t.getId().equals(taskId)) {
                newList.add(t); 
            }
        }

        saveTasks(newList);
    }
}
