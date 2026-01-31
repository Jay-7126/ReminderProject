package com.example.reminderapplicatiom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskItem> taskList;
    private TaskAdapter adapter;
    private EditText editText;

    private static final String PREFS_NAME = "todo_prefs";
    private static final String KEY_TASKS = "tasks_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        editText = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonLogout = findViewById(R.id.buttonLogout); // This was causing the error
        Button buttonClearAll = findViewById(R.id.buttonClearAll);
        ListView listView = findViewById(R.id.listViewTasks);

        loadData();

        adapter = new TaskAdapter(this, taskList);
        listView.setAdapter(adapter);

        // Add Task
        buttonAdd.setOnClickListener(v -> {
            String taskName = editText.getText().toString().trim();
            if (!taskName.isEmpty()) {
                taskList.add(new TaskItem(taskName, false, 0));
                adapter.notifyDataSetChanged();
                editText.setText("");
                saveData();
            }
        });

        // Logout
        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Clear All
        buttonClearAll.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear All")
                    .setMessage("Delete everything?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        taskList.clear();
                        adapter.notifyDataSetChanged();
                        saveData();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Long click to delete single
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            taskList.remove(position);
            adapter.notifyDataSetChanged();
            saveData();
            return true;
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString(KEY_TASKS, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_TASKS, null);
        Type type = new TypeToken<ArrayList<TaskItem>>() {}.getType();
        taskList = gson.fromJson(json, type);
        if (taskList == null) taskList = new ArrayList<>();
    }
}