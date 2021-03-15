// Callback.aidl
package com.bb.server;
import com.bb.server.State;
// Declare any non-default types here with import statements

interface Callback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onCallback(in State data);
}
