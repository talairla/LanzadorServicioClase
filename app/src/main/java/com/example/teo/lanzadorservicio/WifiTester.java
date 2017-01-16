package com.example.teo.lanzadorservicio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WifiTester extends Service {
    final String tag="Demo Servicio";
    boolean enEjecucion=false;
    Tester hiloTester;

    public WifiTester() {
    }

    public boolean enEjecucion() {
        return enEjecucion;
    }

    public void setEnEjecucion(boolean enEjecucion) {
        this.enEjecucion = enEjecucion;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag,"Servicio Wireless Creado");
        hiloTester = new Tester(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!enEjecucion()) {
            setEnEjecucion(true);
            Log.i(tag, "Servicio Wireless Arrancado");
            hiloTester.start();
        }
        else {
            Log.i(tag, "El servicio ya estaba arrancado");
        }
            return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(tag, "Servicio Wireless Destruido!");
        if(enEjecucion()) {
            setEnEjecucion(false);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Al ser un servicio unbounded, podemos devolver null.
        return null;
    }


}
