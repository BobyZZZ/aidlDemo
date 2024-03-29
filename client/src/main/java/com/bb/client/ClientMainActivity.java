package com.bb.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bb.aidl.IServiceToClient;
import com.bb.aidl.ServiceProvider;
import com.bb.aidl.ServiceProxy;
import com.bb.aidl.State;

public class ClientMainActivity extends AppCompatActivity {
    String TAG = "Client1";
    private ServiceProxy mServiceProxy;
    private IServiceToClient mServiceToClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        ServiceProvider instance = ServiceProvider.getInstance(this);
        getService(instance);
    }

    private void getService(ServiceProvider instance) {
        instance.getService(new ServiceProvider.ConnectCallback() {
            @Override
            public void onServiceConnected(ServiceProxy serviceProxy) {
                mServiceProxy = serviceProxy;
                mServiceToClient = new IServiceToClient() {
                    @Override
                    public void onCallback(State state) {
                        Log.e(TAG, "onCallback: " + state.code);
                    }

                    @Override
                    public String from() {
                        return TAG;
                    }
                };
                mServiceProxy.addCallback(mServiceToClient);
            }
        });
    }

    public void onClick(View view) {
        mServiceProxy.test(TAG + "发来的消息");
        Log.d(TAG, "onClick: 反注册回调");
        mServiceProxy.removeCallback(mServiceToClient);
    }
}
