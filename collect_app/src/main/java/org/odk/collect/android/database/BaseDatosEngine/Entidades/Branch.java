package org.odk.collect.android.database.BaseDatosEngine.Entidades;

/**
 * Created by Mardis on 14/07/2017.
 */

public class Branch {
    public String ID ;
    public String idbranch ;
    public String idAccount ;
    public String externalCode ;
    public String code ;
    public String name ;
    public String mainStreet ;
    public String neighborhood ;
    public String reference ;
    public String propietario ;
    public String uriformulario;
    public String idprovince;
    public String iddistrict;
    public String idParish;
    public String rutaaggregate;
    public String imeI_ID;
    public String LatitudeBranch;
    public String LenghtBranch;
    public  String TypeBusiness;
    public String Phone;
    public String Cedula;
    public String ESTADOAGGREGATE;

    public String getTypeBusiness() {
        return TypeBusiness;
    }

    public void setTypeBusiness(String typeBusiness) {
        TypeBusiness = typeBusiness;
    }

    public String getCedula() {
        return Cedula;
    }

    public String getESTADOAGGREGATE() {
        return ESTADOAGGREGATE;
    }

    public Branch(String ID, String idbranch, String idAccount, String externalCode, String code, String name, String mainStreet, String neighborhood, String reference, String propietario, String uriformulario, String idprovince, String iddistrict, String idParish, String rutaaggregate, String imeI_ID, String latitudeBranch, String lenghtBranch, String typeBusiness, String phone, String cedula, String ESTADOAGGREGATE) {
        this.ID = ID;
        this.idbranch = idbranch;
        this.idAccount = idAccount;
        this.externalCode = externalCode;
        this.code = code;
        this.name = name;
        this.mainStreet = mainStreet;
        this.neighborhood = neighborhood;
        this.reference = reference;
        this.propietario = propietario;
        this.uriformulario = uriformulario;
        this.idprovince = idprovince;
        this.iddistrict = iddistrict;
        this.idParish = idParish;
        this.rutaaggregate = rutaaggregate;
        this.imeI_ID = imeI_ID;
        LatitudeBranch = latitudeBranch;
        LenghtBranch = lenghtBranch;
        TypeBusiness = typeBusiness;
        Phone = phone;
        Cedula = cedula;
        this.ESTADOAGGREGATE = ESTADOAGGREGATE;
    }

    public void setESTADOAGGREGATE(String ESTADOAGGREGATE) {
        this.ESTADOAGGREGATE = ESTADOAGGREGATE;
    }

    public void setCedula(String cedula) {
        Cedula = cedula;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLatitudeBranch() {
        return LatitudeBranch;
    }

    public void setLatitudeBranch(String latitudeBranch) {
        LatitudeBranch = latitudeBranch;
    }

    public String getLenghtBranch() {
        return LenghtBranch;
    }

    public void setLenghtBranch(String lenghtBranch) {
        LenghtBranch = lenghtBranch;
    }

    public String getRutaaggregate() {

        return rutaaggregate;
    }

    public void setRutaaggregate(String rutaaggregate) {
        this.rutaaggregate = rutaaggregate;
    }

    public String getImeI_ID() {
        return imeI_ID;
    }

    public void setImeI_ID(String imeI_ID) {
        this.imeI_ID = imeI_ID;
    }

    public String getIdParish() {
        return idParish;
    }

    public void setIdParish(String idParish) {
        this.idParish = idParish;
    }

    public String getIdprovince() {
        return idprovince;
    }

    public void setIdprovince(String idprovince) {
        this.idprovince = idprovince;
    }

    public String getIddistrict() {
        return iddistrict;
    }

    public void setIddistrict(String iddistrict) {
        this.iddistrict = iddistrict;
    }

    public Branch() {
    }



    public String getUriformulario() {
        return uriformulario;
    }

    public void setUriformulario(String uriformulario) {
        this.uriformulario = uriformulario;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIdbranch() {
        return idbranch;
    }

    public void setIdbranch(String idbranch) {
        this.idbranch = idbranch;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return "Codigo='" + code + '\'' +
                ", Nombre='" + name + '\'' +
                ", Propietario='" + propietario + '\'' +
                ", Referencia='" + reference + '\'' ;
    }
}
