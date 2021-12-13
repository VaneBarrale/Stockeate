package com.stockeate.stockeate.clases;

public class class_producto {
    private String id, categoria, marca, presentacion, unidad, codigo_barra, comercio, precio;

    public class_producto(){}

    public class_producto(String Id, String categoria, String marca, String presentacion, String unidad, String codigo_barra, String comercio, String precio) {
        this.id = id;
        this.categoria = categoria;
        this.marca = marca;
        this.presentacion = presentacion;
        this.unidad = unidad;
        this.codigo_barra = codigo_barra;
        this.precio = precio;
        this.comercio = comercio;
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

    public String getCodigo_barra() {return codigo_barra; }

    public void setCodigo_barra(String codigo_barra) { this.codigo_barra = codigo_barra; }

    public String getPrecio() {return precio; }

    public void setPrecio(String precio) { this.precio = precio; }

    public String getComercio() {return comercio; }

    public void setComercio(String comercio) { this.comercio = comercio; }

    @Override
    public String toString() {
        return categoria  + ' ' + marca + ' ' + presentacion + ' ' + codigo_barra; }
}
