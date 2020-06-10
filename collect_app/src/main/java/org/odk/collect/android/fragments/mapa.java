package org.odk.collect.android.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.odk.collect.android.activities.Engine_util;
import org.odk.collect.android.activities.FormChooserList;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CodigoSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.FiltrosBusqueda;
import  org.odk.collect.android.activities.informacionpuntoapp;

/**
 * Created by Administrador1 on 14/3/2018.
 */

public class mapa extends SupportMapFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    public mapa() {
    }
    final FiltrosBusqueda objFiltrosBusqueda= new FiltrosBusqueda();
    private GoogleMap mMap;
    final BranchSession objBranchSeccion = new BranchSession();
    Engine_util objutil;
    final CodigoSession objcodigoSession = new CodigoSession();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        objutil= new Engine_util();
        getMapAsync(this);

        return rootView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        // Posicionar el mapa en una localización y con un nivel de zoom
        mMap = map;
        mMap.clear();
        // Un zoom mayor que 13 hace que el emulador falle, pero un valor deseado para
        // callejero es 17 aprox.
        String opcion = "";
        String[] args = new String[]{};
        String where = "where 1=1 ";
        if(objFiltrosBusqueda.getF_codigo().equals("")) {
            where = where + " and rutaaggregate ='" + objFiltrosBusqueda.getF_Ruta() + "' ";
        }else {
            where = where + " and code ='" + objFiltrosBusqueda.getF_codigo().toUpperCase() + "' and   rutaaggregate ='" + objFiltrosBusqueda.getF_Ruta() + "'";
        }
        Cursor cursor = objutil.ListarTareas(args, opcion, where);
        if (cursor.moveToFirst()) {

            do {

                if(cursor.getString(16).toString().equals("0")==false && cursor.getString(16).toString().equals(" ")==false && cursor.getString(16).toString().equals(null)==false ) {
                    double latitud = Double.valueOf(cursor.getString(16).toString().replace(',', '.'));
                    double Longitud = Double.valueOf(cursor.getString(17).toString().replace(',', '.'));
                    LatLng latLng = new LatLng(latitud, Longitud);
                    float zoom = 13;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                    MarkerOptions Opc = new MarkerOptions();
                    Opc.position(latLng);
                    Opc.title(cursor.getString(4));
                    if(cursor.getString(23).toString().equals("S"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    if(cursor.getString(23).toString().equals("E"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    if(cursor.getString(23).toString().equals("N"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    if(cursor.getString(23).toString().equals("C"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    /*if(cursor.getString(23).toString().equals("D"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));*/
                    if(cursor.getString(23).toString().equals("B"))
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                    if(cursor.getString(10).toString().equals("")==false )
                        Opc.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


                    mMap.addMarker(Opc);
                }
            } while (cursor.moveToNext());

        }

        // Colocar un marcador en la misma posición

        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Toast.makeText(
                        getActivity(),
                        "Punto:"+ marker.getTitle()+" \n" +
                                "Lat: " + marker.getPosition().latitude + "\n" +
                                "Lng: " + marker.getPosition().longitude + "\n" ,
                        Toast.LENGTH_SHORT).show();
                String where = "where 1=1 ";
                where = where + " and code ='" + marker.getTitle() + "' and   rutaaggregate ='" + objFiltrosBusqueda.getF_Ruta() + "'";
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
                objBranchSeccion.setE_TypeBusiness("");
                objBranchSeccion.setE_nuevo("");
                objBranchSeccion.setE_name("");
                objcodigoSession.setcId("");
                objcodigoSession.setC_idAccount("");
                objcodigoSession.setC_code("");
                objcodigoSession.setC_estado("");
                objcodigoSession.setC_uri("");
                objcodigoSession.setC_imei_id("");
                objBranchSeccion.setE_name("");
                objBranchSeccion.setE_TypeBusiness("");
                objBranchSeccion.setE_Phone("");
                objBranchSeccion.setE_Cedula("");




                if (objseleccionado.moveToFirst()) {
                    do {
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
                        objBranchSeccion.setE_rutaaggregate(String.valueOf(objFiltrosBusqueda.getF_Ruta()));
                        objBranchSeccion.setE_imeI_ID(objseleccionado.getString(15));
                        objBranchSeccion.setE_TypeBusiness(objseleccionado.getString(21));
                        objBranchSeccion.setE_nuevo("");

                        objBranchSeccion.setE_nuevo("");
                        objBranchSeccion.setE_Colabora("");
                        objBranchSeccion.setE_TypeBusiness(objseleccionado.getString(21));
                        objBranchSeccion.setE_Cedula(objseleccionado.getString(22));
                        objBranchSeccion.setE_Phone(objseleccionado.getString(20));
                        objBranchSeccion.setE_LatitudeBranch(objseleccionado.getString(16).toString());
                        objBranchSeccion.setE_LenghtBranch(objseleccionado.getString(17).toString());
                        objBranchSeccion.setE_fotoexterior(objseleccionado.getString(24).toString());
                        objBranchSeccion.setE_isclient(objseleccionado.getString(25).toString());
                        objBranchSeccion.setE_nombregrabacion("");

                        final android.app.AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new android.app.AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new android.app.AlertDialog.Builder(getActivity());
                        }
                        builder.setTitle("Iniciar Tarea");
                        builder.setMessage(objseleccionado.getString(5));
                        builder.setPositiveButton("Formulario", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity().getApplication(), FormChooserList.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            }
                        });
                        builder.setNegativeButton("Información", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //onMapReady(map);

                                startActivity(new Intent(getActivity().getApplication(), informacionpuntoapp.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert);
                        builder.show();




                    } while (objseleccionado.moveToNext());
                }







                return true;
            }
        });



        // Más opciones para el marcador en:
        // https://developers.google.com/maps/documentation/android/marker

        // Otras configuraciones pueden realizarse a través de UiSettings
        // UiSettings settings = getMap().getUiSettings();
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);




            Toast.makeText(this.getActivity(),
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occteur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

}