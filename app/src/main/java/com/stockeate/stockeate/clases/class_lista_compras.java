package com.stockeate.stockeate.clases;

public class class_lista_compras {
    private String id, id_usuario, id_lista_compras, fecha;

    public class_lista_compras() {
    }

    public class_lista_compras(String id, String id_usuario, String id_lista_compras, String fecha) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_lista_compras = id_lista_compras;
        this.fecha = fecha;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return id_lista_compras  + " Fecha: " + fecha; }
}
