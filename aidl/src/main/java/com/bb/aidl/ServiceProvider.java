package com.bb.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * 封装隔离aidl
 * 优点：aidl代码集中在库中实现，Client端不需要实现任何aidl。
 */
public class ServiceProvider {
    private final Context mContext;
    String TAG = "ServiceProvider";
    static ServiceProvider mInstance;
    private IServiceProvider mService;
    private ServiceProxy mServiceProxy;

    private ServiceProvider(Context context) {
        mContext = context;
        bindServices(context);
    }

    private void bindServices(Context context) {
        ComponentName componentName = new ComponentName("com.bb.server", "com.bb.server.ServiceProvider");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        context.bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    public static ServiceProvider getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ServiceProvider.class) {
                if (mInstance == null) {
                    mInstance = new ServiceProvider(context);
                }
            }
        }
        return mInstance;
    }

    public void getService(ConnectCallback connectCallback) {
        mConnectCallback = connectCallback;
    }

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(TAG, "onServiceConnected() called with: name = [" + name + "], 客户端"+mContext.getPackageName()
                    +"获取到的Binder内存地址 = [" + service + "]");
            mService = IServiceProvider.Stub.asInterface(service);
            if (mConnectCallback != null) {
                mConnectCallback.onServiceConnected(new ServiceProxy(mService));
                mConnectCallback = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
        }
    };

    private ConnectCallback mConnectCallback;
    public interface ConnectCallback {
        void onServiceConnected(ServiceProxy serviceProxy);
    }

}
