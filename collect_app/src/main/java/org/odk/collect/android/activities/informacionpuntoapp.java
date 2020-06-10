package org.odk.collect.android.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jsibbold.zoomage.ZoomageView;

import org.json.JSONObject;
import org.odk.collect.android.R;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class informacionpuntoapp  extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mapa;
    LatLng origin = new LatLng(-0.33405, -78.45217);
    LatLng dest = new LatLng(-0.22985, -78.5249481);
    ProgressDialog progressDialog;
    GoogleMap mMap;
    LocationManager locationManager;
    /*Se declara una variable de tipo LocationManager encargada de proporcionar acceso al servicio de localización del sistema.*/
    private LocationManager locManager = null;
    /*Se declara una variable de tipo Location que accederá a la última posición conocida proporcionada por el proveedor.*/
    private Location loc;

    AlertDialog alert = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacionpunto);


        // Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!checkLocation())
                return;

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            ZoomageView imageZoom = (ZoomageView) findViewById(R.id.myZoomageView);
            TextView txtcod = (TextView) findViewById(R.id.txtcodigo);
            TextView txtnom = (TextView) findViewById(R.id.txtnombre);
            BranchSession objBranchSeccion = new BranchSession();
            TextView txtnomcliente = (TextView) findViewById(R.id.txtnombrecliente);
            TextView txtdireccion = (TextView) findViewById(R.id.txtdirecion);
            String imageURL = objBranchSeccion.getE_fotoexterior();
            txtcod.setText(objBranchSeccion.getE_code() + "-");
            txtnom.setText(objBranchSeccion.getE_name());
            txtnomcliente.setText(objBranchSeccion.getE_propietario() + "-" + objBranchSeccion.getE_Cedula());
            txtdireccion.setText(objBranchSeccion.getE_mainStreet() + "-" + objBranchSeccion.getE_reference());
            if (networkInfo != null && networkInfo.isConnected()) {
                /*Se asigna a la clase LocationManager el servicio a nivel de sistema a partir del nombre.*/

                new LoadImage(imageZoom).execute(imageURL);

                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(this, "No esta activo", Toast.LENGTH_LONG).show();
                    AlertNoGps();
                }
                if (loc != null) {
                    origin = new LatLng(loc.getLatitude(), loc.getLongitude());
                    dest = new LatLng(Double.parseDouble(objBranchSeccion.getE_LatitudeBranch()), Double.parseDouble(objBranchSeccion.getE_LenghtBranch()));
                    drawPolylines();
                }
            } else {
                Toast.makeText(this, "No tiene conectividad de internet..!!!!", Toast.LENGTH_LONG).show();
    }
}


    }
    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
    private boolean checkLocation() {
        if (!isLocationEnabled())
            AlertNoGps();
        return isLocationEnabled();
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1 /* El codigo que puse a mi request */: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // aqui ya tengo permisos
                } else {
                    // aqui no tengo permisos
                }
                return;
            }
        }
    }

    public void LLamarPhone(View view){

        BranchSession objBranchSeccion = new BranchSession();
        objBranchSeccion.setE_nombrearchivoaudio("");
         if(objBranchSeccion.getE_Phone().toString().length()==9) {

                 Intent i = new Intent(Intent.ACTION_DIAL);
                 i.setData(Uri.parse("tel:"+objBranchSeccion.getE_Phone())); //+ objBranchSeccion.getE_Phone()));
                 startActivity(i);

        }else if(objBranchSeccion.getE_Phone().toString().length()==10) {

                 Intent i = new Intent(Intent.ACTION_DIAL);
                 i.setData(Uri.parse("tel:"+objBranchSeccion.getE_Phone())); //+ objBranchSeccion.getE_Phone()));
                 startActivity(i);


        }else{
            Toast.makeText(this , "Número de Teléfono invalido!!!!", Toast.LENGTH_SHORT).show();
        }
    }
    public void formulario(View view) {
        startActivity(new Intent(this.getApplication(), FormChooserList.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
    public void salir(View view) {

    }
    @SuppressLint("MissingPermission")
    public void actualizar(View view) {
        if (!checkLocation())
            return;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) {
                    BranchSession objBranchSeccion = new BranchSession();
                    mMap.clear();
                    origin = new LatLng(loc.getLatitude(), loc.getLongitude());
                    dest = new LatLng(Double.parseDouble(objBranchSeccion.getE_LatitudeBranch()), Double.parseDouble(objBranchSeccion.getE_LenghtBranch()));
                    drawPolylines();
                    mMap.addMarker(new MarkerOptions()
                            .position(origin)
                            .title("MI UBICACIÓN")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    mMap.addMarker(new MarkerOptions()
                            .position(dest)
                            .title(objBranchSeccion.getE_name())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
                } else {
                    AlertNoGps();
                }
            } catch (Exception ex) {

            }
        }else{
            Toast.makeText(this, "No tiene conectividad de internet..!!!!", Toast.LENGTH_LONG).show();
        }
    }
    private void drawPolylines() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Por favor espere, la polilínea entre dos ubicaciones se está construyendo.");
        progressDialog.setCancelable(true);
        progressDialog.show();

        // Checks, whether start and end locations are captured
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(origin, dest);
        Log.d("url", url + "");
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled (true);
        mMap.setIndoorEnabled(true);


        BranchSession objBranchSeccion = new BranchSession();

        googleMap.addMarker(new MarkerOptions()
                .position(origin)
                .title("MI UBICACIÓN")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(dest)
                .title(objBranchSeccion.getE_name())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
    }

    public void sendMessageToWhatsAppContact(View view) {
        BranchSession objBranchSeccion = new BranchSession();
        switch(objBranchSeccion.getE_Phone().toString().length()){
            case 10:
                enviarwhasap(objBranchSeccion.getE_Phone()) ;
                break;
            case 9:
                enviarwhasap("0"+objBranchSeccion.getE_Phone()) ;
                       break;
            default:
                Toast.makeText(this , "Número de Teléfono invalido para uso de WHATSAAP!!!!", Toast.LENGTH_SHORT).show();
                        break;
        }

        }

public void  enviarwhasap(String nuemrotelefono){
        BranchSession objBranchSeccion = new BranchSession();
        PackageManager packageManager = this.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        String url="";
        try {
            if(objBranchSeccion.getE_isclient().equals("BGSuperName")) {
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
    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            progressDialog.dismiss();
            Log.d("result", result.toString());
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+"&key=AIzaSyDzL5Xs37ud3aRBdbGpkwyNCmrE961Peas";


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();
            Log.d("data", data);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ZoomageView> imageViewReference;

        public LoadImage(ZoomageView imageView) {
            imageViewReference = new WeakReference<ZoomageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (Exception e) {
                Log.e("LoadImage class", "doInBackground() " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ZoomageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.e("LoadImage class", "Descargando imagen desde url: " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}