package com.stockeate.stockeate.clases;

public class class_comparar_precios {
    private String id,  id_local, local;
    private float precio_total;

    public class_comparar_precios() {
    }

    public class_comparar_precios(String id, String id_local, String local, float precio_total) {
        this.id = id;
        this.id_local = id_local;
        this.local = local;
        this.precio_total = precio_total;
    }

    public String getId() {return id; }

    public void setId(String id) { this.id = id; }

    public String getId_local() { return id_local; }

    public void setId_local(String id_local) { this.id_local = id_local; }

    public String getLocal() { return local; }

    public void setLocal(String local) { this.local = local; }

    public float getPrecio_total() { return precio_total; }

    public void setPrecio_total(float precio_total) { this.precio_total = precio_total; }

    public String toString() {
        return "Local: " + local + "\nPrecio total: " + precio_total;
    }
}
