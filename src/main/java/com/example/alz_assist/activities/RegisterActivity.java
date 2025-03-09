package com.example.alz_assist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alz_assist.R;

import com.example.alz_assist.activities.CareTakerDashboardActivity;
import com.example.alz_assist.activities.LoginActivity;
import com.example.alz_assist.activities.PatientDashboardActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etConfirmPassword;
    private Spinner spinnerUserRole;
    private Button btnRegister;
    private TextView tvLogin;
    private String selectedRole = "Patient"; // Default role

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        spinnerUserRole = findViewById(R.id.spinnerUserRole);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Set up the user role spinner
        String[] roles = getResources().getStringArray(R.array.user_roles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, roles);
        spinnerUserRole.setAdapter(adapter);

        // Handle spinner selection
        spinnerUserRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRole = roles[position]; // Save selected role
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedRole = "Patient"; // Default role
            }
        });

        // Handle register button click
        btnRegister.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fake registration logic (Replace with Firebase or database logic)
            Toast.makeText(RegisterActivity.this, "Registration Successful as " + selectedRole, Toast.LENGTH_SHORT).show();

            // Navigate based on user role
            if (selectedRole.equals("Care-Taker")) {
                startActivity(new Intent(RegisterActivity.this, CareTakerDashboardActivity.class));
            } else {
                startActivity(new Intent(RegisterActivity.this, PatientDashboardActivity.class));
            }

            finish(); // Close RegisterActivity
        });

        // Navigate to LoginActivity
        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
}
