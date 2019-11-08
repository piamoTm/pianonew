package com.example.pianoadroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class Main_menu extends AppCompatActivity {

     SocketHandler socketdata = new SocketHandler();
    private Button mPlayBtn, mWriteBtn;
    private static final String TAG = "Main_BluetoothC";
    private final int REQUEST_BLUETOOTH_ENABLE = 100;

    static boolean isConnectionError = false;
    static BluetoothAdapter mBluetoothAdapter;
    private String mConnectedDeviceName = null;

    MakeMusic.ConnectedTask mConnectedTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mPlayBtn = (Button)findViewById(R.id.play_btn);  //연주하기
        mWriteBtn = (Button)findViewById(R.id.write_btn);  // 작곡하기


        //연주하기
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MusicTest.class);
                //intent.putExtra("imageUri", uri);
                startActivity(intent);
            }
        });

        // 작곡하기
        mWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MakeMusic.class);
                //intent.putExtra("imageUri", uri);
                startActivity(intent);

            }
        });
        Log.d( TAG, "Initalizing Bluetooth adapter...");

        //BluetoothAdapter 클래스를 얻는 작업부터 시작
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            showErrorDialog("이 기기는 블루투스를 구현하지 않았습니다.");
            return;
        }

        // 블루투스 활성/비활성 판단
        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_BLUETOOTH_ENABLE);
        }
        else {
            Log.d(TAG, "Initialisation successful.");
            //블루투스 활성화됨
            showPairedDevicesListDialog();
        }

    }

    //페어링 기기 검색 .dialog 띄워주기
    public void showPairedDevicesListDialog()
    {
        //페어링 된 기기 목록을 얻으며 페어링 된 기기 정보가 BluetoothDevice 전달됨
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        final BluetoothDevice[] pairedDevices = devices.toArray(new BluetoothDevice[0]);

        if ( pairedDevices.length == 0 ){
            showQuitDialog( "연결 된 기기가 없습니다.\n"
                    +"피아노와 연결하세요.");
            return;
        }

        String[] items;
        items = new String[pairedDevices.length];
        for (int i=0;i<pairedDevices.length;i++) {
            items[i] = pairedDevices[i].getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("기기 선택");
        builder.setCancelable(false);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //기기 선택후  기기와 CONNECT함
                ConnectTask task = new ConnectTask(pairedDevices[which]);
                task.execute();
            }
        });
        builder.create().show();
    }

    //아두이노와 안드로이드 연결함
    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {

        private BluetoothSocket mBluetoothSocket = null;
        private BluetoothDevice mBluetoothDevice = null;

        ConnectTask(BluetoothDevice bluetoothDevice) {
            mBluetoothDevice = bluetoothDevice;
            mConnectedDeviceName = bluetoothDevice.getName();

            //SPP
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

            try {
                mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                Log.d( TAG, "create socket for "+mConnectedDeviceName);

            } catch (IOException e) {
                Log.e( TAG, "socket create failed " + e.getMessage());
            }

            Toast.makeText(getApplicationContext(),"connecting...",Toast.LENGTH_LONG).show();
        }


        @Override
        protected Boolean doInBackground(Void... params) {

            // Always cancel discovery because it will slow down a connection
            mBluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mBluetoothSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mBluetoothSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "닫을 수 없습니다() " +
                            " 연결 실패 중 소켓", e2);
                }

                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean isSucess) {

            if ( isSucess ) { //연결됨
                Log.d( TAG, mConnectedDeviceName+"에 연결되었습니다.");
                Toast.makeText(Main_menu.this,mConnectedDeviceName+"에 연결되었습니다",Toast.LENGTH_LONG).show();

                // 연결된후 서로 메세지보낼수 있는 메소드
                connected(mBluetoothSocket,mConnectedDeviceName);
            }
            else{

                isConnectionError = true;
                Log.d( TAG,  "장치를 연결할 수 없습니다");
                showErrorDialog("장치를 연결할 수 없습니다");
            }
        }
    }


    //기기와 연결된후 통신하기
    public void connected( BluetoothSocket socket , String mDevicename) {
        socketdata.setmBluetoothsocket(socket);
        socketdata.setmDeviceName(mDevicename);

    }
    public void showErrorDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit");
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if ( isConnectionError  ) {
                    isConnectionError = false;
                    finish();
                }
            }
        });
        builder.create().show();
    }

    //블루투스 활성화시 연결되지 않으면 나오는 dialog
    public void showQuitDialog(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit");
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_BLUETOOTH_ENABLE){
            if (resultCode == RESULT_OK){
                //BlueTooth is now Enabled
                showPairedDevicesListDialog();
            }
            if(resultCode == RESULT_CANCELED){
                showQuitDialog( "블루투스를 활성화해야합니다");
            }
        }
    }
}
