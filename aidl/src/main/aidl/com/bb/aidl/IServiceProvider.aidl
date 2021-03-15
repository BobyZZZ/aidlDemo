// IServiceProvider.aidl
package com.bb.aidl;
import com.bb.aidl.Callback;

// Declare any non-default types here with import statements
/**
*Server's Interface define
*/
interface IServiceProvider {
    void setCallback(in Callback callback);
}
