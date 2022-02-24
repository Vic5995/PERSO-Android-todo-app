package com.example.todoapp.util;

import androidx.room.TypeConverter;

import com.example.todoapp.model.Priority;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long val) {
        return val == null ? null : new Date(val);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromPriority(Priority priority) {
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority toPriority(String priority) {
        return priority == null ? null : Priority.valueOf(priority);
    }
}
