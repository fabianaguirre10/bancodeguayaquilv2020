package org.odk.collect.android.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class HttpManager {
    private String url;
    private static HttpClient httpClient;

    // sobrecargo el constructor
    public HttpManager(String url)
    {
        this.url=url;
        if(httpClient==null)
        {
            httpClient = crearHttpClient(80);
        }
    }


    public HttpManager(String url, int port)
    {
        this.url=url;
        if(httpClient==null)
        {
            httpClient = crearHttpClient(port);
        }
    }



    public String getStrDataByGET() throws ClientProtocolException, IOException
    {

        // tipo de request GET
        HttpGet request = new HttpGet(url);

        // tipo de dato de respuesta esperado
        request.setHeader("Accept","text/plain");

        // ejecutamos el request y creamos el response
        HttpResponse response = httpClient.execute(request);

        // leemos estado
        int statucCode = response.getStatusLine().getStatusCode();
        if(statucCode!= HttpStatus.SC_OK)
        {
            throw new IOException();
        }

        // obtenemos el wraper que contiene la respuesta (HttpEntity)
        HttpEntity entityResponse = response.getEntity();
        //____________________________________________
        // devolvemos la respuesta convertida a String
        return EntityUtils.toString(entityResponse);
        // ___________________________________________
    }

    public byte[] getBytesDataByGET() throws ClientProtocolException, IOException
    {
        // tipo de request GET
        HttpGet request = new HttpGet(url);
        // tipo de dato de respuesta esperado
        request.setHeader("Accept","image/png");

        // ejecutamos el request y creamos el response
        HttpResponse response = httpClient.execute(request);

        // leemos estado
        int statucCode = response.getStatusLine().getStatusCode();
        if(statucCode!= HttpStatus.SC_OK)
        {
            throw new IOException();
        }

        // obtenemos el wraper que contiene la respuesta (HttpEntity)
        HttpEntity entityResponse = response.getEntity();
        //____________________________________________

        // devolvemos la respuesta convertida a String
        return EntityUtils.toByteArray(entityResponse);
        // ___________________________________________
    }

    public String sendJsonDataByPOST(String JsonData) throws ClientProtocolException, IOException
    {
        // tipo de request POST
        HttpPost post = new HttpPost(url);

        // tipo de dato de respuesta esperado
        post.setHeader("Accept","application/json");
        post.setHeader("Content-type", "application/json");

        // envio el Json
        StringEntity entity = new StringEntity(JsonData);
        post.setEntity(entity);
        int timeoutConnection = 10000;
        // ejecutamos el request y creamos el response
        HttpResponse response = httpClient.execute(post);

        // leemos estado
        int statucCode = response.getStatusLine().getStatusCode();





        if(statucCode!= HttpStatus.SC_OK)
        {
            throw new IOException();
        }

        // obtenemos el wraper que contiene la respuesta (HttpEntity)
        HttpEntity entityResponse = response.getEntity();
        //____________________________________________
        // devolvemos la respuesta convertida a String
        return EntityUtils.toString(entityResponse);
        // ___________________________________________
    }

    private HttpClient crearHttpClient(int port)
    {
        int timeOutSocket = 30;

        // creamos un registro de esquema datos de la URI y TCP
        SchemeRegistry schemeRegistry = new SchemeRegistry();

        // creamos un esquema indicando http y port
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),port));

        // creamos un objeto HttpParams con los parametros para la conexion
        // partiendo de los parametros por defecto
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, 5); //5 con max.
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(5));

        // timeouts

        // Set the timeout in milliseconds until a connection is established.
        //int timeoutConnection = 10000;
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(params, timeoutConnection);

        // in milliseconds which is the timeout for waiting for data.
        //int timeoutSocket = 15000;
        //int timeoutSocket = 40000;
        int timeoutSocket = (timeOutSocket * 1000);

        Log.d("SW","timeoutSocket: " + timeoutSocket + "------<");
        HttpConnectionParams.setSoTimeout(params, timeoutSocket);

        // creamos el ThreadSafe para pasarle al HttpClient
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

        HttpClient httpClient = new DefaultHttpClient(cm,params);

        return httpClient;
    }

    public static boolean hayInternet(Context c)
    {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        return false;
    }

    public static boolean hayRedDisponible(Context c)
    {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isAvailable()) {
            return true;
        }

        return false;
    }

    public static boolean redConectando(Context c)
    {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }

    public static String getTipoDeRed(Context c)
    {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null) {
            return netInfo.getTypeName();
        }

        return "";
    }

}

