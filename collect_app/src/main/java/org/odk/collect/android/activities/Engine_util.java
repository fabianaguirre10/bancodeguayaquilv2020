package org.odk.collect.android.activities;

import android.database.Cursor;
import android.os.Environment;
import android.os.StatFs;

import org.odk.collect.android.database.BaseDatosEngine.BaseDatosEngine;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.Capania;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionDB;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionSession;

/**
 * Created by Administrador1 on 9/2/2018.
 */

public class Engine_util {
    public Engine_util() {
    }

    final ConfiguracionSession objconfiguracionSession= new ConfiguracionSession();
    public void CargarConfiguracion(){
        BaseDatosEngine usdbh = new BaseDatosEngine();
        usdbh = usdbh.open();
        Cursor c= usdbh.ConfiguracionLista();
        ConfiguracionDB actualconf= new ConfiguracionDB();
        if(c!=null) {
            if (c.moveToFirst()) {
                do {
                    actualconf = new ConfiguracionDB();
                    actualconf.setId_cuenta(c.getString(1));
                    actualconf.setId_campania(c.getString(2));
                    actualconf.setFormaBusqueda(c.getString(3));
                    actualconf.setEstado(c.getString(4));
                    Cursor AccountCanpa = usdbh.ConfiguracionCanpania(actualconf.getId_cuenta(),actualconf.getId_campania());
                    if(AccountCanpa!=null) {
                        if (AccountCanpa.moveToFirst()) {
                            do {
                                Capania objcampania = new Capania();
                                objcampania.setID(AccountCanpa.getString(0));
                                objcampania.setIdAccount(AccountCanpa.getString(1));
                                objcampania.setAccountNombre(AccountCanpa.getString(2));
                                objcampania.setIdCampania(AccountCanpa.getString(3));
                                objcampania.setCampaniaNombre(AccountCanpa.getString(4));
                                objconfiguracionSession.setCnf_idAccount(objcampania.getIdAccount());
                                objconfiguracionSession.setCnf_idcampania(objcampania.getIdCampania());
                                objconfiguracionSession.setCnf_AccountNombre(objcampania.getAccountNombre());
                                objconfiguracionSession.setCnf_CampaniaNombre(objcampania.getCampaniaNombre());
                                objconfiguracionSession.setCnf_factorbusqueda(actualconf.getFormaBusqueda());

                            } while (AccountCanpa.moveToNext());
                        }
                    }
                } while (c.moveToNext());
            }
        }
        usdbh.close();
    }
    public Cursor ListarRutas() {
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            String[] args = new String[]{};
            Cursor cursor = usdbh.listarRutasCodigos();
            usdbh.close();
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }
    public  Cursor ListarTareas( String[] args,String opcion,String where){
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            Cursor cursor = usdbh.listar(args, opcion, where);
            usdbh.close();
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }
    public  Cursor ContarEstado(String where){
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            Cursor cursor = usdbh.ContarEstado(where);
            usdbh.close();
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }

    public void EliminarCodigos(){
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            usdbh.EliminarRegistrosCodigos();
            usdbh.close();
        } catch (Exception ex) {
        }
    }
    public Cursor SeleccionarLocal(String where){
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            Cursor cursor=usdbh.listar(null,null,where);
            usdbh.close();
            return cursor;
        } catch (Exception ex) {
            return  null;
        }
    }
    public long totalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   total  = (statFs.getBlockCount() * statFs.getBlockSize());
        return total;
    }

    public static float freeMemory()
    {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long)stat.getBlockSize() * (long)stat.getAvailableBlocks()) / (1024.f * 1024.f);
    }

    public long busyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   total  = (statFs.getBlockCount() * statFs.getBlockSize());
        long   free   = (statFs.getAvailableBlocks() * statFs.getBlockSize());
        long   busy   = total - free;
        return busy;
    }
    public Cursor getResumen(String consulta)
    {
        try {
            BaseDatosEngine usdbh = new BaseDatosEngine();
            usdbh = usdbh.open();
            Cursor cursor=usdbh.ResumenDatos(consulta);
            usdbh.close();
            return cursor;
        } catch (Exception ex) {
            return  null;
        }
    }
}
