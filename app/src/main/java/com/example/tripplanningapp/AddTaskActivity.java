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
import android.widget.ImageButton;


import java.util.Calendar;
import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtTitle, edtCity, edtNote;
    private TextView txtDate;
    private RadioGroup radioType;
    private CheckBox chkPaid;
    private Switch switchReminder;
    private Button btnSave;

    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtTitle = findViewById(R.id.edtTitle);
        edtCity = findViewById(R.id.edtCity);
        edtNote = findViewById(R.id.edtNote);
        txtDate = findViewById(R.id.txtDate);
        radioType = findViewById(R.id.radioType);
        chkPaid = findViewById(R.id.chkPaid);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);

        txtDate.setOnClickListener(v -> showDatePicker());

        btnSave.setOnClickListener(v -> saveTask());

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

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

    private void saveTask() {
        String title = edtTitle.getText().toString().trim();
        String city = edtCity.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        if (title.isEmpty() || city.isEmpty() || selectedDate.isEmpty()) {
            Toast.makeText(this, "Please fill title, city, and date", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = radioType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Select task type", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rb = findViewById(selectedId);
        String type = rb.getText().toString();

        TripTask task = new TripTask(
                UUID.randomUUID().toString(),
                title,
                city,
                selectedDate,
                type,
                chkPaid.isChecked(),
                switchReminder.isChecked(),
                note
        );

        TaskStorageManager manager = new TaskStorageManager(this);
        manager.addTask(task);

        Toast.makeText(this, "Task added!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
