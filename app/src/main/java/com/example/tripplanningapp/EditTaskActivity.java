package com.example.tripplanningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtCity, edtNote;
    private TextView txtDate;
    private RadioGroup radioType;
    private CheckBox chkPaid;
    private Switch switchReminder;
    private Button btnSaveChanges, btnDelete;

    private String selectedDate;
    private TripTask currentTask;
    private TaskStorageManager storageManager;
    private List<TripTask> allTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        String taskId = getIntent().getStringExtra("task_id");

        storageManager = new TaskStorageManager(this);
        allTasks = storageManager.loadTasks();

        for (TripTask t : allTasks) {
            if (t.getId().equals(taskId)) {
                currentTask = t;
                break;
            }
        }

        if (currentTask == null) {
            Toast.makeText(this, "Task not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        edtTitle = findViewById(R.id.edtTitle);
        edtCity = findViewById(R.id.edtCity);
        edtNote = findViewById(R.id.edtNote);
        txtDate = findViewById(R.id.txtDate);
        radioType = findViewById(R.id.radioType);
        chkPaid = findViewById(R.id.chkPaid);
        switchReminder = findViewById(R.id.switchReminder);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnDelete = findViewById(R.id.btnDelete);

        edtTitle.setText(currentTask.getTitle());
        edtCity.setText(currentTask.getCity());
        edtNote.setText(currentTask.getNote());
        txtDate.setText(currentTask.getDate());
        selectedDate = currentTask.getDate();

        if (currentTask.getType().equals("Sightseeing")) radioType.check(R.id.rbSight);
        else if (currentTask.getType().equals("Food")) radioType.check(R.id.rbFood);
        else if (currentTask.getType().equals("Shopping")) radioType.check(R.id.rbShop);
        else radioType.check(R.id.rbTransport);

        chkPaid.setChecked(currentTask.isPaid());
        switchReminder.setChecked(currentTask.isNeedReminder());

        txtDate.setOnClickListener(v -> showDatePicker());

        btnSaveChanges.setOnClickListener(v -> updateTask());

        btnDelete.setOnClickListener(v -> {
            storageManager.deleteTask(currentTask.getId());
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    txtDate.setText(selectedDate);
                },
                y, m, d
        );
        dp.show();
    }

    private void updateTask() {
        currentTask.setTitle(edtTitle.getText().toString());
        currentTask.setCity(edtCity.getText().toString());
        currentTask.setNote(edtNote.getText().toString());
        currentTask.setDate(selectedDate);

        int selectedId = radioType.getCheckedRadioButtonId();
        RadioButton rb = findViewById(selectedId);
        currentTask.setType(rb.getText().toString());

        currentTask.setPaid(chkPaid.isChecked());
        currentTask.setNeedReminder(switchReminder.isChecked());

        storageManager.updateTask(currentTask);

        Toast.makeText(this, "Changes saved!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
