package com.example.papeleriaosocomics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegister;
    private FirebaseAuth mAuth;
    TextInputEditText mTextEmail, mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        mTextEmail = findViewById(R.id.loginEmail);
        mTextPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> handleAuthentication());
    }

    private void handleAuthentication() {
        String userEmail = mTextEmail.getText().toString().trim();
        String userPassword = mTextPassword.getText().toString().trim();

        if (userEmail.isEmpty()) {
            mTextEmail.setError("Correo electrónico requerido");
            mTextEmail.requestFocus();
            return;
        }

        if (userPassword.isEmpty()) {
            mTextPassword.setError("Contraseña requerida");
            mTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(MainActivity.this, Home.class));
            } else {
                Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
