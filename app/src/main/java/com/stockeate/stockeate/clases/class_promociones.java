package com.stockeate.stockeate.clases;

public class class_promociones {

    private String id, categoria, marca, presentacion, tipo_promocion, local;

    public class_promociones() {
    }

    public class_promociones(String id, String categoria, String marca, String presentacion, String tipo_promocion, String local) {
        this.id = id;
        this.categoria = categoria;
        this.marca = marca;
        this.presentacion = presentacion;
        this.tipo_promocion = tipo_promocion;
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public String getTipo_promocion() {
        return tipo_promocion;
    }

    public void setTipo_promocion(String tipo_promocion) {
        this.tipo_promocion = tipo_promocion;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
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

    @Override
    public String toString() {
        return tipo_promocion + ' ' + categoria + ' ' + marca + ' ' + presentacion + ' ' + local; }
}
