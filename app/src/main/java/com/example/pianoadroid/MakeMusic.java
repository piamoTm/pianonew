package com.example.pianoadroid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * Class : MakeMusic
 * 내용 : 작곡
 * */
public class MakeMusic extends AppCompatActivity {
    private static final String TAG = "MakeMusic";

    private ImageView mBackBtn;
    private Button mPlayBtn,mUploadBtn;
    private RecyclerView mMakeNoteRecycler;
    private MakeMusic_RecyclerAdapter adapter;


    //BlueTooth
    private static String mConnectedDeviceName = null;
    static boolean isConnectionError = false;

    ConnectedTask mConnectedTask = null;
    Write data; //노래 하나.

    int count =0, con=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_make_music);

        mBackBtn = (ImageView)findViewById(R.id.back_btn);
        mUploadBtn = (Button)findViewById(R.id.saveBtn);
        mPlayBtn =(Button)findViewById(R.id.palyBtn);

        //show();// 안내 다이얼로그
        init();//리사이클러뷰 초기 세팅

        // 블루투스 소켓연결
        mConnectedTask = new ConnectedTask(SocketHandler.getmBluetoothsocket(),SocketHandler.getmDeviceName());
        mConnectedTask.execute();


        sendMessage("W"); //작곡모드로



        //백버튼 누르면 뒤로가는 이벤트 붙임
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendMessage("G");

            }
        });

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
//            if ( mConnectedTask != null ) {
//                mConnectedTask.cancel(true);
//            }
        sendMessage("N");  //노말 모드로
    }


    //리사이클러뷰 초기 세팅//
    private void init() {
        mMakeNoteRecycler = (RecyclerView)findViewById(R.id.makenote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMakeNoteRecycler.setLayoutManager(linearLayoutManager);

        adapter = new MakeMusic_RecyclerAdapter();
        mMakeNoteRecycler.setAdapter(adapter);


        //Music data = new Music();
        //data.setId(0); <- onCreat()로 옮김
        // data.setTitle("도");

        //Music 개체 생성
        data = new Write();
        data.setmNextChek(0);

        adapter.addItem(data);
        adapter.notifyDataSetChanged();

    }

    //아두이노로 데이터 보내기
    void sendMessage(String msg){

        if ( mConnectedTask != null ) {
            mConnectedTask.write(msg.trim());
            Log.d(TAG, "메세지 전송 : " + msg);
        }
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    public  class ConnectedTask extends AsyncTask<Void, String, Boolean> {
        private InputStream mInputStream = null;
        private OutputStream mOutputStream = null;
        private BluetoothSocket mBluetoothSocket = null;

        ConnectedTask(BluetoothSocket socket, String devicename){
            mConnectedDeviceName = devicename;
            mBluetoothSocket = socket;
            try {
                mInputStream = mBluetoothSocket.getInputStream();
                mOutputStream = mBluetoothSocket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "소켓이 생성되지 않았습니다", e );
            }

            Log.d( TAG, mConnectedDeviceName+"에 연결");
        }


        @Override
        protected Boolean doInBackground(Void... params) {

            byte [] readBuffer = new byte[1024];
            int readBufferPosition = 0;


            while (true) {

                if ( isCancelled() ) return false;

                try {

                    int bytesAvailable = mInputStream.available();

                    if(bytesAvailable > 0) {

                        byte[] packetBytes = new byte[bytesAvailable];

                        mInputStream.read(packetBytes);

                        for(int i=0;i<bytesAvailable;i++) {

                            byte b = packetBytes[i];
                            if(b == '\n')
                            {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0,
                                        encodedBytes.length);
                                String recvMessage = new String(encodedBytes, "UTF-8");

                                readBufferPosition = 0;

                                Log.d(TAG, "recv message: " + recvMessage);
                                publishProgress(recvMessage);

                                //=-=-=-

                                count++; //0~>9
                                if(count == 9){
                                    data.setmNextChek(0);
                                    adapter.addItem(data);
                                    count =0;
                                    con++;
                                    Log.i("uploadbtn after", String.valueOf(count));
                                }
                                else {
                                    data.setmNextChek(con);
                                    data.setmNotes(recvMessage.trim());
                                    //여기서 setTitle은 계이름이 아니라 노래제목임..."나비야"같은..
                                    //data.addNote("E");를 사용해야 할듯.
                                    Log.i("uploadbtn after", String.valueOf(count));
                                }
                                //리사이클러뷰 갱신

                                //==-=-
                            }
                            else
                            {
                                readBuffer[readBufferPosition++] = b;
                            }
                        }
                    }
                } catch (IOException e) {

                    Log.e(TAG, "disconnected", e);
                    return false;
                }
            }

        }

        @Override
        protected void onProgressUpdate(String... recvMessage) {

            adapter.notifyDataSetChanged();
            Log.i("MakeMusic : ",mConnectedDeviceName + ": " + recvMessage[0]);


        }

        @Override
        protected void onPostExecute(Boolean isSucess) {
            super.onPostExecute(isSucess);


            if ( !isSucess ) {

                closeSocket();
                Log.d(TAG, "장치 연결이 끊어졌습니다");
                isConnectionError = true;
                showErrorDialog("장치 연결이 끊어졌습니다");
            }
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);

            closeSocket();
        }

        void closeSocket(){

            try {

                mBluetoothSocket.close();
                Log.d(TAG, "close socket()");

            } catch (IOException e2) {

                Log.e(TAG, "unable to close() " +
                        " socket during connection failure", e2);
            }
        }

        void write(String msg){

            msg += "\n";

            try {
                mOutputStream.write(msg.getBytes());
                mOutputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "Exception during send", e );
            }

            ///mInputEditText.setText(" ");
        }
    }
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

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

    //처음 안내 dialog
    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("건반을 눌러주세요.:)");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });

        builder.show();
    }

}
