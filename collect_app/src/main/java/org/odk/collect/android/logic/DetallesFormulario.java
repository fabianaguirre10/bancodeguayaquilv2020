package org.odk.collect.android.logic;

public class DetallesFormulario {
    private String title;
    private String description;
    private String cantidad;

    public DetallesFormulario() {
    }

    public DetallesFormulario(String title, String description, String cantidad) {
        this.title = title;
        this.description = description;
        this.cantidad = cantidad;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
