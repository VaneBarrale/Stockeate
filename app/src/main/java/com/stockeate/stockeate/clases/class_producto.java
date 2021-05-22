package com.stockeate.stockeate.clases;

import android.widget.EditText;

import java.util.ArrayList;

public class class_producto {
    private String id, id_categoria, codigo_barra, marca, presentacion, unidad;
    private float precio_unitario;

    public class_producto(){}

    public class_producto(String id, String categoria, String codigo_barra, String marca, String presentacion, String unidad, float precio_unitario) {
        this.id = id;
        this.id_categoria = categoria;
        this.codigo_barra = codigo_barra;
        this.marca = marca;
        this.presentacion = presentacion;
        this.unidad = unidad;
        this.precio_unitario = precio_unitario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
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

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}
