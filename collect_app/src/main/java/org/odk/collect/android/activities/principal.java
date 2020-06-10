package org.odk.collect.android.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.database.BaseDatosEngine.BaseDatosEngine;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CodigoSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CuentaSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.EstadoEditar;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.EstadoFormularioSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.FiltrosBusqueda;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD;
import org.odk.collect.android.logic.Category;
import org.odk.collect.android.preferences.AdminKeys;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.PreferencesActivity;
import org.odk.collect.android.utilities.SimpleFragmentPagerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class principal extends AppCompatActivity {
    private static final int PASSWORD_DIALOG = 1;
    private static final int CODIGO_SOLICITUD_PERMISOS = 1;
    final ConfiguracionSession objconfiguracionSession = new ConfiguracionSession();
    final BranchSession objBranchSeccion = new BranchSession();
    final CodigoSession objcodigoSession = new CodigoSession();
    final CuentaSession objcuentaSession = new CuentaSession();
    final FiltrosBusqueda objFiltrosBusqueda = new FiltrosBusqueda();
    Engine_util objutil;
    TextView txtnombrecampania;
    Spinner cmbnumeroruta;
    EstadoFormularioSession objseccion = new EstadoFormularioSession();
    EditText txtbuscar;
    private SharedPreferences adminPreferences;
    ProgressDialog progress;
    JSONArray respJSON = new JSONArray();
    ArrayList<Category> category = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*Solicitud de permisos de aplicación
        */

        if(!ChecarStatusPermisos()){
            Solicitarpermiso();
        }

        txtnombrecampania = (TextView) findViewById(R.id.txnombrecampania);
        objseccion.setE_imei(obterImeid());
        cmbnumeroruta = (Spinner) findViewById(R.id.cmbnumeroruta);
        txtbuscar = (EditText) findViewById(R.id.txtcodbusqueda);

        EstadoEditar ed = new EstadoEditar();
        ed.setEstadoEdit("1");
        objutil = new Engine_util();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        adminPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objcuentaSession.getCu_ID() != "") {
                    final android.app.AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new android.app.AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new android.app.AlertDialog.Builder(view.getContext());
                    }
                    builder.setTitle("Código de local nuevo");
                    EditText  et = new EditText(builder.getContext());
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    String etStr = et.getText().toString();

                    builder.setView(et);
                    builder.setMessage("Ingrese número teléfono del contacto..!!!");
                    builder.setPositiveButton("Formulario", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                                CargarNuevos();
                                if (objcodigoSession.getcId() != "") {
                                    objBranchSeccion.setE_nuevo("nuevo");
                                    objBranchSeccion.setE_mainStreet("");
                                    objBranchSeccion.setE_TypeBusiness("");
                                    objBranchSeccion.setE_name("");
                                    objBranchSeccion.setE_reference("");
                                    objBranchSeccion.setE_Phone("");
                                    BaseDatosEngine usdbh = new BaseDatosEngine();
                                    if (objBranchSeccion.getE_code().equals("") == false) {
                                        usdbh = usdbh.open();
                                        ContentValues Objdatosnuevos = new ContentValues();
                                        Objdatosnuevos.put(EstructuraBD.CabecerasCodigos.codeunico, objBranchSeccion.getE_code());
                                        Objdatosnuevos.put(EstructuraBD.CabecerasCodigos.uri, "ocupado");
                                        String where = "codeunico='" + objBranchSeccion.getE_code() + "'";
                                        usdbh.ActualizarTablaCodigos(Objdatosnuevos, where);
                                    }
                                        objBranchSeccion.setE_Phone(etStr);

                                    Intent intent = new Intent(getApplication(), FormChooserList.class);
                                    startActivityForResult(intent, 0);
                                } else {
                                ConnectivityManager connMgr = (ConnectivityManager)
                                        getSystemService(getApplication().CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                if (networkInfo != null && networkInfo.isConnected()) {
                                    // DatosConsulta();
                                    CargarCodigosNuvos fetchJsonTask = new CargarCodigosNuvos(builder.getContext());
                                    fetchJsonTask.execute();
                                    //fetchJsonTask.getStatus();

                                } else {
                                    Toast.makeText(getApplication(),
                                            "Sin Conexión a la red seleccione Digitar Código", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                    });
                    builder.setNegativeButton("LLamar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
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
                            objBranchSeccion.setE_nuevo("nuevo");

                            objcodigoSession.setcId("");
                            objcodigoSession.setC_idAccount("");
                            objcodigoSession.setC_code("");
                            objcodigoSession.setC_estado("");
                            objcodigoSession.setC_uri("");
                            objcodigoSession.setC_imei_id("");
                           /* Intent intent = new Intent(getApplication(), FormChooserList.class);
                            startActivityForResult(intent, 0);*/

                            String etStr = et.getText().toString();
                            if(etStr.length()==10){
                                objBranchSeccion.setE_Phone(etStr);
                                Intent i = new Intent(Intent.ACTION_DIAL);
                                i.setData(Uri.parse("tel:"+etStr)); //+ objBranchSeccion.getE_Phone()));
                                startActivity(i);

                            }else{
                                Toast.makeText(principal.this, "Número de contacto incorrecto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNeutralButton("WhatsApp", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String etStr = et.getText().toString();
                            BranchSession objBranchSeccion = new BranchSession();
                            objBranchSeccion.setE_Phone(etStr);
                            informacionpuntoapp inf= new informacionpuntoapp();
                            switch(objBranchSeccion.getE_Phone().toString().length()){
                                case 10:
                                    enviarwhasap(objBranchSeccion.getE_Phone()) ;
                                    break;
                                case 9:
                                    enviarwhasap("0"+objBranchSeccion.getE_Phone()) ;
                                    break;
                                default:
                                    Toast.makeText(builder.getContext() , "Número de Teléfono invalido para uso de WHATSAAP!!!!", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });


                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();


                } else {
                    Toast.makeText(getApplication(),
                            "Seleccione una cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btnbuscartareacodigo = (Button) findViewById(R.id.btnbuscartareacodigo);
        Button btninforme = (Button) findViewById(R.id.btninforme);
        if (objconfiguracionSession.getCnf_CampaniaNombre() == null) {
            objutil.CargarConfiguracion();
            if (objconfiguracionSession.getCnf_CampaniaNombre() == null) {
                Toast.makeText(getApplication(),
                        "Configurar Cuenta..!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, configuracion.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                txtnombrecampania.setText(objconfiguracionSession.getCnf_CampaniaNombre().toString());
                ListarRutasEngine();

            }
        } else {
            txtnombrecampania.setText(objconfiguracionSession.getCnf_CampaniaNombre().toString());
            ListarRutasEngine();
        }

        cmbnumeroruta.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        String valor = (String) parent.getItemAtPosition(position);

                        //buscarlocalesruta(valor, objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());

                        //llama fragmento para cargar pendientes
                        objFiltrosBusqueda.setF_codigo("");
                        objFiltrosBusqueda.setF_Ruta(valor);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
                        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getApplication().getApplicationContext(), getSupportFragmentManager(), valor, "");
                        viewPager.setAdapter(adapter);
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(viewPager);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        btninforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cmbnumeroruta.getSelectedItem().toString().equals("") == false) {


                }
            }

        });

        btnbuscartareacodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtbuscar.getText().toString().equals("") == false) {
                    //buscarlocalesrutaCodigo(cmbnumeroruta.getSelectedItem().toString(), objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
                    objFiltrosBusqueda.setF_codigo(txtbuscar.getText().toString());
                    objFiltrosBusqueda.setF_Ruta(cmbnumeroruta.getSelectedItem().toString());
                    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
                    SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getApplication().getApplicationContext(), getSupportFragmentManager(), cmbnumeroruta.getSelectedItem().toString(), txtbuscar.getText().toString());
                    viewPager.setAdapter(adapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);
                } else {
                    objFiltrosBusqueda.setF_codigo("");
                    objFiltrosBusqueda.setF_Ruta(cmbnumeroruta.getSelectedItem().toString());
                    // buscarlocalesruta(cmbnumeroruta.getSelectedItem().toString(), objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
                    ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
                    SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getApplication().getApplicationContext(), getSupportFragmentManager(), cmbnumeroruta.getSelectedItem().toString(), "");
                    viewPager.setAdapter(adapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);
                }
            }

        });

        String CodigoLocal = getIntent().getStringExtra("codigolocal");
        if(CodigoLocal!=null) {
            if(buscarCodigo(CodigoLocal)) {

                Intent i = new Intent(this.getApplication(), informacionpuntoapp.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("telefono", objBranchSeccion.getE_Phone());
                startActivity(i);


            }
        }
    }
    public void  enviarwhasap(String nuemrotelefono){
        BranchSession objBranchSeccion = new BranchSession();
        PackageManager packageManager = this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String url="";
        try {
            if(objBranchSeccion.getE_isclient().equals("BGSuperName") || objBranchSeccion.getE_isclient().equals("") ) {
                url = "https://api.whatsapp.com/send?phone=" + "593" + nuemrotelefono+ "&text=" + URLEncoder.encode("Hola, buen día... te saluda, tu gestor de Banco del Barrio. \n" +
                        "Queremos conversar sobre los beneficios que tenemos para tu negocio", "utf-8");
            }else{
                url = "https://api.whatsapp.com/send?phone=" + "593" + nuemrotelefono + "&text=" + URLEncoder.encode("Hola, buen día... te saluda *"+objBranchSeccion.getE_isclient().toUpperCase()+"*, tu gestor  de Banco del Barrio. \n" +
                        "Queremos conversar sobre los beneficios que tenemos para tu negocio.", "utf-8");
            }
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                this.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void  Solicitarpermiso(){
        //read call log
        //write call log
        boolean solicitarpermisoRCL = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG);
        boolean solicitarpermisoWCL = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CALL_LOG);

        boolean permiso1 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permiso2 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean permiso3 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean permiso4 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean permiso5 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA);
        boolean permiso6 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE);
        boolean permiso7 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
        boolean permiso8 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS);
        boolean permiso9 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS);
        boolean permiso10 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET);
        boolean permiso11 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE);
        boolean permiso12 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE);
        boolean permiso13 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK);
        boolean permiso14 = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);



        if(solicitarpermisoRCL && solicitarpermisoWCL && permiso1
                && permiso2 && permiso3 && permiso4 && permiso5 && permiso6 && permiso7  && permiso9 && permiso10 && permiso11 && permiso12 && permiso13 ) {

            Toast.makeText(this, "Los permisos fueron otorgados", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG
                    ,Manifest.permission.WRITE_CALL_LOG
                    ,Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.CAMERA
                    ,Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.RECORD_AUDIO
                    ,Manifest.permission.READ_CONTACTS
                    ,Manifest.permission.SEND_SMS
                    ,Manifest.permission.INTERNET
                    ,Manifest.permission.ACCESS_WIFI_STATE
                    ,Manifest.permission.ACCESS_NETWORK_STATE
                    ,Manifest.permission.WAKE_LOCK
            },CODIGO_SOLICITUD_PERMISOS);
        }


    }
    public Boolean ChecarStatusPermisos(){
        boolean permisosRCL=ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)== PackageManager.PERMISSION_GRANTED;
        boolean permisosWCL=ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CALL_LOG)== PackageManager.PERMISSION_GRANTED;
        boolean permiso1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
        boolean permiso2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED;
        boolean permiso3 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        boolean permiso4 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        boolean permiso5 = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean permiso6 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED;
        boolean permiso7 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED;
        boolean permiso8 = ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)== PackageManager.PERMISSION_GRANTED;
        boolean permiso9 = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED;
        boolean permiso10 = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED;
        boolean permiso11 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)== PackageManager.PERMISSION_GRANTED;
        boolean permiso12 = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED;
        boolean permiso13 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK)== PackageManager.PERMISSION_GRANTED;
        boolean permiso14 = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED;

        if(permisosRCL && permisosWCL && permiso1
                && permiso2 && permiso3 && permiso4 && permiso5 && permiso6 && permiso7  && permiso9 && permiso10 && permiso11 && permiso12 && permiso13)
            return true;
        else
            return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CODIGO_SOLICITUD_PERMISOS:
                if(ChecarStatusPermisos()){
                    Toast.makeText(this, "Permisos OK", Toast.LENGTH_SHORT).show();
                    // consultarCPLlamadas();
                }else{
                    Toast.makeText(this, "No se activo el permiso", Toast.LENGTH_SHORT).show();
                }
        }

    }
