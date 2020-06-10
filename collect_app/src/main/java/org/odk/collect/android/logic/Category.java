package org.odk.collect.android.logic;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrador1 on 15/2/2018.
 */

public class Category {
    private String title;
    private String categoryId;
    private String description;
    private  String celular;
    private Drawable imagen;

    public Category() {
        super();
    }

    public Category(String categoryId, String title, String description, String celular, Drawable imagen) {
        super();
        this.title = title;
        this.description = description;
        this.imagen = imagen;
        this.categoryId = categoryId;
        this.celular=celular;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTitle() {
        return title;
    }

    public void setTittle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getCategoryId(){return categoryId;}

    public void setCategoryId(String categoryId){this.categoryId = categoryId;}

}