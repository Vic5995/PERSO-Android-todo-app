package com.example.todoapp.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.todoapp.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String  formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        return simpleDateFormat.format(date);
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int priorityColor(Task task) {
        switch (task.getPriority()) {
            case MEDIUM: return Color.argb(200, 155, 179, 0);
            case HIGH: return Color.argb(200, 201, 21, 23);
            default: return Color.argb(200, 51, 181, 129);
        }
    }
}
