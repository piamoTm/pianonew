package com.example.pianoadroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Set;


public class Main_menu extends AppCompatActivity {

    private Button mPlayBtn, mWriteBtn;
    private static final String TAG = "Main_BluetoothC";
    private final int REQUEST_BLUETOOTH_ENABLE = 100;

    static boolean isConnectionError = false;
    static BluetoothAdapter mBluetoothAdapter;

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
            showQuitDialog( "페어링 된 기기가 없습니다.\n"
                    +"다른 기기와 페어링해야합니다.");
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
//                //기기 선택후  기기와 CONNECT함
//                Bluetooth_test.ConnectTask task = new Bluetooth_test.ConnectTask(pairedDevices[which]);
//                task.execute();
            }
        });
        builder.create().show();
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
