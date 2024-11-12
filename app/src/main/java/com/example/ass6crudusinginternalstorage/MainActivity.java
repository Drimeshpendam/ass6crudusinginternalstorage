package com.example.ass6crudusinginternalstorage;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "user_data.txt";  // File name for internal storage
    private EditText inputEditText;
    private TextView contentTextView;
    private Button createButton, readButton, updateButton, deleteButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        inputEditText = findViewById(R.id.inputEditText);
        contentTextView = findViewById(R.id.contentTextView);
        createButton = findViewById(R.id.createButton);
        readButton = findViewById(R.id.readButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Set up listeners for each button
        createButton.setOnClickListener(v -> createFile());
        readButton.setOnClickListener(v -> readFile());
        updateButton.setOnClickListener(v -> updateFile());
        deleteButton.setOnClickListener(v -> deleteFile());
    }

    // Create a file and write some initial content to it
    private void createFile() {
        String content = inputEditText.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "Please enter some content", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE)) {
            fos.write(content.getBytes());
            Toast.makeText(this, "File created successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error creating file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Read the content of the file and display it in the TextView
    private void readFile() {
        try (FileInputStream fis = openFileInput(FILENAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String content = new String(buffer);
            contentTextView.setText(content);
        } catch (IOException e) {
            Toast.makeText(this, "Error reading file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Update the content of the file
    private void updateFile() {
        String newContent = inputEditText.getText().toString();
        if (newContent.isEmpty()) {
            Toast.makeText(this, "Please enter new content", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE)) {
            fos.write(newContent.getBytes());
            Toast.makeText(this, "File updated successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error updating file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Delete the file from internal storage
    private void deleteFile() {
        boolean isDeleted = deleteFile(FILENAME);
        if (isDeleted) {
            contentTextView.setText("");  // Clear the displayed content
            Toast.makeText(this, "File deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error deleting file", Toast.LENGTH_LONG).show();
        }
    }
}
