package com.stockeate.stockeate.clases;

public class class_producto {
    private String id, id_categoria, codigo_barra, marca, id_presentacion, id_unidad, precio_unitario;

    public class_producto(){}

    public class_producto(String id, String id_categoria, String codigo_barra, String marca, String id_presentacion, String id_unidad, String precio_unitario) {
        this.id = id;
        this.id_categoria = id_categoria;
        this.codigo_barra = codigo_barra;
        this.marca = marca;
        this.id_presentacion = id_presentacion;
        this.id_unidad = id_unidad;
        this.precio_unitario = precio_unitario;
    }

    public <T> class_producto(T value) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategoria() {
        return id_categoria;
    }

    public void setIdCategoria(String id_categoria) {
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

    public String getIdPresentacion() {
        return id_presentacion;
    }

    public void setIdPresentacion(String id_presentacion) {
        this.id_presentacion = id_presentacion;
    }

    public String getIdUnidad() {
        return id_unidad;
    }

    public void setIdUnidad(String unidad) {
        this.id_unidad = id_unidad;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    @Override
    public String toString() {
        return id_categoria  + ' ' + marca + ' ' + id_presentacion; }
}