public Boolean buscarCodigo(String Codigo){

    String where = "where 1=1 ";
    where = where + " and code ='" + Codigo + "' and   rutaaggregate ='" + objFiltrosBusqueda.getF_Ruta() + "'";
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

            return true;





        } while (objseleccionado.moveToNext());
    }
    return  false;

}
    public void ListarRutasEngine() {
        category = new ArrayList<>();
        Cursor cursor = objutil.ListarRutas();

        //startManagingCursor(cursor);
        ArrayList<String> listar = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                listar.add(String.valueOf(cursor.getString(0)));
            } while (cursor.moveToNext());
        } else {
            startActivity(new Intent(getBaseContext(), configuracion.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
        }
        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(getApplication(), R.layout.spinner_personalizado, listar);

        cmbnumeroruta.setAdapter(adaptador);
        if (objBranchSeccion != null) {
            int pos = adaptador.getPosition(objBranchSeccion.getE_rutaaggregate());
            if (pos > -1)
                cmbnumeroruta.setSelection(pos);
        }

    }


    public void CargarNuevos() {
        BaseDatosEngine usdbh = new BaseDatosEngine();
        usdbh = usdbh.open();
        Cursor cursor = usdbh.listarCodigois();
        String CodigoNuevo = "";
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
        objBranchSeccion.setE_Phone("");
        objBranchSeccion.setE_Cedula("");

        objBranchSeccion.setE_name("");
        objBranchSeccion.setE_reference("");

        objcodigoSession.setcId("");
        objcodigoSession.setC_idAccount("");
        objcodigoSession.setC_code("");
        objcodigoSession.setC_estado("");
        objcodigoSession.setC_uri("");
        objcodigoSession.setC_imei_id("");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
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
                    objBranchSeccion.setE_Colabora("");
                    objBranchSeccion.setE_TypeBusiness("");
                    objBranchSeccion.setE_Phone("");
                    objBranchSeccion.setE_name("");
                    objBranchSeccion.setE_reference("");
                    objBranchSeccion.setE_Cedula("");
                    Cursor cursor1 = usdbh.CodigoNuevo(cursor.getString(0));
                    if (cursor1.moveToFirst()) {
                        do {
                            objBranchSeccion.setE_code(cursor1.getString(6));
                            objcodigoSession.setcId(cursor1.getString(0));
                            objcodigoSession.setC_idAccount(cursor1.getString(1));
                            objcodigoSession.setC_code(cursor1.getString(2));
                            objcodigoSession.setC_estado(cursor1.getString(3));
                            objcodigoSession.setC_uri(cursor1.getString(4));
                            objcodigoSession.setC_imei_id(cursor1.getString(5));
                            objcodigoSession.setC_codeunico(cursor1.getString(6));
                            objBranchSeccion.setE_rutaaggregate(cmbnumeroruta.getSelectedItem().toString());
                            objBranchSeccion.setE_nuevo("nuevo");


                        } while (cursor.moveToNext());
                    }


                } while (cursor.moveToNext());
            }
        }
        usdbh.close();
    }

    @SuppressLint("MissingPermission")
    public String obterImeid() {

        final String androidIdName = Settings.Secure.ANDROID_ID;
        String myIMEI = Settings.Secure.getString(Collect.getInstance().getApplicationContext().getContentResolver(), androidIdName);
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (mTelephony.getDeviceId() != null) {
                myIMEI = mTelephony.getDeviceId();
            }
        }catch (Exception ex){

        }

        return myIMEI;
    }
    public class CargarCodigosNuvos extends AsyncTask<Void,Void,String> {
        public CargarCodigosNuvos(Context context) {
            this.context = context;
        }
        private ProgressDialog progressDialog;
        private  Context context;
        @Override
        protected void onPreExecute() {
            // set up progress dialog
            try {
                progressDialog = new ProgressDialog(context);
                progressDialog.setIndeterminate(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please wait...");

                // show it
                progressDialog.show();
            }catch (Exception ex){

            }

        }
        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String jsonStr = null;

            try {
                // Construct the URL somehow
                String Idaccount =objcuentaSession.getCu_idAccount();
                String Idcampania="";
                String url1 = "http://geomardis6728.cloudapp.net/msbancoGuayaquil/api/codigos?idAccounut="+Idaccount+"&imail="+obterImeid();
                URL url = new URL(url1);

                // Create the request to MuslimSalat.com, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();

                //JSONArray objetos = new JSONArray(result);
                respJSON = new JSONArray(jsonStr);
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            return jsonStr;
        }
        @Override
        protected void onPostExecute(String jsonString) {

            // dismiss the progress because downloading process is finished.
            progressDialog.dismiss();
            progress=new ProgressDialog(context);
            progress.setMessage("Descargando Codigos....");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);

            progress.setProgress(0);
            progress.setMax(respJSON.length());
            progress.show();
            final int totalProgressTime = respJSON.length();
            final Thread t = new Thread() {
                @Override
                public void run() {
                    int jumpTime = 0;
                    while(jumpTime < totalProgressTime) {
                        try {
                            JSONObject obj = respJSON.getJSONObject(jumpTime);
                            String id = obj.getString("id");
                            String idAccount = obj.getString("idAccount");
                            String code = obj.getString("code");
                            String estado = obj.getString("estado");
                            String uri = "";
                            String imei_id = obj.getString("imei_id");
                            String codeunico=obj.getString("codeunico");
                            BaseDatosEngine usdbh = new BaseDatosEngine();
                            try {

                                usdbh = usdbh.open();
                                ContentValues Objdatos = new ContentValues();
                                Objdatos.put(EstructuraBD.CabecerasCodigos.ID, id);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.idAccount, idAccount);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.code, code.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.estado, estado.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.uri, uri.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.imei_id, imei_id);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.codeunico, codeunico);
                                usdbh.insertardatosCodigos(Objdatos);
                                usdbh.close();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            jumpTime += 1;
                            progress.setProgress(jumpTime);
                            sleep(25);
                        }
                        catch (InterruptedException e) {
                            Log.e("Error Carga",e.getMessage());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    CargarNuevos();
                    if (objcodigoSession.getcId() != "") {
                        BaseDatosEngine usdbh = new BaseDatosEngine();
                        Cursor cursor = usdbh.RutaLista();
                        objBranchSeccion.setE_rutaaggregate("0");
                        if(cursor!=null) {
                            if (cursor.moveToFirst()) {

                                do {
                                    objBranchSeccion.setE_rutaaggregate(cursor.getString(0));
                                    objBranchSeccion.setE_nuevo("nuevo");

                                } while (cursor.moveToNext());


                            }
                        }



                        Intent intent = new Intent(getApplication(), FormChooserList.class);
                        startActivityForResult(intent, 0);
                    } else {
                        //Toast.makeText(getApplication(),
                        //"No se pudo cargar los códigos nuevos", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            };
            t.start();
            try {
                t.join();
                CargarNuevos();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //progress.dismiss();



        }
    }
    @Override
    protected void onStart() {
        txtbuscar.setText("");
        if (txtbuscar.getText().toString().equals("") == false) {
            //buscarlocalesrutaCodigo(cmbnumeroruta.getSelectedItem().toString(), objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
            objFiltrosBusqueda.setF_codigo(txtbuscar.getText().toString());
            objFiltrosBusqueda.setF_Ruta(cmbnumeroruta.getSelectedItem().toString());
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getApplication().getApplicationContext(),getSupportFragmentManager(),cmbnumeroruta.getSelectedItem().toString(),txtbuscar.getText().toString());
            viewPager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }else{
            objFiltrosBusqueda.setF_codigo("");
            objFiltrosBusqueda.setF_Ruta(cmbnumeroruta.getSelectedItem().toString());
            // buscarlocalesruta(cmbnumeroruta.getSelectedItem().toString(), objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
            ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
            SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getApplication().getApplicationContext(),getSupportFragmentManager(),cmbnumeroruta.getSelectedItem().toString(),"");
            viewPager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
        super.onStart();
        //Collect.getInstance().getActivityLogger().logOnStart(this);
    }

    @Override
    protected void onStop() {
        //Collect.getInstance().getActivityLogger().logOnStop(this);
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cuentaconf:

                startActivity(new Intent(getBaseContext(), configuracion.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            case R.id.menu_main:

                startActivity(new Intent(getBaseContext(), MainMenuActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            case R.id.menu_about:

                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.menu_general_preferences:

                Intent ig = new Intent(this, PreferencesActivity.class);
                startActivity(ig);
                return true;
            case R.id.menu_admin_preferences:

                String pw = adminPreferences.getString(AdminKeys.KEY_ADMIN_PW, "");
                if ("".equalsIgnoreCase(pw)) {
                    Intent i = new Intent(getApplicationContext(),
                            AdminPreferencesActivity.class);
                    startActivity(i);
                } else {
                    showDialog(PASSWORD_DIALOG);

                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

        boolean edit = sharedPreferences.getBoolean(
                AdminKeys.KEY_EDIT_SAVED, true);


        boolean send = sharedPreferences.getBoolean(
                AdminKeys.KEY_SEND_FINALIZED, true);


        boolean viewSent = sharedPreferences.getBoolean(
                AdminKeys.KEY_VIEW_SENT, true);






        ((Collect) getApplication())
                .getDefaultTracker()
                .enableAutoActivityTracking(true);
    }


}