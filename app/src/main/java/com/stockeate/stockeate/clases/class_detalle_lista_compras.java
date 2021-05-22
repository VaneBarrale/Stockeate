package com.stockeate.stockeate.clases;

import android.text.Editable;

public class class_detalle_lista_compras {

    private String id, id_lista_compras, id_producto, marca, presentacion;
    private int unidad, cantidad;
    private float precio;

    public class_detalle_lista_compras() {
    }

    public class_detalle_lista_compras(String id_lista_compras, String id_producto, String marca, String presentacion, int unidad, int cantidad, float precio) {
        this.id_lista_compras = id_lista_compras;
        this.id_producto = id_producto;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precio = precio;
        this.marca = marca;
        this.presentacion = presentacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_lista_compras() {
        return id_lista_compras;
    }

    public void setId_lista_compras(String id_lista_compras) {
        this.id_lista_compras = id_lista_compras;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
}
