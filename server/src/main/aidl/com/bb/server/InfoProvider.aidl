// InfoProvider.aidl
package com.bb.server;
import com.bb.server.Callback;

// Declare any non-default types here with import statements

interface InfoProvider {
    void setCallback(in Callback callback);
}
