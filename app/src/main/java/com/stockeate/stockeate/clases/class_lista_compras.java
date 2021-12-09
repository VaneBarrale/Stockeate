package com.stockeate.stockeate.clases;

public class class_lista_compras {
    private String id, id_usuario, id_lista_compras, fecha, id_producto;

    public class_lista_compras() {
    }

    public class_lista_compras(String id, String id_usuario, String id_lista_compras, String fecha, String id_producto) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_lista_compras = id_lista_compras;
        this.fecha = fecha;
        this.id_producto = id_producto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return id; }
}
