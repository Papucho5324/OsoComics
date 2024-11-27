package com.example.papeleriaosocomics;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Carrito extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carrito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar BottomNavigationView después de setContentView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Manejo de la selección de elementos
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent = null;

            if (itemId == R.id.item_1) {
                intent = new Intent(this, Inventario.class);
            } else if (itemId == R.id.item_2) {
                intent = new Intent(this, Home.class);
            } else if (itemId == R.id.item_3) {
                // Evitar iniciar la misma actividad
            } else {
                return false;
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }
}