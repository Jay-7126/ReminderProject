package com.example.reminderapplicatiom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<TaskItem> {

    public TaskAdapter(Context context, List<TaskItem> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }

        TaskItem currentTask = getItem(position);
        TextView textView = convertView.findViewById(R.id.todoText);
        CheckBox checkBox = convertView.findViewById(R.id.todoCheckBox);

        textView.setText(currentTask.getTaskName());

        // Set Priority Colors
        int color;
        switch (currentTask.getPriority()) {
            case 2: color = Color.RED; break;
            case 1: color = Color.rgb(255, 165, 0); break;
            default: color = Color.parseColor("#4CAF50"); break;
        }
        textView.setTextColor(color);

        // Update Strike-through state
        if (currentTask.isDone()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setAlpha(0.5f);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            textView.setAlpha(1.0f);
        }

        // Handle Checkbox
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(currentTask.isDone());

        checkBox.setOnCheckedChangeListener((cb, isChecked) -> {
            currentTask.setDone(isChecked);
            if (getContext() instanceof MainActivity) {
                ((MainActivity) getContext()).saveData();
            }
            notifyDataSetChanged();
        });

        // Tap text to change priority
        textView.setOnClickListener(v -> {
            int nextPriority = (currentTask.getPriority() + 1) % 3;
            currentTask.setPriority(nextPriority);
            if (getContext() instanceof MainActivity) {
                ((MainActivity) getContext()).saveData();
            }
            notifyDataSetChanged();
        });

        return convertView;
    }
}