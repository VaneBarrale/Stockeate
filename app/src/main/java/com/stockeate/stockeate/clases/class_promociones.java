package com.stockeate.stockeate.clases;

public class class_promociones {

    private String id, id_producto, tipo_promocion;

    public class_promociones() {
    }

    public class_promociones(String id, String id_producto, String tipo_promocion) {
        this.id = id;
        this.id_producto = id_producto;
        this.tipo_promocion = tipo_promocion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getTipo_promocion() {
        return tipo_promocion;
    }

    public void setTipo_promocion(String tipo_promocion) {
        this.tipo_promocion = tipo_promocion;
    }
}
