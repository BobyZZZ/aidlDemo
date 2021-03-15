package com.bb.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return mMyBinder;
    }

    public class MyBinder extends IServiceProvider.Stub {
        private List<Callback> mCallbacks;

        MyBinder() {
            mCallbacks = new ArrayList<>();
        }

        @Override
        public void setCallback(Callback callback) throws RemoteException {
            Log.d(TAG, "setCallback: ");
            mCallbacks.add(callback);
        }

        public void update(State state) {
            Log.w(TAG, "update() called with: state = [" + state.code + "],callback's size: " + mCallbacks.size());
            for (Callback callback : mCallbacks) {
                try {
                    callback.onCallback(state);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
