// IServiceProvider.aidl
package com.bb.aidl;
import com.bb.aidl.Callback;

// Declare any non-default types here with import statements
/**
*Server's Interface define
*/
interface IServiceProvider {
    void addCallback(in Callback callback);
    void removeCallback(in Callback callback);
    void test(in String msg);
}
