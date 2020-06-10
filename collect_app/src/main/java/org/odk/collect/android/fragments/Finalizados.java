package org.odk.collect.android.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.Engine_util;
import org.odk.collect.android.activities.FormChooserList;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.Branch;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CodigoSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.FiltrosBusqueda;
import org.odk.collect.android.logic.AdapterItem;
import org.odk.collect.android.logic.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador1 on 16/3/2018.
 */

public class Finalizados extends Fragment {
    final BranchSession objBranchSeccion = new BranchSession();
    Engine_util objutil;
    final CodigoSession objcodigoSession = new CodigoSession();
    public Finalizados() {

    }
    public static Finalizados newInstance() {
        return new Finalizados();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ListView lv;
    ArrayList<Category> category = new ArrayList<Category>();
    String CodigoLocal;
    final ConfiguracionSession objconfiguracionSession = new ConfiguracionSession();
    final FiltrosBusqueda objFiltrosBusqueda= new FiltrosBusqueda();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_terminados, container, false);
        objutil= new Engine_util();
        lv = (ListView) rootView.findViewById(R.id.listafinalizados);
        String CodigoRuta = objFiltrosBusqueda.getF_Ruta();
        CodigoLocal= objFiltrosBusqueda.getF_codigo();
        if(CodigoLocal.equals(""))
            buscarlocalesruta(CodigoRuta, objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
        else
            buscarlocalesrutaCodigo(CodigoRuta,objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                float espaciolibre=objutil.freeMemory();
                if(espaciolibre > 512) {
                    if (CodigoRuta != "") {
                        Category currentLead = (Category) parent.getItemAtPosition(position);
                        String where = "where 1=1 ";
                        where = where + " and idbranch ='" + currentLead.getCategoryId() + "' ";
                        Cursor objseleccionado = objutil.SeleccionarLocal(where);


                        objBranchSeccion.setE_ID("");
                        objBranchSeccion.setE_idbranch("");
                        objBranchSeccion.setE_idAccount("");
                        objBranchSeccion.setE_externalCode("");
                        objBranchSeccion.setE_code("");
                        objBranchSeccion.setE_neighborhood("");
                        objBranchSeccion.setE_mainStreet("");
                        objBranchSeccion.setE_reference("");
                        objBranchSeccion.setE_propietario("");
                        objBranchSeccion.setE_uriformulario("");
                        objBranchSeccion.setE_idprovince("");
                        objBranchSeccion.setE_iddistrict("");
                        objBranchSeccion.setE_idParish("");
                        objBranchSeccion.setE_rutaaggregate("0");
                        objBranchSeccion.setE_imeI_ID("");
                        objBranchSeccion.setE_nuevo("");
                        objBranchSeccion.setE_name("");

                        objBranchSeccion.setE_TypeBusiness("");
                        objBranchSeccion.setE_Cedula("");
                        objBranchSeccion.setE_Phone("");

                        objcodigoSession.setcId("");
                        objcodigoSession.setC_idAccount("");
                        objcodigoSession.setC_code("");
                        objcodigoSession.setC_estado("");
                        objcodigoSession.setC_uri("");
                        objcodigoSession.setC_imei_id("");


                        if (objseleccionado.moveToFirst()) {
                            do {
                                if(objseleccionado.getString(18)!=null && objseleccionado.getString(23)!=null) {
                                    if (objseleccionado.getString(18).toString().equals("cerrado") && !objseleccionado.getString(23).toString().equals("D")) {
                                        objBranchSeccion.setE_ID(objseleccionado.getString(0));
                                        objBranchSeccion.setE_idbranch(objseleccionado.getString(1));
                                        objBranchSeccion.setE_idAccount(objseleccionado.getString(2));
                                        objBranchSeccion.setE_externalCode(objseleccionado.getString(3));
                                        objBranchSeccion.setE_code(objseleccionado.getString(4));
                                        objBranchSeccion.setE_name(objseleccionado.getString(5));
                                        objBranchSeccion.setE_neighborhood(objseleccionado.getString(7));
                                        objBranchSeccion.setE_mainStreet(objseleccionado.getString(6));
                                        objBranchSeccion.setE_reference(objseleccionado.getString(8));
                                        objBranchSeccion.setE_propietario(objseleccionado.getString(9));
                                        objBranchSeccion.setE_uriformulario(objseleccionado.getString(10));
                                        objBranchSeccion.setE_idprovince(objseleccionado.getString(11));
                                        objBranchSeccion.setE_iddistrict(objseleccionado.getString(12));
                                        objBranchSeccion.setE_idParish(objseleccionado.getString(13));
                                        objBranchSeccion.setE_rutaaggregate(String.valueOf(CodigoRuta));
                                        objBranchSeccion.setE_imeI_ID(objseleccionado.getString(15));
                                        objBranchSeccion.setE_TypeBusiness(objseleccionado.getString(21));
                                        objBranchSeccion.setE_Cedula(objseleccionado.getString(22));
                                        objBranchSeccion.setE_nuevo("");
                                        objBranchSeccion.setE_Colabora("");
                                        objBranchSeccion.setE_Phone(objseleccionado.getString(20));

                                        Toast.makeText(getActivity(),
                                                "Iniciar Tarea  para: \n" + objseleccionado.getString(5),
                                                Toast.LENGTH_SHORT).show();
                                /*Intent i = new Intent(getActivity(), FormChooserList.class);
                                startActivity(i);*/
                                        startActivity(new Intent(getActivity().getApplication(), FormChooserList.class)
                                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                    } else {
                                        Toast.makeText(getActivity(),
                                                "Solo se puede crear otro formulario si esta cerrado..!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } while (objseleccionado.moveToNext());
                        }

                    } else {
                        Toast.makeText(getActivity(),
                                "Seleccione Ruta", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    final android.app.AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new android.app.AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new android.app.AlertDialog.Builder(view.getContext());
                    }
                    builder.setTitle("Alerta");
                    builder.setMessage("¡Espacio insuficiente para guardar información!");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();
                }
            }
        });
        return rootView;

    }
    public void buscarlocalesruta(String ruta, String formabusqueda) {
        category=new ArrayList<>();
        /*************************************** Validacion solo para censo*************************/
        if (ruta == "") {
            Toast.makeText(getActivity(),
                    "Seleccione Ruta", Toast.LENGTH_SHORT).show();
        } else {
            String opcion = "";
            String[] args = new String[]{};
            String where = "where 1=1 ";

            if (formabusqueda.toString().trim().equals("C")) {
                where = "where 1=1 and (uriformulario <> '' or ESTADOAGGREGATE =='D')   ";
                where = where + " and  rutaaggregate ='" + ruta + "' ";
            }
            if (formabusqueda.toString().trim().equals("N")) {
                where = "where 1=1 and (uriformulario <> '' or ESTADOAGGREGATE =='D') ";
                where = where + " and rutaaggregate ='" + ruta + "' ";
            }

            Cursor cursor = objutil.ListarTareas(args, opcion, where);
            Branch objBranch = new Branch();
            ArrayList<String> listar = new ArrayList<String>();
            List<Branch> listOBJ = new ArrayList<Branch>();
            if (cursor.moveToFirst()) {

                do {
                    listar.add(cursor.getString(4) + " " + cursor.getString(5));
                    objBranch = new Branch();
                    objBranch.setID(cursor.getString(0));
                    objBranch.setIdbranch(cursor.getString(1));
                    objBranch.setIdAccount(cursor.getString(2));
                    objBranch.setExternalCode(cursor.getString(3));
                    objBranch.setCode(cursor.getString(4));
                    objBranch.setName(cursor.getString(5));
                    objBranch.setMainStreet(cursor.getString(6));
                    objBranch.setNeighborhood(cursor.getString(7));
                    objBranch.setReference(cursor.getString(8));
                    objBranch.setPropietario(cursor.getString(9));
                    objBranch.setUriformulario(cursor.getString(10));
                    objBranch.setIdprovince(cursor.getString(11));
                    objBranch.setIddistrict(cursor.getString(12));
                    objBranch.setIdParish(cursor.getString(13));
                    objBranch.setRutaaggregate(cursor.getString(14));
                    objBranch.setImeI_ID(cursor.getString(15));
                    objBranch.setTypeBusiness(cursor.getString(21));
                    objBranch.setESTADOAGGREGATE(cursor.getString(23));
                    listOBJ.add(objBranch);


                    if(cursor.getString(18)!=null) {
                        if (cursor.getString(18).toString().equals("nuevo") ) {
                            category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.azul)));
                        } else if (cursor.getString(18).toString().equals("cerrado")) {
                            category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.naranja)));
                        } else if (cursor.getString(23)!=null) {
                            if (cursor.getString(23).toString().equals("D")){
                                category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.morado)));
                            }else{
                                category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.verde)));
                            }
                        } else {
                            category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.verde)));
                        }

                    }else {
                        if (cursor.getString(23).toString().equals("D")) {

                            category.add(new Category(cursor.getString(1), cursor.getString(4), cursor.getString(5), cursor.getString(20), getResources().getDrawable(R.drawable.morado)));
                        }
                    }


                } while (cursor.moveToNext());
            }

            AdapterItem adapter = new AdapterItem(this.getActivity(), category);
            lv.setAdapter(adapter);
        }
    }
    public void buscarlocalesrutaCodigo(String ruta, String formabusqueda) {
        category=new ArrayList<>();
        if (CodigoLocal.equals("")) {
            Toast.makeText(getActivity(),
                    "Ingrese Datos", Toast.LENGTH_SHORT).show();
        } else {
            /*************************************** Validacion solo para censo*************************/
            if (ruta == "") {
                Toast.makeText(getActivity(),
                        "Seleccione Ruta", Toast.LENGTH_SHORT).show();
            } else {
                String opcion = "";
                String[] args = new String[]{};
                String where = "where 1=1 ";

                if (formabusqueda.equalsIgnoreCase("C")) {
                    opcion = "c";
                    args = new String[]{CodigoLocal.toUpperCase() + "%"};
                    //*********************************************where solo para censo
                    where = "where 1=1 and (uriformulario <> '' or ESTADOAGGREGATE =='D')  ";
                    where = where + " and  rutaaggregate ='" + ruta + "' ";
                    //*********************************************
                    where = where + "and code = '" + CodigoLocal.toUpperCase() + "'";
                }
                if (formabusqueda.equalsIgnoreCase("N")) {
                    opcion = "n";
                    args = new String[]{CodigoLocal.toUpperCase() + "%"};
                    //*********************************************where solo para censo
                    where = "where 1=1 and (uriformulario <> '' or ESTADOAGGREGATE =='D')   ";
                    where = where + " and rutaaggregate ='" + ruta + "' ";
                    //*********************************************
                    where = where + "and name like '%" + CodigoLocal.toUpperCase() + "%'";
                }
                Cursor cursor = objutil.ListarTareas(args, opcion, where);

                Branch objBranch = new Branch();
                ArrayList<String> listar = new ArrayList<String>();
                List<Branch> listOBJ = new ArrayList<Branch>();
                if (cursor.moveToFirst()) {

                    do {
                        listar.add(cursor.getString(4) + " " + cursor.getString(5));
                        objBranch = new Branch();
                        objBranch.setID(cursor.getString(0));
                        objBranch.setIdbranch(cursor.getString(1));
                        objBranch.setIdAccount(cursor.getString(2));
                        objBranch.setExternalCode(cursor.getString(3));
                        objBranch.setCode(cursor.getString(4));
                        objBranch.setName(cursor.getString(5));
                        objBranch.setMainStreet(cursor.getString(6));
                        objBranch.setNeighborhood(cursor.getString(7));
                        objBranch.setReference(cursor.getString(8));
                        objBranch.setPropietario(cursor.getString(9));
                        objBranch.setUriformulario(cursor.getString(10));
                        objBranch.setIdprovince(cursor.getString(11));
                        objBranch.setIddistrict(cursor.getString(12));
                        objBranch.setIdParish(cursor.getString(13));
                        objBranch.setRutaaggregate(cursor.getString(14));
                        objBranch.setImeI_ID(cursor.getString(15));
                        objBranch.setTypeBusiness(cursor.getString(21));
                        listOBJ.add(objBranch);
                        category.add(new Category(cursor.getString(1),cursor.getString(4),cursor.getString(5),cursor.getString(20),getResources().getDrawable(R.drawable.ok)));

                    } while (cursor.moveToNext());
                }
                AdapterItem adapter = new AdapterItem(getActivity(), category);

                lv.setAdapter(adapter);

            }
        }

    }
}
