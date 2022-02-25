package com.example.todoapp.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.model.Task;
import com.example.todoapp.util.Utils;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Task> taskList;
    private final OnTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Task> taskList, OnTodoClickListener todoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = todoClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        holder.taskName.setText(task.getTask());
        holder.dueDateTextView.setText(formatted);
        if (task.isDone) {
            holder.radioButton.setChecked(true);
            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.dueDateTextView.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatCheckBox radioButton;
        public AppCompatTextView taskName;
        public AppCompatTextView dueDateTextView;
        public AppCompatImageButton deleteButton;

        OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.todo_radio_button);
            radioButton.setOnClickListener(this);
            taskName = itemView.findViewById(R.id.view_holder_task_name);
            dueDateTextView = itemView.findViewById(R.id.view_holder_task_due_date);
            deleteButton = itemView.findViewById(R.id.view_holder_delete_btn);
            deleteButton.setOnClickListener(this);

            this.onTodoClickListener = todoClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Task currTask = taskList.get(getAdapterPosition());
            if (id == R.id.todo_row_layout) {
                todoClickListener.onTodoClick(currTask);
            } else if (id == R.id.todo_radio_button) {
                todoClickListener.onTodoRadioButtonClick(currTask);
            } else if (id == R.id.view_holder_delete_btn) {
                todoClickListener.onDeleteTodoClick(currTask);
            }
        }


    }
}
