package org.odk.collect.android.database.BaseDatosEngine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.odk.collect.android.application.Collect;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.Branch;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasCampanias;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasCodigos;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasDistrict;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasEngine;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasEstadoFormulario;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.ColumnasProvince;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD.Columnasparish;
import org.odk.collect.android.database.DatabaseContext;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Administrador1 on 7/2/2018.
 */

public class BaseDatosEngine {
    private static final String NOMBRE_BASE_DATOS = "EngineDatos.db";

    private static final int VERSION_ACTUAL = 26;
    private DatabaseHelperEngine dbHelper;
    private SQLiteDatabase db;
    interface Tablas {
        String LocalesEngine = "LocalesEngine";
        String CodigosNuevos = "CodigosNuevos";
        String CampaniaCuentas = "CampaniaCuentas";
        String Povince = "Province";
        String District = "District";
        String Parish = "Parish";
        String Service = "Service";
        String Configuracion = "Configuracion";
        String EstadoFormulario = "EstadoFormulario";
        String Producto = "Producto";
        String Operacion = "Operacion";
    }

    private static class DatabaseHelperEngine extends SQLiteOpenHelper {
        DatabaseHelperEngine() {
            super(new DatabaseContext(Collect.METADATA_PATH), NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to keep track of the itemsets
                String query = "create table LocalesEngine(_ID integer primary key," +
                        ColumnasEngine.idbranch + " text," +
                        ColumnasEngine.idAccount + " text," +
                        ColumnasEngine.externalCode + " text," +
                        ColumnasEngine.code + " text," +
                        ColumnasEngine.name + " text," +
                        ColumnasEngine.mainStreet + " text," +
                        ColumnasEngine.neighborhood + " text," +
                        ColumnasEngine.reference + " text," +
                        ColumnasEngine.propietario + " text," +
                        ColumnasEngine.uriformulario + " text," +
                        ColumnasEngine.idprovince + " text," +
                        ColumnasEngine.iddistrict + " text," +
                        ColumnasEngine.idParish + " text," +
                        ColumnasEngine.rutaaggregate + " text," +
                        ColumnasEngine.imeI_ID + " text," +
                        ColumnasEngine.LatitudeBranch + " text," +
                        ColumnasEngine.LenghtBranch + " text," +
                        ColumnasEngine.EstadoFormulario + " text," +
                        ColumnasEngine.Colabora + " text," +
                        ColumnasEngine.Celular + " text," +
                        ColumnasEngine.TypeBusiness + " text," +
                        ColumnasEngine.Cedula + " text," +
                        ColumnasEngine.ESTADOAGGREGATE + " text," +
                        ColumnasEngine.Foto_Exterior  + " text," +
                        ColumnasEngine.isclient + " text" +
                        ")";



                db.execSQL(query);

                query = "create table CodigosNuevos(_ID integer primary key ," +
                        ColumnasCodigos.idAccount + " text," +
                        ColumnasCodigos.code + " int," +
                        ColumnasCodigos.estado + " text," +
                        ColumnasCodigos.uri + " text," +
                        ColumnasCodigos.imei_id + " text," +
                        ColumnasCodigos.codeunico + " text" +
                        ")";

                db.execSQL(query);
                query = "create table " + Tablas.CampaniaCuentas + "(_ID integer primary key," +
                        ColumnasCampanias.idAccount + " text," +
                        ColumnasCampanias.AccountNombre + " text," +
                        ColumnasCampanias.IdCampania + " text," +
                        ColumnasCampanias.CampaniaNombre + " text" +
                        ")";
                db.execSQL(query);
                query = "create table " + Tablas.Povince + "(_ID integer primary key," +
                        ColumnasProvince.Idprovince + " text," +
                        ColumnasProvince.IdCountry + " text," +
                        ColumnasProvince.Code + " text," +
                        ColumnasProvince.Name + " text" +
                        ")";
                db.execSQL(query);
            query = "create table " + Tablas.EstadoFormulario + "(_ID integer primary key," +
                    ColumnasEstadoFormulario.idAccount + " text," +
                    ColumnasEstadoFormulario.IdCampania + " text," +
                    ColumnasEstadoFormulario.code + " text," +
                    ColumnasEstadoFormulario.ruta + " text," +
                    ColumnasEstadoFormulario.estadovisita + " text," +
                    ColumnasEstadoFormulario.estadointeres + " text," +
                    ColumnasEstadoFormulario.nombrelocal + " text," +
                    ColumnasEstadoFormulario.direccion + " text," +
                    ColumnasEstadoFormulario.referencia + " text," +
                    ColumnasEstadoFormulario.barrio + " text," +
                    ColumnasEstadoFormulario.latitud + " text," +
                    ColumnasEstadoFormulario.longitud + " text," +
                    ColumnasEstadoFormulario.nombrepro + " text," +
                    ColumnasEstadoFormulario.apellidopropi + " text," +
                    ColumnasEstadoFormulario.telefono + " text," +
                    ColumnasEstadoFormulario.celular + " text," +
                    ColumnasEstadoFormulario.fecha + " text," +
                    ColumnasEstadoFormulario.estadoenvio + " text," +
                    ColumnasEstadoFormulario.uri + " text," +
                    ColumnasEstadoFormulario.imei + " text," +
                    ColumnasEstadoFormulario.cedula + " text" +
                    ")";
            db.execSQL(query);
                query = "create table " + Tablas.District + "(_ID integer primary key," +
                        ColumnasDistrict.IdDistrict + " text," +
                        ColumnasDistrict.IdProvince + " text," +
                        ColumnasDistrict.Code + " text," +
                        ColumnasDistrict.Name + " text," +
                        ColumnasDistrict.StatusRegister + " text," +
                        ColumnasDistrict.IdManagement + " text" +
                        ")";
                db.execSQL(query);
                query = "create table " + Tablas.Parish + "(_ID integer primary key," +
                        Columnasparish.IdParish + " text," +
                        Columnasparish.IdDistrict + " text," +
                        Columnasparish.Code + " text," +
                        Columnasparish.Name + " text," +
                        Columnasparish.StatusRegister + " text" +
                        ")";
                db.execSQL(query);
            query = "create table " + Tablas.Configuracion + "(_ID integer primary key," +
                    EstructuraBD.ColumnasConfiguracion.Id_cuenta + " text," +
                    EstructuraBD.ColumnasConfiguracion.Id_campania + " text," +
                    EstructuraBD.ColumnasConfiguracion.FormaBusqueda + " text," +
                    EstructuraBD.ColumnasConfiguracion.Estado + " text" +
                    ")";
            db.execSQL(query);




        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Timber.w("Upgrading database from version %d to %d, which will destroy all old data", oldVersion, newVersion);
            // first drop all of our generated itemset tables
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.LocalesEngine);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.CodigosNuevos);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.CampaniaCuentas);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Povince);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.District);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Parish);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Service);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Configuracion);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Producto);
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.Operacion);
            if(newVersion==26){
                onCreate(db);
            }
        }
    }

        public BaseDatosEngine open() throws SQLException {
            dbHelper = new BaseDatosEngine.DatabaseHelperEngine();
            db = dbHelper.getWritableDatabase();
            //dbHelper.onUpgrade(db,1,2);
            return this;
        }
    public void beginTransaction() {
        db.execSQL("BEGIN");
    }

     public void commit() {
        db.execSQL("COMMIT");
    }
        public void close() {
            dbHelper.close();
        }

        //sdff
        //select * from mardiscore.branch  where idbrnahgfklsfksfkl kslsd lkgsd
    /* insert in to */
        public void CerrarBase(SQLiteDatabase db) {
            db.close();
        }

        //
        public Cursor ConfiguracionLista() {

            Cursor c = null;
            try {
                String query = "";
                query = "select * from " + Tablas.Configuracion;
                //db.execSQL(query);
                c = db.rawQuery(query, null);
                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;

            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }
    public Cursor ConfiguracionCanpania(String idaccount,String idcanpania) {

        Cursor c = null;
        try {
            String query = "";
            query = "select * from " + Tablas.CampaniaCuentas + " where idaccount='"+idaccount+"' and idcampania='"+idcanpania+"'";
            //db.execSQL(query);
            c = db.rawQuery(query, null);
            //Nos aseguramos de que existe al menos un registro
            List<Branch> resultado = null;

        } catch (Exception ex) {
            String a = ex.getMessage();
        }
        return c;
    }
        public boolean insertardatosConfiguracion(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.Configuracion + " values (" +
                        values.getAsString("ID") +
                        ",'" + values.getAsString(EstructuraBD.ColumnasConfiguracion.Id_cuenta) + "',"
                        + "'" + values.getAsString(EstructuraBD.ColumnasConfiguracion.Id_campania) + "',"
                        + "'" + values.getAsString(EstructuraBD.ColumnasConfiguracion.FormaBusqueda) + "',"
                        + "'" + values.getAsString(EstructuraBD.ColumnasConfiguracion.Estado) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CampaniaCuentas, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }
    public boolean EliminarRegistrosConfiguracion() {

        int idUsuario;
        try {
            idUsuario = (int) db.delete(Tablas.Configuracion, null, null);
            //CerrarBase(db);
            //listar();
            return true;
        } catch (Exception ex) {
            CerrarBase(db);
            return false;
        }
    }

        //metodos para ingresar provincia , distrinct ,parish
        public boolean insertardatosProvincia(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.Povince + " values (" +
                        values.getAsString("ID") +
                        ",'" + values.getAsString(ColumnasProvince.Idprovince) + "',"
                        + "'" + values.getAsString(ColumnasProvince.IdCountry) + "',"
                        + "'" + values.getAsString(ColumnasProvince.Code) + "',"
                        + "'" + values.getAsString(ColumnasProvince.Name) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CampaniaCuentas, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public Cursor listarProvincias() {
            String[] campos = new String[]{"_ID", ColumnasProvince.Idprovince, ColumnasProvince.IdCountry, ColumnasProvince.Code, ColumnasProvince.Name};

            Cursor c = null;
            String[] args = new String[]{""};
            try {
                c = db.query(Tablas.Povince, campos, "", null, null, null, null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public Cursor listarCiudad(String[] args) {
            String[] campos = new String[]{"_ID", ColumnasDistrict.IdDistrict, ColumnasDistrict.IdProvince, ColumnasDistrict.Code, ColumnasDistrict.Name};

            Cursor c = null;

            try {
                c = db.query(Tablas.District, campos, "IdProvince=?", args, null, null, null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public Cursor listarParroquia(String[] args) {
            String[] campos = new String[]{"_ID", Columnasparish.IdParish, Columnasparish.IdDistrict, Columnasparish.Code, Columnasparish.Name};

            Cursor c = null;

            try {
                c = db.query(Tablas.Parish, campos, "IdDistrict=?", args, null, null, null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public boolean insertardatosDistrict(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.District + " values (" +
                        values.getAsString("ID") +
                        ",'" + values.getAsString(ColumnasDistrict.IdDistrict) + "',"
                        + "'" + values.getAsString(ColumnasDistrict.IdProvince) + "',"
                        + "'" + values.getAsString(ColumnasDistrict.Code) + "',"
                        + "'" + values.getAsString(ColumnasDistrict.Name) + "',"
                        + "'" + values.getAsString(ColumnasDistrict.StatusRegister) + "',"
                        + "'" + values.getAsString(ColumnasDistrict.IdManagement) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CampaniaCuentas, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean insertardatosparish(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.Parish + " values (" +
                        values.getAsString("ID") +
                        ",'" + values.getAsString(Columnasparish.IdParish) + "',"
                        + "'" + values.getAsString(Columnasparish.IdDistrict) + "',"
                        + "'" + values.getAsString(Columnasparish.Code) + "',"
                        + "'" + values.getAsString(Columnasparish.Name) + "',"
                        + "'" + values.getAsString(Columnasparish.StatusRegister) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CampaniaCuentas, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }


        //metodos para ingresar cuenta campañas
        public boolean insertardatosCampania(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.CampaniaCuentas + " values (" +
                        values.getAsString("ID") +
                        ",'" + values.getAsString(ColumnasCampanias.idAccount) + "',"
                        + "'" + values.getAsString(ColumnasCampanias.AccountNombre) + "',"
                        + "'" + values.getAsString(ColumnasCampanias.IdCampania) + "',"
                        + "'" + values.getAsString(ColumnasCampanias.CampaniaNombre) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CampaniaCuentas, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public Cursor listarCanpanias() {
            String[] campos = new String[]{"_ID", ColumnasCampanias.idAccount, ColumnasCampanias.AccountNombre, ColumnasCampanias.IdCampania, ColumnasCampanias.CampaniaNombre};

            Cursor c = null;
            String[] args = new String[]{""};
            try {
                c = db.query(Tablas.CampaniaCuentas, campos, "", null, null, null, null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public boolean EliminarRegistrosCampania() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.CampaniaCuentas, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean EliminarRegistrosParish() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.Parish, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean EliminarRegistrosProvincia() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.Povince, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean EliminarRegistrosDistrict() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.District, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }


        //metodos para ingresar codigos nuevos
        public boolean insertardatosCodigos(ContentValues values) {

            int idUsuario;
            try {
                String query = "insert into " + Tablas.CodigosNuevos + " values (" +
                        values.getAsString(ColumnasCodigos.ID) + ","
                        + "'" + values.getAsString(ColumnasCodigos.idAccount) + "',"
                        + "'" + values.getAsString(ColumnasCodigos.code) + "',"
                        + "'" + values.getAsString(ColumnasCodigos.estado) + "',"
                        + "'" + values.getAsString(ColumnasCodigos.uri) + "',"
                        + "'" + values.getAsString(ColumnasCodigos.imei_id) + "',"
                        + "'" + values.getAsString(ColumnasCodigos.codeunico) + "')";
                db.execSQL(query);
                //idUsuario = (int) db.insert(Tablas.CodigosNuevos, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean EliminarRegistrosCodigos() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.CodigosNuevos, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public Cursor ContarEstado(String where)
        {
            Cursor c = null;
            try {
                String query = "";
                query = "select count(_id) from " + Tablas.LocalesEngine + "  "+ where;
                c = db.rawQuery(query, null);
                List<Branch> resultado = null;
                if (c.moveToFirst()) {
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }
        public Cursor listarCodigois() {
            String[] campos = new String[]{"_ID", ColumnasCodigos.idAccount, ColumnasCodigos.code, ColumnasCodigos.estado, ColumnasCodigos.uri, ColumnasCodigos.imei_id};

            Cursor c = null;
            String[] args = new String[]{""};
            try {
                String query = "";
                query = "select max(code) from " + Tablas.CodigosNuevos + " where uri=''  ";
                //db.execSQL(query);
                c = db.rawQuery(query, null);

                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        //cargar rutas que estan en ese moento en el celular censo

        public Cursor listarRutasCodigos() {
            String[] campos = new String[]{"_ID", ColumnasCodigos.idAccount, ColumnasCodigos.code, ColumnasCodigos.estado, ColumnasCodigos.uri, ColumnasCodigos.imei_id};

            Cursor c = null;
            String[] args = new String[]{""};
            try {
                String query = "";
                query = "select DISTINCT rutaaggregate from " + Tablas.LocalesEngine;
                //db.execSQL(query);
                c = db.rawQuery(query, null);

                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public int CodigosActual(String tabla) {
            String[] campos = new String[]{"_ID", ColumnasCodigos.idAccount, ColumnasCodigos.code, ColumnasCodigos.estado, ColumnasCodigos.uri, ColumnasCodigos.imei_id};

            Cursor c = null;
            String[] args = new String[]{""};
            int codigo = 1;
            try {
                String query = "";
                query = "select count(_ID) from " + tabla;
                //db.execSQL(query);
                c = db.rawQuery(query, null);

                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        codigo = c.getInt(0) + 1;
                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return codigo;
        }


        public Cursor CodigoNuevo(String code) {
            String[] campos = new String[]{"_ID", ColumnasCodigos.idAccount, ColumnasCodigos.code, ColumnasCodigos.estado, ColumnasCodigos.uri, ColumnasCodigos.imei_id};

            Cursor c = null;
            String[] args = new String[]{""};
            try {
                String query = "";
                query = "select * from " + Tablas.CodigosNuevos + " where code='" + code + "'";
                //db.execSQL(query);
                c = db.rawQuery(query, null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {


                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }
//resumen datos
public Cursor ResumenDatos(String consulta) {

    Cursor c = null;
    String[] args = new String[]{""};
    try {
        String query = "";
        query = consulta;
        //db.execSQL(query);
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {


            } while (c.moveToNext());
        }
    } catch (Exception ex) {
        String a = ex.getMessage();
    }
    return c;
}


        public boolean ActualizarTablaCodigos(ContentValues values, String sentencia) {

            int idUsuario;
            try {
                idUsuario = (int) db.update(Tablas.CodigosNuevos, values, sentencia, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

    public boolean ActualizarTablaestado(ContentValues values, String sentencia) {

        int idUsuario;
        try {
            idUsuario = (int) db.update(Tablas.EstadoFormulario, values, sentencia, null);
            //CerrarBase(db);
            //listar();
            return true;
        } catch (Exception ex) {
            CerrarBase(db);
            return false;
        }
    }

        //metodos para insertar modificar listar datos
        public boolean insertardatos(ContentValues values) {

            int idUsuario;
            try {
                idUsuario = (int) db.insert(Tablas.LocalesEngine, null, values);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }


        }

    public boolean insertardatosFormulario(ContentValues values) {

        int idUsuario;
        try {
            idUsuario = (int) db.insert(Tablas.EstadoFormulario, null, values);
            //CerrarBase(db);
            //listar();
            return true;
        } catch (Exception ex) {
            CerrarBase(db);
            return false;
        }


    }

        public boolean ActualizarTabla(ContentValues values, String sentencia) {

            int idUsuario;
            try {
                idUsuario = (int) db.update(Tablas.LocalesEngine, values, sentencia, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public boolean EliminarRegistros() {

            int idUsuario;
            try {
                idUsuario = (int) db.delete(Tablas.LocalesEngine, null, null);
                //CerrarBase(db);
                //listar();
                return true;
            } catch (Exception ex) {
                CerrarBase(db);
                return false;
            }
        }

        public Cursor listar(String[] argumentos, String op, String where) {
            String[] campos = new String[]{"_ID", "idbranch", "idAccount", "externalCode", "code", "name", "mainStreet", "neighborhood", "reference", "propietario", "uriformulario"};

            Cursor c = null;
            try {
                String query = "";
                query = "select * from " + Tablas.LocalesEngine + "  " + where;
                //db.execSQL(query);
                c = db.rawQuery(query, null);


                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        Branch obj = new Branch();
                        obj.setID(c.getString(0));
                        obj.setIdbranch(c.getString(1));
                        obj.setIdAccount(c.getString(2));
                        obj.setExternalCode(c.getString(3));
                        obj.setCode(c.getString(4));
                        obj.setName(c.getString(5));
                        obj.setMainStreet(c.getString(6));
                        obj.setNeighborhood(c.getString(7));
                        obj.setReference(c.getString(8));
                        obj.setPropietario(c.getString(9));
                        obj.setUriformulario(c.getColumnName(10));
                        //resultado.add(obj);

                    } while (c.moveToNext());
                }
            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }

        public Cursor RutaLista() {

            Cursor c = null;
            try {
                String query = "";
                query = "select rutaaggregate from " + Tablas.LocalesEngine;
                //db.execSQL(query);
                c = db.rawQuery(query, null);
                //Nos aseguramos de que existe al menos un registro
                List<Branch> resultado = null;

            } catch (Exception ex) {
                String a = ex.getMessage();
            }
            return c;
        }


    }

