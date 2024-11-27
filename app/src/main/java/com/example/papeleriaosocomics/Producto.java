package com.example.papeleriaosocomics;

public class Producto {
    private String name;
    private int precio;
    private int cantidad;

    // Constructor vacío para Firebase
    public Producto() {}

    public Producto(String name, int precio, int cantidad) {
        this.name = name;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getName() {
        return name;
    }

    public int getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
