package org.odk.collect.android.fragments;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.database.BaseDatosEngine.BaseDatosEngine;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CodigoSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.EstadoEditar;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.EstadosFormulario;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.FiltrosBusqueda;
import org.odk.collect.android.provider.InstanceProviderAPI;
import org.odk.collect.android.utilities.ApplicationConstants;

import java.util.ArrayList;
import java.util.List;

public class EstadosFormularios extends Fragment {
    private static final String INSTANCE_LIST_ACTIVITY_SORTING_ORDER = "instanceListActivitySortingOrder";
    private static final String VIEW_SENT_FORM_SORTING_ORDER = "ViewSentFormSortingOrder";

    private static final boolean EXIT = true;
    public EstadosFormularios() {

    }
    public static EstadosFormularios newInstance() {
        return new EstadosFormularios();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
     SimpleCursorAdapter listAdapter;

     Toolbar toolbar;
     ListView listView;
     String CodigoLocal="";
    final FiltrosBusqueda objFiltrosBusqueda= new FiltrosBusqueda();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public List<EstadosFormulario> consultaformularios(String uri){
        BaseDatosEngine usdbh = new BaseDatosEngine();
        usdbh = usdbh.open();
        ArrayList<EstadosFormulario> listaEstadosFormularios= new ArrayList<>();
        EstadosFormulario dato = null;

        Cursor listardatos=usdbh.ResumenDatos("select * from EstadoFormulario where code='"+uri+"'");
        usdbh.close();
        if(listardatos!=null) {
            if (listardatos.moveToFirst()) {
                do {
                    dato= new EstadosFormulario();
                    dato.setID(listardatos.getInt(0));
                    dato.setIdAccount(listardatos.getString(1));
                    dato.setIdCampania(listardatos.getString(2));
                    dato.setCode(listardatos.getString(3));
                    dato.setRuta(listardatos.getString(4));
                    dato.setEstadovisita(listardatos.getString(5));
                    dato.setEstadointeres(listardatos.getString(6));
                    dato.setNombrelocal(listardatos.getString(7));
                    dato.setDireccion(listardatos.getString(8));
                    dato.setReferencia(listardatos.getString(9));
                    dato.setBarrio(listardatos.getString(10));
                    dato.setLatitud(listardatos.getString(11));
                    dato.setLongitud(listardatos.getString(12));
                    dato.setNombrepro(listardatos.getString(13));
                    dato.setApellidopropi(listardatos.getString(14));
                    dato.setTelefono(listardatos.getString(15));
                    dato.setCelular(listardatos.getString(16));
                    dato.setFecha(listardatos.getString(17));
                    dato.setEstadoenvio(listardatos.getString(18));
                    dato.setUri(listardatos.getString(19));
                    dato.setImei(listardatos.getString(20));
                    dato.setCedula(listardatos.getString(21));
                    listaEstadosFormularios.add(dato);
                } while (listardatos.moveToNext());
            }
        }
        return listaEstadosFormularios;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // must be at the beginning of any activity that can be called from an external intent
        try {
            Collect.createODKDirs();
        } catch (RuntimeException e) {

        }
        View rootView = inflater.inflate(R.layout.activity_chooser_list_status, container, false);

        listView = (ListView) rootView.findViewById(R.id.idlista);

        String CodigoRuta = objFiltrosBusqueda.getF_Ruta();
        CodigoLocal= objFiltrosBusqueda.getF_codigo();

        setupAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                if (Collect.allowClick(getClass().getName())) {
                    EstadoEditar ed= new EstadoEditar();
                    ed.setEstadoEdit("2");
                    BranchSession objBranchSessio=new BranchSession();
                    objBranchSessio.setE_code("");
                    objBranchSessio.setE_name("");
                    objBranchSessio.setE_externalCode("");
                    objBranchSessio.setE_ID("");
                    objBranchSessio.setE_idAccount("");
                    objBranchSessio.setE_rutaaggregate("");
                    objBranchSessio.setE_TypeBusiness("");
                    objBranchSessio.setE_Cedula("");
                    objBranchSessio.setE_Phone("");
                    CodigoSession codse= new CodigoSession();
                    codse.setC_code("");
                    codse.setcId("");
                    Cursor c = (Cursor) listView.getAdapter().getItem(position);
                    String codigolocal;
                    codigolocal = (String) ((CrossProcessCursorWrapper) parent.getItemAtPosition(position)).getString((c.getColumnIndex(InstanceProviderAPI.InstanceColumns.DISPLAY_NAME)));
                    //startManagingCursor(c);
                    List<EstadosFormulario> busqueda= new ArrayList<>();
                    busqueda=consultaformularios(codigolocal);
                    for(EstadosFormulario x :busqueda){
                        objBranchSessio.setE_code(x.code);
                        objBranchSessio.setE_name(x.nombrelocal);
                        objBranchSessio.setE_mainStreet(x.direccion);
                        objBranchSessio.setE_reference(x.direccion);
                        objBranchSessio.setE_rutaaggregate(x.nombrepro);
                        objBranchSessio.setE_Cedula(x.getCedula());

                    }
                    Uri instanceUri =
                            ContentUris.withAppendedId(InstanceProviderAPI.InstanceColumns.CONTENT_URI,
                                    c.getLong(c.getColumnIndex(InstanceProviderAPI.InstanceColumns._ID)));

                    Intent parentIntent = getActivity().getIntent();
                    Intent intent = new Intent(Intent.ACTION_EDIT, instanceUri);

                    intent.putExtra(ApplicationConstants.BundleKeys.FORM_MODE, ApplicationConstants.FormModes.EDIT_SAVED);
                            startActivity(intent);
                }

            }
        });
        return rootView;

    }

    /**
     * Stores the path of selected instance in the parent class and finishes.
     */




    private Cursor getCursor() {
        Cursor cursor;
        cursor = new InstancesDao().getUnsentInstancesCursorSave(CodigoLocal, "displayName COLLATE NOCASE ASC, status DESC");
        return cursor;
    }

    private void setupAdapter() {
        String[] data = new String[]{
                InstanceProviderAPI.InstanceColumns.DISPLAY_NAME, InstanceProviderAPI.InstanceColumns.DISPLAY_SUBTEXT, InstanceProviderAPI.InstanceColumns.DELETED_DATE
        };
        int[] view = new int[]{
                R.id.text1, R.id.text2, R.id.text4
        };
        listAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.two_item, getCursor(), data, view);
        listView.setAdapter(listAdapter);
    }









}

