package com.bb.server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.SeekBar;

import com.bb.aidl.State;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    private ServiceProvider.MyBinder mMyBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        startMyServices();
    }

    private void initView() {
        SeekBar seekBar = findViewById(R.id.sb);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                State state = new State();
                state.code = progress;
                mMyBinder.update(state);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void startMyServices() {
        Log.d(TAG, "startMyServices: ");
        Intent intent = new Intent(this, ServiceProvider.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
                mMyBinder = (ServiceProvider.MyBinder) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
                mMyBinder = null;
            }
        },BIND_AUTO_CREATE);
    }
}
