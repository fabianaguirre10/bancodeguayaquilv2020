package org.odk.collect.android.database.BaseDatosEngine.Entidades;

public class EstadosFormulario {
    public int ID ;
    public String idAccount ;
    public String IdCampania;
    public String code;
    public String ruta;
    public String estadovisita;
    public String estadointeres;
    public String nombrelocal;
    public String direccion;
    public String referencia;
    public String barrio;
    public String latitud;
    public String longitud;
    public String nombrepro;
    public String apellidopropi;
    public String telefono;
    public String celular;
    public String imei;
    public String fecha ;
    public String estadoenvio;
    public String uri;
    public  String cedula;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstadoenvio() {
        return estadoenvio;
    }

    public void setEstadoenvio(String estadoenvio) {
        this.estadoenvio = estadoenvio;
    }



    public EstadosFormulario() {
    }

    public EstadosFormulario(int ID, String idAccount, String idCampania, String code, String ruta, String estadovisita, String estadointeres, String nombrelocal, String direccion, String referencia, String barrio, String latitud, String longitud, String nombrepro, String apellidopropi, String telefono, String celular, String imei) {
        this.ID = ID;
        this.idAccount = idAccount;
        IdCampania = idCampania;
        this.code = code;
        this.ruta = ruta;
        this.estadovisita = estadovisita;
        this.estadointeres = estadointeres;
        this.nombrelocal = nombrelocal;
        this.direccion = direccion;
        this.referencia = referencia;
        this.barrio = barrio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombrepro = nombrepro;
        this.apellidopropi = apellidopropi;
        this.telefono = telefono;
        this.celular = celular;
        this.imei = imei;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getIdCampania() {
        return IdCampania;
    }

    public void setIdCampania(String idCampania) {
        IdCampania = idCampania;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getEstadovisita() {
        return estadovisita;
    }

    public void setEstadovisita(String estadovisita) {
        this.estadovisita = estadovisita;
    }

    public String getEstadointeres() {
        return estadointeres;
    }

    public void setEstadointeres(String estadointeres) {
        this.estadointeres = estadointeres;
    }

    public String getNombrelocal() {
        return nombrelocal;
    }

    public void setNombrelocal(String nombrelocal) {
        this.nombrelocal = nombrelocal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombrepro() {
        return nombrepro;
    }

    public void setNombrepro(String nombrepro) {
        this.nombrepro = nombrepro;
    }

    public String getApellidopropi() {
        return apellidopropi;
    }

    public void setApellidopropi(String apellidopropi) {
        this.apellidopropi = apellidopropi;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
