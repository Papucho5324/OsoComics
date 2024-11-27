package com.example.papeleriaosocomics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private final List<Producto> productoList;

    public ProductoAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_articulo, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.tvNombre.setText(producto.getName());
        holder.tvPrecio.setText("Precio: $" + producto.getPrecio());
        holder.tvCantidad.setText("Cantidad: " + producto.getCantidad());
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvCantidad;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
        }
    }
}
