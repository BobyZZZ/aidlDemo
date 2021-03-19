package com.bb.aidl;

public interface IServiceToClient {
    void onCallback(State state);
    String from();
}
