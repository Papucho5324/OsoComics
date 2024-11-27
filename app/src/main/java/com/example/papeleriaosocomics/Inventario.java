package com.example.papeleriaosocomics;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Inventario extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUserId = auth.getCurrentUser().getUid();
    TextView mAgregarArticulo, MModificarArticulo, mEliminarArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventario);
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
                // Evitar iniciar la misma actividad
            } else if (itemId == R.id.item_2) {
                intent = new Intent(this, Home.class);

            } else if (itemId == R.id.item_3) {
                intent = new Intent(this, Carrito.class);
            } else {
                return false;
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });

        mAgregarArticulo = findViewById(R.id.agregarArticulo);
        mAgregarArticulo.setOnClickListener(view -> {
            Intent intent = new Intent(this, AgregarInventario.class);
            startActivity(intent);
        });

        mEliminarArticulo = findViewById(R.id.eliminarArticulo);
        mEliminarArticulo.setOnClickListener(view -> {
            eliminarTodosLosProductos(currentUserId);
        });



    }
    private void eliminarTodosLosProductos(String currentUserId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Accede a la subcolección "Productos" del usuario actual
        db.collection("Usuarios")
                .document(currentUserId)
                .collection("Productos")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        // Elimina cada documento dentro de la subcolección "Productos"
                        db.collection("Usuarios")
                                .document(currentUserId)
                                .collection("Productos")
                                .document(document.getId())
                                .delete();
                    }
                    Toast.makeText(this, "Productos eliminados para el usuario actual", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al eliminar productos", Toast.LENGTH_SHORT).show();
                });
    }
}