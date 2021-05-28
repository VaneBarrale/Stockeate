package com.stockeate.stockeate.clases;

public class class_producto {
    private String id, categoria, marca, presentacion, unidad;

    public class_producto(){}

    public class_producto(String Id, String categoria, String marca, String presentacion, String unidad) {
        this.id = id;
        this.categoria = categoria;
        this.marca = marca;
        this.presentacion = presentacion;
        this.unidad = unidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    @Override
    public String toString() {
        return categoria  + ' ' + marca + ' ' + presentacion + unidad; }
}
