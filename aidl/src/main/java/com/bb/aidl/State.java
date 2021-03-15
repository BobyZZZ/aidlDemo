package com.bb.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class State implements Parcelable {
    public int code = -1;

    public int getCode() {
        return code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
    }

    public State() {
    }

    protected State(Parcel in) {
        this.code = in.readInt();
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel source) {
            return new State(source);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };
}
