package com.bb.client2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bb.aidl.IServiceToClient;
import com.bb.aidl.ServiceProvider;
import com.bb.aidl.ServiceProxy;
import com.bb.aidl.State;

public class MainActivity extends AppCompatActivity {
    String TAG = "Client2";
    private ServiceProxy mServiceProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceProvider instance = ServiceProvider.getInstance(this);
        getService(instance);
    }

    private void getService(ServiceProvider instance) {
        instance.getService(new ServiceProvider.ConnectCallback() {
            @Override
            public void onServiceConnected(ServiceProxy serviceProxy) {
                mServiceProxy = serviceProxy;
                mServiceProxy.addCallback(new IServiceToClient() {
                    @Override
                    public void onCallback(State state) {
                        Log.e(TAG, "onCallback: " + state.code);
                    }

                    @Override
                    public String from() {
                        return TAG;
                    }
                });
            }
        });
    }

    public void onClick(View view) {
        mServiceProxy.test(TAG + "发来的消息");
    }
}
