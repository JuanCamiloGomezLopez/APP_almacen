package com.example.appcamilo;

public class blog {

    private String producto;
    private String descripccion;
    private boolean permission;


    public blog(String producto, String descripccion, boolean permission) {
        this.producto = producto;
        this.descripccion = descripccion;
        this.permission = permission;
    }

    public blog() {
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
