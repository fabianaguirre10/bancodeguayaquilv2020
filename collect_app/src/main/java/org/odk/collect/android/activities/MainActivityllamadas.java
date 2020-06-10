package org.odk.collect.android.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.CallLog;
import android.text.BoringLayout;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.odk.collect.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivityllamadas extends AppCompatActivity {
    private static final int CODIGO_SOLICITUD_PERMISOS = 1;
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityllamadas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;
    }
    public  void mostrarllamdas(View view){
        if(ChecarStatusPermisos()){
            consultarCPLlamadas();
        }else {
            Solicitarpermiso();
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

            Toast.makeText(activity, "Los permisos fueron otorgados", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_CALL_LOG
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
                    Toast.makeText(activity, "Permisos OK", Toast.LENGTH_SHORT).show();
                   // consultarCPLlamadas();
                }else{
                    Toast.makeText(activity, "No se activo el permiso", Toast.LENGTH_SHORT).show();
                }
        }

    }
    public void consultarCPLlamadas(){
        TextView tvLlamadas=(TextView)findViewById(R.id.tvLlamdas);
        tvLlamadas.setText("");
        Uri direccionUrillamadas= CallLog.Calls.CONTENT_URI;
        //nuemro,fecha,tipo,duraccion
        String[]campos={
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION
        };
        ContentResolver contentResolver= getContentResolver();
        String[] numeroTelefono={"0988412421"};

        Cursor registros= contentResolver.query(direccionUrillamadas,campos, CallLog.Calls.NUMBER+"=?",numeroTelefono,CallLog.Calls.DATE+" DESC");
        String detalles="";
        while(registros.moveToNext()) {
            //obtenemos los datos a partir del indice de la colummna
            String numero = registros.getString(registros.getColumnIndex(CallLog.Calls.NUMBER));
            Long fecha = registros.getLong(registros.getColumnIndex(CallLog.Calls.DATE));
            int tipo = registros.getInt(registros.getColumnIndex(CallLog.Calls.TYPE));
            String duracion = registros.getString(registros.getColumnIndex(CallLog.Calls.DURATION));
            String tipollamada="";

            //validar tipo de llamda
            switch (tipo){
                case CallLog.Calls.INCOMING_TYPE:
                    tipollamada=getResources().getString(R.string.entrada);
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    tipollamada=getResources().getString(R.string.perdida);
                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    tipollamada=getResources().getString(R.string.salida);
                    break;
                default:
                    tipollamada=getResources().getString(R.string.desconocida);
            }
             detalles= detalles+ getResources().getString(R.string.etiqueta_numero)+ numero+
                    "\n"+getResources().getString(R.string.etiqueta_fecha)+ DateFormat.format("dd/mm/yy k:mm",fecha)+
                    "\n"+getResources().getString(R.string.etiqueta_tipo)+ tipollamada+
                    "\n"+getResources().getString(R.string.etiqueta_duracion)+ duracion+"s.";

        }
        tvLlamadas.setText(detalles);
    }
    public String consultarCPLlamadasFormulario(String telefono, Context act) throws ParseException {

        Uri direccionUrillamadas = CallLog.Calls.CONTENT_URI;
        //nuemro,fecha,tipo,duraccion
        String[] campos = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION
        };
        ContentResolver contentResolver = act.getContentResolver();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -7);

        Date d = c.getTime();


        String[] numeroTelefono = {telefono};

        Cursor registros = contentResolver.query(direccionUrillamadas, campos, CallLog.Calls.NUMBER + "=?", numeroTelefono, CallLog.Calls.DATE + " DESC");
        String detalles = "";
        int totalllamada = 0;


        while (registros.moveToNext()) {
            //obtenemos los datos a partir del indice de la colummna
            String numero = registros.getString(registros.getColumnIndex(CallLog.Calls.NUMBER));
            Long fecha = registros.getLong(registros.getColumnIndex(CallLog.Calls.DATE));
            int tipo = registros.getInt(registros.getColumnIndex(CallLog.Calls.TYPE));
            String duracion = registros.getString(registros.getColumnIndex(CallLog.Calls.DURATION));
            String tipollamada = "";
            if (System.currentTimeMillis() > d.getTime()) {
                //catalog_outdated = 1;
            }

            String dateformato = "";
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // modify format
                dateformato = formatter.format(new Date(fecha));
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateformato);


                System.out.println("Today is " + date1);
                if (date1.getTime() >= d.getTime()) {


                    //validar tipo de llamda
                    switch (tipo) {
                        case CallLog.Calls.INCOMING_TYPE:
                            tipollamada = act.getApplicationContext().getResources().getString(R.string.entrada);
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            tipollamada = act.getApplicationContext().getResources().getString(R.string.perdida);
                            break;
                        case CallLog.Calls.OUTGOING_TYPE:
                            tipollamada = act.getApplicationContext().getResources().getString(R.string.salida);
                            break;
                        default:
                            tipollamada = act.getApplicationContext().getResources().getString(R.string.desconocida);
                    }
                    detalles = detalles + "\n" + act.getApplicationContext().getResources().getString(R.string.etiqueta_numero) + numero +
                            "\n" + act.getApplicationContext().getResources().getString(R.string.etiqueta_fecha) + dateformato +
                            "\n" + act.getApplicationContext().getResources().getString(R.string.etiqueta_tipo) + tipollamada +
                            "\n" + act.getApplicationContext().getResources().getString(R.string.etiqueta_duracion) + duracion + "s.";
                    totalllamada += Integer.valueOf(duracion);

                }


            } catch (Exception ex) {

            }

        }
        return detalles + "\n Tiempo total de llamada: " + totalllamada;
    }
}
