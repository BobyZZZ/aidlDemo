package com.bb.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bb.aidl.IServiceToClient;
import com.bb.aidl.ServiceProvider;
import com.bb.aidl.ServiceProxy;
import com.bb.aidl.State;

public class ClientService extends Service {
    String TAG = "ClientService";

    public ClientService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        ServiceProvider instance = ServiceProvider.getInstance(this);
        getService(instance);
    }

    private void getService(ServiceProvider instance) {
        instance.getService(new ServiceProvider.ConnectCallback() {
            @Override
            public void onServiceConnected(ServiceProxy serviceProxy) {
                serviceProxy.setCallback(new IServiceToClient() {
                    @Override
                    public void onCallback(State state) {
                        Log.e(TAG, "onCallback: " + state.code);
                    }
                });
            }
        });
    }
}
