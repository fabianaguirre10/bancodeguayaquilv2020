package org.odk.collect.android.database.BaseDatosEngine.Entidades;

/**
 * Created by Mardis on 26/07/2017.
 */

public class CodigosNuevos {
    public String ID;
    public String idAccount;
    public int code;
    public String estado;
    public String uri;
    public String imei_id;
    public String codeunico;

    public CodigosNuevos() {
    }

    public CodigosNuevos(String ID, String idAccount, int code, String estado, String uri, String imei_id, String codeunico) {
        this.ID = ID;
        this.idAccount = idAccount;
        this.code = code;
        this.estado = estado;
        this.uri = uri;
        this.imei_id = imei_id;
        this.codeunico = codeunico;
    }

    public String getCodeunico() {

        return codeunico;
    }

    public void setCodeunico(String codeunico) {
        this.codeunico = codeunico;
    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getImei_id() {
        return imei_id;
    }

    public void setImei_id(String imei_id) {
        this.imei_id = imei_id;
    }
}
