package com.bb.aidl;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ServiceProxy {
    String TAG = "ServiceProxy";
    private IServiceProvider service;
    private Map<IServiceToClient,IBinder> callbacks;

    public ServiceProxy(IServiceProvider service) {
        this.service = service;
        callbacks = new HashMap<>();
    }

    public void addCallback(final IServiceToClient serviceToClient){
        try {
            //创建回调对象
            final Callback.Stub callback = new Callback.Stub() {
                @Override
                public void onCallback(State data) throws RemoteException {
                    serviceToClient.onCallback(data);
                }

                @Override
                public String from() {
                    return serviceToClient.from();
                }
            };
            service.addCallback(callback);
            callbacks.put(serviceToClient,callback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void removeCallback(final IServiceToClient serviceToClient) {
        IBinder iBinder = callbacks.get(serviceToClient);
        Log.d(TAG, "removeCallback: " + iBinder);
        try {
            if (iBinder != null) {
                service.removeCallback(Callback.Stub.asInterface(iBinder));
                callbacks.remove(serviceToClient);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

        public void test(String msg) {
        try {
            service.test(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
