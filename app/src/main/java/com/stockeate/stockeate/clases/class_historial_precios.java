package com.stockeate.stockeate.clases;

public class class_historial_precios {
    private String id, id_producto, id_comercio, categoria, marca, presentacion, unidad, codigo_barra, comercio;
    private double precio;

    public class_historial_precios(){};

    public class_historial_precios(String id, String id_producto, String id_comercio, String categoria, String marca, String presentacion, String unidad, String codigo_barra, String comercio, double precio) {
        this.id = id;
        this.id_producto = id_producto;
        this.id_comercio = id_comercio;
        this.categoria = categoria;
        this.marca = marca;
        this.presentacion = presentacion;
        this.unidad = unidad;
        this.codigo_barra = codigo_barra;
        this.comercio = comercio;
        this.precio = precio;
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

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public String getComercio() {
        return comercio;
    }

    public String getId_comercio() {
        return id_comercio;
    }

    public void setId_comercio(String id_comercio) {
        this.id_comercio = id_comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    @Override
    public String toString() {
        return "Producto: " + categoria  + " " + marca + " " + presentacion + " " + unidad +
                "\nLocal: " + comercio +
                "\nPrecio: " +precio; }
}
