package org.odk.collect.android.logic;

public class Resultado {
    private String title;
    private String cantidad;
    private String description;

    public Resultado() {
    }

    public Resultado(String title, String cantidad, String description) {
        this.title = title;
        this.cantidad = cantidad;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
