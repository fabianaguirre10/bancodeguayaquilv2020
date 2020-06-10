package org.odk.collect.android.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

import org.odk.collect.android.R;
import org.odk.collect.android.adapters.InstanceListCursorAdapter;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.dao.InstancesDao;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.FiltrosBusqueda;
import org.odk.collect.android.provider.InstanceProviderAPI;

public class EstadosFinalizados extends Fragment {
    private static final String INSTANCE_LIST_ACTIVITY_SORTING_ORDER = "instanceListActivitySortingOrder";
    private static final String VIEW_SENT_FORM_SORTING_ORDER = "ViewSentFormSortingOrder";

    private static final boolean EXIT = true;
    public EstadosFinalizados() {

    }
    public static EstadosFinalizados newInstance() {
        return new EstadosFinalizados();
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

        return rootView;

    }

    /**
     * Stores the path of selected instance in the parent class and finishes.
     */




    private Cursor getCursor() {
        Cursor cursor;
        cursor = new InstancesDao().getUnsentInstancesCursorfnish(CodigoLocal, "displayName COLLATE NOCASE ASC, status DESC");
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

