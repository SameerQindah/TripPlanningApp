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
    private List<TripTask> allTasks;   // كل التاسكات الأصلية (قبل الفلترة)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ربط الواجهات
        recyclerTasks = findViewById(R.id.recyclerTasks);
        searchView = findViewById(R.id.searchView);
        btnAddTask = findViewById(R.id.btnAddTask);

        // مدير التخزين
        storageManager = new TaskStorageManager(this);

        // تحميل البيانات من الشيرد بريفرنس
        allTasks = storageManager.loadTasks();

        // (اختياري) لو أول مرة فاضية، ممكن نحط مثال تجريبي
        if (allTasks.isEmpty()) {
            // مثال واحد عشان تشوف شكل الليست
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

        // إعداد الريسايكلرفيو
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TripTaskAdapter(this, allTasks, this);
        recyclerTasks.setAdapter(adapter);

        // البحث
        setupSearch();

        // زر الإضافة (لاحقًا رح نفتح AddTaskActivity)
        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // لما نرجع من شاشة تانية (إضافة/تعديل) نعيد تحميل البيانات
        allTasks = storageManager.loadTasks();
        adapter.updateList(allTasks);
    }

    // إعداد البحث
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

    // فلترة الليست حسب الاسم أو المدينة
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

    // لما نضغط على عنصر (لاحقًا رح نفتح EditTaskActivity)
    @Override
    public void onItemClick(TripTask task) {
        Intent i = new Intent(MainActivity.this, EditTaskActivity.class);
        i.putExtra("task_id", task.getId());
        startActivity(i);
    }


    // ضغط مطوّل (ممكن نستخدمها للحذف بعدين)
    @Override
    public void onItemLongClick(TripTask task) {
        Toast.makeText(this, "Long click on: " + task.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
