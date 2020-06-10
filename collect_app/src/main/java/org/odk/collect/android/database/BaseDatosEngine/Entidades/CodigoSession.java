package org.odk.collect.android.database.BaseDatosEngine.Entidades;

/**
 * Created by Mardis on 26/07/2017.
 */

public class CodigoSession {
    public static String C_ID = "C_id";
    public static String C_idAccount = "C_idAccount";
    public static String C_code = "C_code";
    public static String C_estado = "C_estado";
    public static String C_uri = "C_uri";
    public static String C_imei_id = "C_imei_id";
    public static String C_codeunico;

    public static String getC_codeunico() {
        return C_codeunico;
    }

    public static void setC_codeunico(String c_codeunico) {
        C_codeunico = c_codeunico;
    }

    public CodigoSession() {

    }

    public static String getcId() {
        return C_ID;
    }

    public static void setcId(String cId) {
        C_ID = cId;
    }

    public static String getC_idAccount() {
        return C_idAccount;
    }

    public static void setC_idAccount(String c_idAccount) {
        C_idAccount = c_idAccount;
    }

    public static String getC_code() {
        return C_code;
    }

    public static void setC_code(String c_code) {
        C_code = c_code;
    }

    public static String getC_estado() {
        return C_estado;
    }

    public static void setC_estado(String c_estado) {
        C_estado = c_estado;
    }

    public static String getC_uri() {
        return C_uri;
    }

    public static void setC_uri(String c_uri) {
        C_uri = c_uri;
    }

    public static String getC_imei_id() {
        return C_imei_id;
    }

    public static void setC_imei_id(String c_imei_id) {
        C_imei_id = c_imei_id;
    }
}
