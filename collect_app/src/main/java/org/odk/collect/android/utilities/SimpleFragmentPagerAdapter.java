package org.odk.collect.android.utilities;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.odk.collect.android.activities.Engine_util;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.EstadosFormulario;
import org.odk.collect.android.fragments.Activos;
import org.odk.collect.android.fragments.EstadosFinalizados;
import org.odk.collect.android.fragments.EstadosFormularios;
import org.odk.collect.android.fragments.mapa;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    String numeroruta,_CodigoLocal;
    Engine_util objutil;
    int finalizadosE;
    int estadoFinalizado;
    int activosE;
    int mapaC;
    Bundle args = new Bundle();
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, String ruta,String CodigoLocal) {
        super(fm);
        numeroruta=ruta;
        _CodigoLocal=CodigoLocal;
        args.putString("Ruta",numeroruta );
        args.putString("Codigo",_CodigoLocal );
        objutil= new Engine_util();
        String where = "where 1=1 ";
        where = "where 1=1 and ESTADOAGGREGATE <>'D' and uriformulario = ''  ";
        where = where + " and  rutaaggregate ='" + ruta + "' ";
        Cursor cursorActivos = objutil.ContarEstado(where);
        where = "where 1=1 ";
        where = "where 1=1 and (uriformulario <> '' or ESTADOAGGREGATE =='D') ";
        where = where + " and  rutaaggregate ='" + ruta + "' ";
        Cursor cursorFinalizados = objutil.ContarEstado(where);


        Cursor cursorin;
        cursorin = new InstancesDao().getUnsentInstancesCursorSave("","");
        finalizadosE=cursorin.getCount();

        Cursor cursorfin;
        cursorfin = new InstancesDao().getUnsentInstancesCursorfnish("","");
        estadoFinalizado=cursorfin.getCount();

        if (cursorActivos.moveToFirst()) {

            do {
                activosE=cursorActivos.getInt(0);
            } while (cursorActivos.moveToNext());
        }
        mapaC=activosE+finalizadosE;
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Fragment activo =new Activos();
            activo.setArguments(args);
            return activo;
        } else if (position==1){
            EstadosFormularios finalizados =new EstadosFormularios();
            finalizados.setArguments(args);
            return finalizados;
        } else if (position==2  ){
            EstadosFinalizados finalizados =new EstadosFinalizados();
            finalizados.setArguments(args);
            return finalizados;
        }
        else
        {
            Fragment mapa =new mapa();
            return mapa;
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Pendientes("+ activosE+")";
            case 1:
                return "Incompletos("+ finalizadosE+")";
            case 2:
                return "Enviados("+ estadoFinalizado+")";
            case 3:
                return "Ubicación("+ mapaC+")";

            default:
                return null;
        }
    }

}
