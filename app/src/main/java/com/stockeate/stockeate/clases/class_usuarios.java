package com.stockeate.stockeate.clases;

public class class_usuarios{
    private String id;
    private String mail;
    private String contraseña;

    public class_usuarios(String mail) {
        this.id = id;
        this.mail = mail;
        this.contraseña = contraseña;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}