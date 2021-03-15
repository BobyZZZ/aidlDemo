package com.bb.aidl;

import android.os.RemoteException;

public class ServiceProxy {
    private IServiceProvider service;

    public ServiceProxy(IServiceProvider service) {
        this.service = service;
    }

    public void setCallback(final IServiceToClient serviceToClient){
        try {
            service.setCallback(new Callback.Stub() {
                @Override
                public void onCallback(State data) throws RemoteException {
                    serviceToClient.onCallback(data);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
