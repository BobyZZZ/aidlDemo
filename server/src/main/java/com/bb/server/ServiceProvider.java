package com.bb.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bb.aidl.Callback;
import com.bb.aidl.IServiceProvider;
import com.bb.aidl.State;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvider extends Service {
    String TAG = "ServiceProvider";
    private MyBinder mMyBinder;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        super.onCreate();
        mMyBinder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: 服务端mMyBinder内存地址 = [" + mMyBinder + "]");
        return mMyBinder;
    }

    public class MyBinder extends IServiceProvider.Stub {
        private RemoteCallbackList<Callback> mCallbacks;

        MyBinder() {
            mCallbacks = new RemoteCallbackList<>();
        }

        @Override
        public void addCallback(Callback callback) throws RemoteException {
            Log.d(TAG, "addCallback: " + callback.from());
            mCallbacks.register(callback);
        }

        @Override
        public void removeCallback(Callback callback) throws RemoteException {
            Log.d(TAG, "removeCallback: " + callback.from());
            mCallbacks.unregister(callback);
        }

        @Override
        public void test(String msg) throws RemoteException {
            Log.d(TAG, "服务端接收到: " + msg);
        }


        public void update(State state) {
            try {
                int count = mCallbacks.beginBroadcast();
                Log.w(TAG, "update() called with: state = [" + state.code + "],callback's size: "
                        + mCallbacks.getRegisteredCallbackCount() + "------count: " + count);
                for (int i = 0; i < count; i++) {
                    mCallbacks.getBroadcastItem(i).onCallback(state);
                }
                mCallbacks.finishBroadcast();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
