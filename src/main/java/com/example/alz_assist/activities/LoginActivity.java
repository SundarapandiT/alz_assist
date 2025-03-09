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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alz_assist.R;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Spinner spinnerUserRole;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPassword;
    private String selectedRole = "Patient"; // Default role

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spinnerUserRole = findViewById(R.id.spinnerUserRole);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

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
                // Default to Patient
                selectedRole = "Patient";
            }
        });

        // Handle login button click
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fake authentication (Replace this with Firebase or database validation)
            if (email.equals("test@alzassist.com") && password.equals("password123")) {
                Toast.makeText(LoginActivity.this, "Login Successful as " + selectedRole, Toast.LENGTH_SHORT).show();

                // Navigate based on user role
                if (selectedRole.equals("Care-Taker")) {
                    startActivity(new Intent(LoginActivity.this, CareTakerDashboardActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
                }
                finish(); // Close LoginActivity
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to Register Activity
        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Handle Forgot Password click
        tvForgotPassword.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Reset password feature coming soon!", Toast.LENGTH_SHORT).show();
        });
    }
}
