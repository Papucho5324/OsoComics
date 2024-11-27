package com.example.papeleriaosocomics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    TextView producto1, producto2, producto3, producto4;
    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private List<Producto> productoList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializar BottomNavigationView después de setContentView
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Manejo de la selección de elementos
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Intent intent = null;

            if (itemId == R.id.item_1) {
                intent = new Intent(this, Inventario.class);
            } else if (itemId == R.id.item_2) {
                // Evitar iniciar la misma actividad
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

        // Configuración de padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productoAdapter = new ProductoAdapter(productoList);
        recyclerView.setAdapter(productoAdapter);

        db = FirebaseFirestore.getInstance();

        cargarProductos();

    }
    private void cargarProductos() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CollectionReference productosRef = db.collection("Usuarios").document(userId).collection("Productos");

        productosRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                List<Producto> productos = queryDocumentSnapshots.toObjects(Producto.class);
                productoList.addAll(productos);
                productoAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> {
            // Manejar errores
        });
    }

}