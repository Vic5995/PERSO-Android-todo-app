package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.todoapp.databinding.ActivityAddTaskBinding;
import com.example.todoapp.model.Priority;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskViewModel;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;
    private Priority priority = Priority.HIGH;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        binding.returnBtn.setOnClickListener(view -> finish());

        binding.priorityChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.chipPriorityHigh.getId()) {
                priority = Priority.HIGH;
            } else if (checkedId == binding.chipPriorityMedium.getId()) {
                priority = Priority.MEDIUM;
            } else {
                priority = Priority.LOW;
            }
        });

        binding.dueDatePicker.setMinDate((new Date()).getTime());
        binding.dueDatePicker.setOnDateChangedListener((datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, monthOfYear, dayOfMonth);
            dueDate = calendar.getTime();
            Log.d("ADD TASK", "onCreate: " + dueDate);
        });

        binding.saveTaskButton.setOnClickListener(view -> {
            String task = binding.taskNameInput.getText().toString();
            if (!TextUtils.isEmpty(task)) {
                Task myTask = new Task(
                        task,
                        priority,
                        dueDate,
                        Calendar.getInstance().getTime(),
                        false
                );
                TaskViewModel.insert(myTask);
                finish();
            } else {
                Snackbar.make(binding.saveTaskButton, R.string.empty_field, Snackbar.LENGTH_LONG).show();
            }
        });

    }
}