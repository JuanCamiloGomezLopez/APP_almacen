package com.example.appcamilo;

public class generar_rm {

  private  String material_rm;
  private  String descripccion_rm;
  private  String unidad;
  private  int cantidad_rm;
  private boolean permission;


    public generar_rm(String material_rm, String descripccion_rm, String unidad, int cantidad_rm, boolean permission) {
        this.material_rm = material_rm;
        this.descripccion_rm = descripccion_rm;
        this.unidad = unidad;
        this.cantidad_rm = cantidad_rm;
        this.permission = permission;
    }

    public generar_rm() {
    }

    public String getMaterial_rm() {
        return material_rm;
    }

    public void setMaterial_rm(String material_rm) {
        this.material_rm = material_rm;
    }

    public String getDescripccion_rm() {
        return descripccion_rm;
    }

    public void setDescripccion_rm(String descripccion_rm) {
        this.descripccion_rm = descripccion_rm;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCantidad_rm() {
        return cantidad_rm;
    }

    public void setCantidad_rm(int cantidad_rm) {
        this.cantidad_rm = cantidad_rm;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }


}
