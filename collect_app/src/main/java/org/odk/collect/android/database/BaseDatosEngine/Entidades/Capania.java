package org.odk.collect.android.database.BaseDatosEngine.Entidades;

/**
 * Created by Mardis on 28/07/2017.
 */

public class Capania {
    public String ID ;
    public String idAccount ;
    public String AccountNombre;
    public String IdCampania;
    public String CampaniaNombre ;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getAccountNombre() {
        return AccountNombre;
    }

    public void setAccountNombre(String accountNombre) {
        AccountNombre = accountNombre;
    }

    public String getIdCampania() {
        return IdCampania;
    }

    public void setIdCampania(String idCampania) {
        IdCampania = idCampania;
    }

    public String getCampaniaNombre() {
        return CampaniaNombre;
    }

    public void setCampaniaNombre(String campaniaNombre) {
        CampaniaNombre = campaniaNombre;
    }

    public Capania(String ID, String idAccount, String accountNombre, String idCampania, String campaniaNombre) {
        this.ID = ID;
        this.idAccount = idAccount;
        AccountNombre = accountNombre;
        IdCampania = idCampania;
        CampaniaNombre = campaniaNombre;
    }

    public Capania() {
    }

    @Override
    public String toString() {
        return CampaniaNombre+"-"+AccountNombre;
    }
}
