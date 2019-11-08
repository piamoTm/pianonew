package com.example.pianoadroid;

import android.bluetooth.BluetoothSocket;

import java.net.Socket;

public class SocketHandler {
    private  static BluetoothSocket  mBluetoothsocket;

    public static synchronized  BluetoothSocket getmBluetoothsocket() {
        return mBluetoothsocket;
    }

    public static synchronized void setmBluetoothsocket(BluetoothSocket mBluetoothsocket) {
        SocketHandler.mBluetoothsocket = mBluetoothsocket;
    }
}
