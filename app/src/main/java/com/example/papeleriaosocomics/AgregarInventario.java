package com.example.papeleriaosocomics;

import android.os.Bundle;
import android.widget.Button;
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

public class AgregarInventario extends AppCompatActivity {

    TextInputEditText inventarioName, inventarioPrecio, inventarioCantidad, inventarioCategoria;
    Button saveProduct, cancelar;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_inventario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        saveProduct = findViewById(R.id.saveProduct);
        cancelar = findViewById(R.id.cancelar);
        cancelar.setOnClickListener(view -> {
            finish();
        });


        mFirestore = FirebaseFirestore.getInstance();
        inventarioName = findViewById(R.id.inventarioName);
        inventarioPrecio = findViewById(R.id.inventarioPrecio);
        inventarioCantidad = findViewById(R.id.inventarioCantidad);
        inventarioCategoria = findViewById(R.id.inventarioCategoria);

        saveProduct.setOnClickListener(view -> {
            String name = inventarioName.getText().toString();
            int precio = Integer.parseInt(inventarioPrecio.getText().toString());
            int cantidad = Integer.parseInt(inventarioCantidad.getText().toString());
            String categoria = inventarioCategoria.getText().toString();

            if (!name.isEmpty() && !categoria.isEmpty()) {
                agregarArticulo(name, precio, cantidad, categoria);
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            }

        });

    }
        public void cleanFields() {
            inventarioName.setText("");
            inventarioPrecio.setText("");
            inventarioCantidad.setText("");
            inventarioCategoria.setText("");
            inventarioName.requestFocus();
        }

    private void agregarArticulo(final String name, final int precio, final int cantidad, final String categoria) {
        Map<String, Object> articulo = new HashMap<>();
        articulo.put("name", name);
        articulo.put("precio", precio);
        articulo.put("cantidad", cantidad);
        articulo.put("categoria", categoria);

        mFirestore.collection("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Productos").add(articulo).addOnSuccessListener(documentReference -> {
            Toast.makeText(AgregarInventario.this, "Articulo agregado correctamente", Toast.LENGTH_SHORT).show();
            cleanFields();


        });
    }
}