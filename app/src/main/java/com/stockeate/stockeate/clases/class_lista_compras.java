package com.stockeate.stockeate.clases;

public class class_lista_compras {
    private String id, id_usuario;

    public class_lista_compras() {
    }

    public class_lista_compras(String id, String id_usuario) {
        this.id = id;
        this.id_usuario = id_usuario;
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
}
