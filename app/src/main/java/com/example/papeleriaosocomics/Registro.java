package com.example.papeleriaosocomics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
    TextView iniciarSesion;
    TextInputEditText email, password, passwordConfirm, mNombre;
    private FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    CheckBox mTerminos;
    Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iniciarSesion = findViewById(R.id.iniciarSesion);
        iniciarSesion.setOnClickListener(v -> {
            finish();
        });

        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        mNombre = findViewById(R.id.registerNombre);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mTerminos = findViewById(R.id.terminos);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });



    }

    private boolean validarCorreo(String email) {
        String patron = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.compile(patron, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    private void registrarUsuario() {
        String nombre = mNombre.getText().toString();
        String correo = email.getText().toString().trim().toLowerCase();
        String contrasena = password.getText().toString();
        String confirmacion = passwordConfirm.getText().toString();

        if (camposCompletos(nombre, correo, contrasena, confirmacion)) {
            if (!validarCorreo(correo)) {
                mostrarMensaje("Correo no válido");
                return;
            }

            if (!contrasena.equals(confirmacion)) {
                mostrarMensaje("Contraseñas no coinciden");
                return;
            }

            if (contrasena.length() < 6) {
                mostrarMensaje("Contraseña debe tener al menos 6 caracteres");
                return;
            }

            if (!mTerminos.isChecked()) {
                mostrarMensaje("Debe aceptar los términos");
                return;
            }

            crearUsuario(nombre, correo, contrasena);
        } else {
            mostrarMensaje("Complete todos los campos");
        }
    }

    private boolean camposCompletos(String... campos) {
        for (String campo : campos) {
            if (campo.isEmpty()) return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void crearUsuario(String nombre, String correo, String contrasena) {
        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                guardarDatosUsuario(mAuth.getCurrentUser().getUid(), nombre, correo);
            } else {
                mostrarMensaje("Error al crear el usuario");
            }
        });
    }

    private void guardarDatosUsuario(String userId, String nombre, String correo) {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("correo", correo);

        mFirestore.collection("Usuarios").document(userId).set(usuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mostrarMensaje("Registro exitoso");
                finish();
            } else {
                mostrarMensaje("Error al guardar los datos");
            }
        });
    }

}