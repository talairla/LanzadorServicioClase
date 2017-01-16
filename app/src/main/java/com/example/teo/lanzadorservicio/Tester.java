package com.example.teo.lanzadorservicio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by Teo on 11/01/2017.
 */

public class Tester extends Thread {

    WifiTester servicioWifi;
    String tag;
    Boolean wifiActivada;

    Tester(WifiTester servicio){
        servicioWifi = servicio;
        tag = servicio.tag + " - hilo";
        wifiActivada = comprobarConexionWifi();
    }

    @Override
    public void run() {
        super.run();
        while (servicioWifi.enEjecucion() == true){
            try {
                if(wifiActivada() != comprobarConexionWifi()) {
                    Log.i(tag,"Servicio activado, hilo activado");
                    Vibrator vibrator = (Vibrator) servicioWifi.
                            getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    //El patron se define como tiempo alternativo entre
                    //Vibrar y no vibrar en milisegundos empezando en NoVibrar
                    long[] patron = {0, 200, 200, 500, 200, 1000};
                    vibrator.vibrate(patron,-1);
                    cambiarEstadoWifi();
                    if (wifiActivada()) {
                        Log.i(tag, "Wifi activada");
                    } else {
                        Log.i(tag, "Wifi desactivada");
                    }
                }
                this.sleep(2000);
            } catch (InterruptedException e) {
                Log.i(tag,"Hilo interrumpido");
                servicioWifi.setEnEjecucion(false);
                e.printStackTrace();
            }
        }
    }

    private Boolean comprobarConexionWifi() {
        ConnectivityManager connManager =
                (ConnectivityManager) servicioWifi.
                        getApplicationContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager!=null ){
            NetworkInfo netInfo = connManager.
                    getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (netInfo != null){
                if(netInfo.isConnected()){
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean wifiActivada() {
        return wifiActivada;
    }

    public void setWifiActivada(Boolean wifiActivada) {
        this.wifiActivada = wifiActivada;
    }

    public void cambiarEstadoWifi(){
        wifiActivada = !wifiActivada;
    }


}
