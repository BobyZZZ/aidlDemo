package com.bb.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.bb.server.Callback;
import com.bb.server.InfoProvider;
import com.bb.server.State;

public class ClientService extends Service {
    String TAG = "ClientService";
    private ServiceConnection mConn;

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
        ComponentName componentName = new ComponentName("com.bb.server", "com.bb.server.ServiceProvider");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]设置回调");
                InfoProvider infoProvider = InfoProvider.Stub.asInterface(service);
                try {
                    infoProvider.setCallback(new MyCallback());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent, mConn,BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    class MyCallback extends Callback.Stub {
        @Override
        public void onCallback(State data) throws RemoteException {
            Log.e(TAG, "onCallback: " + data.getCode());
        }
    }
}
