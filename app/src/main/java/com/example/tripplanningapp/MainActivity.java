package com.example.tripplanningapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements TripTaskAdapter.OnItemClickListener {

    private RecyclerView recyclerTasks;
    private SearchView searchView;
    private FloatingActionButton btnAddTask;

    private TaskStorageManager storageManager;
    private TripTaskAdapter adapter;
    private List<TripTask> allTasks;   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerTasks = findViewById(R.id.recyclerTasks);
        searchView = findViewById(R.id.searchView);
        btnAddTask = findViewById(R.id.btnAddTask);

        storageManager = new TaskStorageManager(this);

        allTasks = storageManager.loadTasks();

        if (allTasks.isEmpty()) {
            TripTask sample = new TripTask(
                    "1",
                    "Visit Eiffel Tower",
                    "Paris",
                    "2025-12-01",
                    "Sightseeing",
                    false,
                    false,
                    "Sample task"
            );
            allTasks.add(sample);
            storageManager.saveTasks(allTasks);
        }

        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TripTaskAdapter(this, allTasks, this);
        recyclerTasks.setAdapter(adapter);

        setupSearch();

        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        allTasks = storageManager.loadTasks();
        adapter.updateList(allTasks);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTasks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTasks(newText);
                return true;
            }
        });
    }

    private void filterTasks(String query) {
        if (query == null || query.trim().isEmpty()) {
            adapter.updateList(allTasks);
            return;
        }

        String lower = query.toLowerCase().trim();
        List<TripTask> filtered = new ArrayList<>();

        for (TripTask t : allTasks) {
            if ((t.getTitle() != null && t.getTitle().toLowerCase().contains(lower)) ||
                    (t.getCity() != null && t.getCity().toLowerCase().contains(lower))) {
                filtered.add(t);
            }
        }

        adapter.updateList(filtered);
    }

    @Override
    public void onItemClick(TripTask task) {
        Intent i = new Intent(MainActivity.this, EditTaskActivity.class);
        i.putExtra("task_id", task.getId());
        startActivity(i);
    }


    @Override
    public void onItemLongClick(TripTask task) {
        Toast.makeText(this, "Long click on: " + task.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
