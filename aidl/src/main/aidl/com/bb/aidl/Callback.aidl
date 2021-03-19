// Callback.aidl
package com.bb.aidl;
import com.bb.aidl.State;
// Declare any non-default types here with import statements

/**
*Client's callback Interface define，Server use this interface to communicate with Client
*/
interface Callback {
    void onCallback(in State data);
    String from();
}
