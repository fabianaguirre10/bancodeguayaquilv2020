package org.odk.collect.android.activities;

import org.odk.collect.android.activities.HttpManager;
import org.opendatakit.httpclientandroidlib.client.ClientProtocolException;

import java.io.IOException;


public class ThreadEnvio implements Runnable{
    private String jSon;


    public ThreadEnvio(String jSon) {
        this.jSon = jSon;

    }
    @Override
    public void run() {
        String respuesta = "";
        int tipoRespuesta = 0;

        boolean intentar = true;
        int tiempo = 30;
        int vez = 0;

        // intento una vez y si falla hago dos reintentos , pausando en el 1ro 5 segundos y en el 2do 10
        while (intentar) {
            if (vez > 0) {
                try {
                    Thread.sleep(tiempo * vez);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            HttpManager httpManager = new HttpManager("http://geomardis6728.cloudapp.net/msbgform/api/EstadoFormularios", 80);

            try {

                respuesta = httpManager.sendJsonDataByPOST(jSon);
                tipoRespuesta = 1;
                intentar = false;

            } catch (ClientProtocolException e) {

                respuesta = "ERROR: Exception ClientProtocolException";
                tipoRespuesta = 2;
                e.printStackTrace();

            } catch (IOException e) {

                respuesta = "ERROR: IOException";
                tipoRespuesta = 2;
                e.printStackTrace();
            }

            vez++;
            if (vez == 3) {
                intentar = false;
            }
        }

        //enviaMensaje(respuesta, tipoRespuesta);
    }

}
