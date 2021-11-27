package com.stockeate.stockeate.clases;

public class class_categorizacion_precios {

    private String categoria, marca, presentacion, unidad, local, codigoBarra;
    private float precio_total;

    public class_categorizacion_precios() {
    }

    public class_categorizacion_precios(String categoria, String marca, String presentacion, String unidad, String local, String codigoBarra, float precio_total) {
        this.categoria = categoria;
        this.marca = marca;
        this.presentacion = presentacion;
        this.unidad = unidad;
        this.local = local;
        this.codigoBarra = codigoBarra;
        this.precio_total = precio_total;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
    }

    public String toString() {
        return "Categoria: " + categoria
                + "\nDatos producto : " + marca + " " + presentacion + " " + codigoBarra
                + "\nLocal : " + local
                + "\nPrecio total: " + precio_total;
    }
}
