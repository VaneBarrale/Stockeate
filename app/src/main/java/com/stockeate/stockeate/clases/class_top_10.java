package com.stockeate.stockeate.clases;

public class class_top_10 {
    private String id,  id_local, local, id_categoria, categoria, id_marca, marca, top;

    public class_top_10() {
    }

    public class_top_10(String id, String id_local, String local, String id_categoria, String categoria, String id_marca, String marca, String top) {
        this.id = id;
        this.id_local = id_local;
        this.local = local;
        this.id_categoria = id_categoria;
        this.categoria = categoria;
        this.id_marca = id_marca;
        this.marca = marca;
        this.top = top;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_local() {
        return id_local;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getId_marca() {
        return id_marca;
    }

    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String toString() {
        return top  + ' ' + categoria; }
}
