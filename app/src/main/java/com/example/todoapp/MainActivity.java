package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.todoapp.adapter.OnTodoClickListener;
import com.example.todoapp.adapter.RecyclerViewAdapter;
import com.example.todoapp.model.ShareViewModel;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskViewModel;
import com.example.todoapp.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ShareViewModel shareViewModel;
    private TextView mainDateTitle;
    private TextView numberOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainDateTitle = findViewById(R.id.main_title_date);
        numberOfTasks = findViewById(R.id.main_number_task);

        mainDateTitle.setText(Utils.formatDateForTitle(new Date()));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        shareViewModel = new ViewModelProvider(this).get(ShareViewModel.class);

        taskViewModel.getAllTasks().observe(this, tasks -> {
            recyclerViewAdapter = new RecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(recyclerViewAdapter);
            numberOfTasks.setText(String.format(getString(R.string.main_number_tasks_text), tasks.size()));
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
        });
    }


    @Override
    public void onTodoClick(Task task) {
        shareViewModel.selectItem(task);
        shareViewModel.setEdit(true);
        // TODO: update task
    }

    @Override
    public void onTodoRadioButtonClick(Task task) {
        Log.d("VH LISTENER", "onTodoRadioButtonClick: update task " + task.getTaskId());
        task.setDone(!task.isDone());
        TaskViewModel.update(task);
    }

    @Override
    public void onDeleteTodoClick(Task task) {
        Log.d("VH LISTENER", "onDeleteTodoClick: delete task " + task.getTaskId());
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }


}