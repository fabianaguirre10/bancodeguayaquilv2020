package org.odk.collect.android.database.BaseDatosEngine;

/**
 * Created by Mardis on 14/07/2017.
 */
import java.util.UUID;

public class EstructuraBD {
    public interface ColumnasEngine {
        String ID = "id";
        String idbranch = "idbranch";
        String idAccount = "idAccount";
        String externalCode = "externalCode";
        String code = "code";
        String name = "name";
        String mainStreet = "mainStreet";
        String neighborhood = "neighborhood";
        String reference = "reference";
        String propietario = "propietario";
        String uriformulario="uriformulario";
        String idprovince="idprovince";
        String iddistrict="iddistrict";
        String idParish="idParish";
        String rutaaggregate="rutaaggregate";
        String imeI_ID="imeI_ID";
        String LatitudeBranch="LatitudeBranch";
        String LenghtBranch="LenghtBranch";
        String EstadoFormulario="EstadoFormulario";
        String AbiertoLocal="AbiertoLocal";
        String Colabora="Colabora";
        String Celular="Celular";
        String TypeBusiness="TypeBusiness";
        String Cedula="Cedula";
        String ESTADOAGGREGATE="ESTADOAGGREGATE";
        String Foto_Exterior="Foto_Exterior";
        String isclient="isclient";
    }
    public static class CabecerasEngine implements ColumnasEngine {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }

    interface ColumnasCodigos{
        String ID = "id";
        String idAccount = "idAccount";
        String code = "code";
        String estado = "estado";
        String uri = "uri";
        String imei_id = "imei_id";
        String codeunico="codeunico";

    }
    public static class CabecerasCodigos implements ColumnasCodigos {
            public static String generarIdCabeceraPedido() {
                return "CP-" + UUID.randomUUID().toString();
            }
    }
    interface ColumnasCampanias {
        String ID = "id";
        String idAccount = "idAccount";
        String AccountNombre = "AccountNombre";
        String IdCampania = "IdCampania";
        String CampaniaNombre = "CampaniaNombre";
    }
    public static class CabecerasCampanias implements ColumnasCampanias {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface ColumnasProvince {
        String ID="ID";
        String Idprovince="Idprovince";
        String IdCountry="IdCountry";
        String Code="Code";
        String Name="Name";
    }
    public static class CabecerasProvince implements ColumnasProvince {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface ColumnasDistrict{
        String ID="ID";
        String IdDistrict="IdDistrict";
        String IdProvince="IdProvince";
        String Code="Code";
        String Name="Name";
        String StatusRegister="StatusRegister";
        String IdManagement="IdManagement";
    }
    public static class CabecerasDistrict implements ColumnasDistrict {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface Columnasparish{
        String ID="ID";
        String IdParish="IdParish";
        String IdDistrict="IdDistrict";
        String Code="Code";
        String Name="Name";
        String StatusRegister="StatusRegister";
    }
    public static class Cabecerasparish implements Columnasparish {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }

    interface ColumnasService{
        String ID="ID";
        String Idservice="Idservice";
        String Code="Code";
        String Name="Name";
        String IdTypeService="IdTypeService";
        String PollTitle="PollTitle";
        String IdAccount="IdAccount";
        String IdCustomer="IdCustomer";
        String CreationDate="CreationDate";
        String StatusRegister="StatusRegister";
        String IdChannel="IdChannel";
        String Icon="Icon";
        String IconColor="IconColor";
        String Template="Template";
    }
    public static class CabeceraService implements ColumnasService {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface ColumnasServiceDetail{
         String Id="Id";
         String IdServiceDetail="IdServiceDetail";
         String IdService="IdService";
         String Order="Order";
         String StatusRegister="StatusRegister";
         String SectionTitle="SectionTitle";
         String Weight="Weight";
         String HasPhoto="HasPhoto";
         String IsDynamic="IsDynamic";
         String GroupName="GroupName";
         String IdSection="IdSection";
         String NumberOfCopies="NumberOfCopies";
    }
    public static class CabeceraServiceDetail implements ColumnasServiceDetail {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface ColumnasQuestion{
         String Id="Id";
         String IdQuestion="IdQuestion";
         String IdServiceDetail="IdServiceDetail";
         String Title="Title";
         String StatusRegister="StatusRegister";
         String Order="Order";
         String Weight="Weight";
         String IdTypePoll="IdTypePoll";
         String HasPhoto="HasPhoto";
         String CountPhoto="CountPhoto";
         String IdProductCategory="IdProductCategory";
         String IdProduct="IdProduct";
         String AnswerRequired="AnswerRequired";
    }
    public static class CabeceraQuestion implements ColumnasQuestion {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }
    interface ColumnasQuestionDetail{
        String Id="Id";
        String IdQuestionDetail="IdQuestionDetail";
        String IdQuestion="IdQuestion";
        String Order="Order";
        String Weight="Weight";
        String Answer="Answer";
        String StatusRegister="StatusRegister";
        String IsNext="IsNext";
        String IdQuestionLink="IdQuestionLink";
    }
    public static class CabeceraQuestionDetail implements ColumnasQuestionDetail {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }

    }
    interface ColumnasConfiguracion{
        String Id_cuenta="Id_cuenta";
        String Id_campania="Id_campania";
        String FormaBusqueda="FormaBusqueda";
        String Estado="Estado";
    }
    public static class CabeceraConfiguracion implements ColumnasConfiguracion {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }

    }
    interface ColumnasEstadoFormulario{

         String idAccount="idAccount"  ;
         String IdCampania="IdCampania" ;
         String code="code" ;
         String ruta="ruta" ;
         String estadovisita="estadovisita" ;
         String estadointeres="estadointeres" ;
         String nombrelocal="nombrelocal" ;
         String direccion="direccion" ;
         String referencia="referencia" ;
         String barrio="barrio" ;
         String latitud="latitud" ;
         String longitud="longitud" ;
         String nombrepro="nombrepro" ;
         String apellidopropi="apellidopropi" ;
         String telefono="telefono" ;
         String celular="celular" ;
         String imei="imei" ;
         String fecha="fecha" ;
        String estadoenvio="estadoenvio" ;
        String uri="uri" ;
        String cedula="cedula" ;


    }
    public static class CabeceraEstadoformulario implements ColumnasEstadoFormulario {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }

    }
    interface ColumnasProductos{
        String codproducto="codproducto";
        String name="name";
        String pvp="pvp";
        String categoria="categoria";
        String stock="stock";
        String descripcion="descripcion";
        String codigosecundario="codigosecundario";
        String estado="estado";
    }
    public static class CabeceraProductos implements ColumnasProductos {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }

    }
    interface ColumnasOperaciones{
        String cantidad="cantidad";
        String codproducto="codproducto";
        String tipooperacion="tipooperacion";
        String fecha="fecha";
        String estado="estado";
    }
    public static class CabeceraOperaciones implements ColumnasOperaciones {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }

    }

}
