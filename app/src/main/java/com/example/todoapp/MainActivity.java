package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    public final String TAG = "MAIN_ACTIVITY";
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    BottomSheetFragment bottomSheetFragment;
    private ShareViewModel shareViewModel;
    private TextView mainDateTitle;
    private TextView numberOfTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

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
            showBottomSheetFragment();
        });
    }

    private void showBottomSheetFragment(){
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onTodoClick(Task task) {
        shareViewModel.selectItem(task);
        shareViewModel.setEdit(true);
        showBottomSheetFragment();
    }

    @Override
    public void onTodoRadioButtonClick(Task task) {
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}