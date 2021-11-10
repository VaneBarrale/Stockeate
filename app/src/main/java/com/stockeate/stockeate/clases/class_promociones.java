package com.stockeate.stockeate.clases;

public class class_promociones {

    private String id, id_producto, tipo_promocion, desc_producto, desc_tipo_producto, local;

    public class_promociones() {
    }

    public class_promociones(String id, String id_producto, String tipo_promocion, String desc_producto, String desc_tipo_producto, String local) {
        this.id = id;
        this.id_producto = id_producto;
        this.tipo_promocion = tipo_promocion;
        this.desc_producto = desc_producto;
        this.desc_tipo_producto = desc_tipo_producto;
        this.tipo_promocion = tipo_promocion;
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public String getDesc_producto() {
        return desc_producto;
    }

    public void setDesc_producto(String desc_producto) {
        this.desc_producto = desc_producto;
    }

    public String getDesc_tipo_producto() {
        return desc_tipo_producto;
    }

    public void setDesc_tipo_producto(String desc_tipo_producto) {
        this.desc_tipo_producto = desc_tipo_producto;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setTipo_promocion(String tipo_promocion) {
        this.tipo_promocion = tipo_promocion;
    }

    @Override
    public String toString() {
        return tipo_promocion + ' ' + desc_tipo_producto + ' ' + desc_producto + ' ' + local; }
}
