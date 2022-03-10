package com.stockeate.stockeate.clases;

import java.util.ArrayList;

public class class_promociones {

    private String tipo_promocion;
    private int id;
    private ArrayList<class_detalle_promocion> detalles;

    public class_promociones() {
    }

    public class_promociones(int id, String tipo_promocion, ArrayList<class_detalle_promocion> detalles) {
        this.id = id;
        this.tipo_promocion = tipo_promocion;
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<class_detalle_promocion> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<class_detalle_promocion> detalles) {
        this.detalles = detalles;
    }

    public String getTipo_promocion() {
        return tipo_promocion;
    }

    public void setTipoPromocion(String tipo_promocion) {
        this.tipo_promocion = tipo_promocion;
    }

    @Override
    public String toString() {
        return tipo_promocion + ' ' + detalles;}
}
