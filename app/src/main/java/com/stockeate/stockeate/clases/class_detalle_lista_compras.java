package com.stockeate.stockeate.clases;

import android.text.Editable;

public class class_detalle_lista_compras {

    private String id, id_lista_compras, id_producto, marca, presentacion, unidad, cantidad, categoria;
    private float precio;

    public class_detalle_lista_compras() {
    }

    public class_detalle_lista_compras(String id, String id_lista_compras, String id_producto, String marca, String presentacion, String unidad, String cantidad, String categoria, float precio) {
        this.id = id;
        this.id_lista_compras = id_lista_compras;
        this.id_producto = id_producto;
        this.categoria = categoria;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
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

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String toString() {
        return categoria  + ' ' + marca + ' ' + presentacion + ' ' + unidad + ' ' + cantidad; }

}
