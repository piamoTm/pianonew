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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
 *
 * 박자 컨버터 메소드 만들기
 * */
public class MakeMusic extends AppCompatActivity {
    private static final String TAG = "MakeMusic";

    private ImageView mBackBtn;
    private Button mPlayBtn,mSaveBtn;
    private RecyclerView mMakeNoteRecycler;
    private MakeMusic_RecyclerAdapter adapter;
    ArrayList<String> makeNotsArr;
    ArrayList<Integer> makeBeatArr;

    //BlueTooth
    private static String mConnectedDeviceName = null;
    static boolean isConnectionError = false;
    ConnectedTask mConnectedTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_make_music);

        mBackBtn = (ImageView)findViewById(R.id.back_btn);
        mSaveBtn = (Button)findViewById(R.id.saveBtn);
        mPlayBtn =(Button)findViewById(R.id.palyBtn);

        //infoshow("확인","no","건반을 눌러주세요:)");// 안내 다이얼로그
        init();//리사이클러뷰 초기 세팅

        // 블루투스 소켓연결
        mConnectedTask = new ConnectedTask(SocketHandler.getmBluetoothsocket(),SocketHandler.getmDeviceName());
        mConnectedTask.execute();


        sendMessage("W"); //작곡모드로



        //백버튼 누르면 뒤로가는 이벤트 붙임
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoshow("취소","ok","저장하지 않고 끝내시겠습니까?");
            }
        });


        // 작곡된 음악 play 버튼
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str;
                int i = 0;
                str = new String[8];
                str[0] = "C";
                str[1] = "D";
                str[2] = "E";
                str[3] = "F";
                str[4] = "G";
                str[5] = "A";
                str[6] = "B";
                str[7] = "H";
                str[8] = " ";
                makeNotsArr.add(str[8]);
                // Log.i("TESTLOG_YYJ","postion : "+testAdapter3.getItemCount());
                mMakeNoteRecycler.getLayoutManager().scrollToPosition(adapter.getItemCount()-1);
                adapter.notifyDataSetChanged();


            }
        });

        //작곡된 노래 play list 에 저장
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();  // 제목 , 작곡자 입력  dialog
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        infoshow("취소","ok","저장하지 않고 끝내시겠습니까?");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            if ( mConnectedTask != null ) {
                mConnectedTask.cancel(true);
            }
        sendMessage("N");  //노말 모드로
    }


    //리사이클러뷰 초기 세팅//
    private void init() {
        mMakeNoteRecycler = (RecyclerView)findViewById(R.id.makenote);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMakeNoteRecycler.setLayoutManager(linearLayoutManager);

        makeNotsArr = new ArrayList<>();
        makeBeatArr = new ArrayList<>();
        adapter = new MakeMusic_RecyclerAdapter(makeNotsArr,makeBeatArr);
        mMakeNoteRecycler.setAdapter(adapter);
        makeNotsArr.add("");

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
        int chBeat ;

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

                                //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
                                String[] note_split = recvMessage.trim().split(",");
                                Log.d(TAG, "make splite recv message: " + note_split[0]);//계이름
                                Log.d(TAG, "make splite recv message Beat: " + convertBeat(note_split[1])+"박//");//박자

                                chBeat  = convertBeat(note_split[1]); // 박자 컨버터 1박이면 1보냄
                                makeNotsArr.add(note_split[0]); //CC  A
                                makeBeatArr.add(chBeat);        //13001

                                //==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
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
           // Log.d(TAG, "make splite recv message chchchBeat: " + chBeat+"박//");//박자
            // 박자에 맞게 공란이랑 비트 넣어주기
            for(int i=1; i< chBeat;i++) {
                makeNotsArr.add(" ");
                makeBeatArr.add(0);
            }
            //포커스 잡는중
            mMakeNoteRecycler.getLayoutManager().scrollToPosition(adapter.getItemCount()-1);
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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    //처음 안내 dialog
    void infoshow(String posiviceMsg, String okFinish, String title)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(title);
        builder.setPositiveButton(posiviceMsg,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                    }
                });

        if(okFinish.equals("ok")){
            builder.setNegativeButton("종료",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
        builder.show();
    }
    // 제목 , 작곡자 입력 dialog
    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_makemusic, null);
        builder.setView(view);
        final Button saveBtn = (Button) view.findViewById(R.id.dsave_btn);    // 확인
        final Button cancelBtn = (Button) view.findViewById(R.id.dcencle_btn);   //취소
        final EditText title_make = (EditText) view.findViewById(R.id.title_make);  //제목
        final EditText name_make = (EditText) view.findViewById(R.id.name_make);   //작곡자

        final AlertDialog dialog = builder.create();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strEmail = title_make.getText().toString();
                String strPassword = name_make.getText().toString();
                Toast.makeText(getApplicationContext(), strEmail+"/"+strPassword,Toast.LENGTH_LONG).show();

                //빈오선지 체크 할것// 당일러고ㅡ로

                // Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();

                // 뮤직 객체에 넣고 dblite 메소드에 넣어줄것
                //SAVE//
                //SQLite db 개체 생성
                //DBMyProductHelper_Write db;

                //SQLite db helper init 초기화
                //db = new DBMyProductHelper_Write(this);

                //새로운 노래를(Music 개체를) db에 추가
                //db.addMusic(Music);


                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();



    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    //작곡된 전체 계이름 악보 가져오기
    public StringBuilder readNots(){
        //노래전체 악보 가져오기
        StringBuilder sb = new StringBuilder();
        for(Object object : makeNotsArr) {
            sb.append(object);
        }
       // Log.i("MakeMusic_wholeNotes","wholeNotes_확인 :"+sb);
        return sb;
    }

    // 아두이노 millis 컨버터
    //눌러진 만큼의 밀리수를
    public  int convertBeat(String originalBeat){
        int basic = 333; //아두이노 기본 비트
        int orBeat = Integer.parseInt(originalBeat);//박자
        int i ; // 반환 비트

        if(basic >= orBeat){
            i = 1;
        }else if(basic*2 >= orBeat){
            i = 2;
        }else if(basic*3 >= orBeat){
            i = 3;
        }else {
            i = 4;
        }
        return i;
    }

}
