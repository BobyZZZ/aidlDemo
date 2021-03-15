package com.bb.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ClientMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        startClientService();
    }

    private void startClientService() {
        Intent intent = new Intent(this, ClientService.class);
        startService(intent);
        finish();
    }
}
