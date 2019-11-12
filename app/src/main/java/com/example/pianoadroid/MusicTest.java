package com.example.pianoadroid;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MusicTest extends AppCompatActivity implements MusicTest_Adapter.ThreadFinishListener {


    private static final String TAG = "MusicTest";

    //SQLite db 개체 생성
    private DBMyProductHelper_Read db;

    //블루투스 통신
    //BlueTooth
    private static String mConnectedDeviceName = null;
    static boolean isConnectionError = false;
    ConnectedTask mConnectedTask = null;

    //리사이클러뷰
    RecyclerView mRecyclerView;
    MusicTest_Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    //플레이할 음악
    Music music;

    // 원곡듣기 버튼
    Button btn_listen, btn_stop;

    // 최종 계이름을 담는 변수
    String musicnote;

    // 임시로 디비에서 악보의 계이름을 코드상으로 받는 함수
    String musicnote_eng;

    // 문자열로 온 악보정보를 split로 나눠서 저장한 배열변수
    String [] array;
    // 문자열로 온 악보 박자정보를 split로 나눠서 저장한 배열변수
    String [] bit_array;

    String musicnote_eng_array[];


    // 초 를 담는 변수
    int index_value = 0;
    // 노래 연주 중 중지버튼 클릭 시 중지된 시점의 index_value값을 담을 변수
    // int index_value_stop = 0;

    boolean bool_music = true;

    // 악보의 계이름을 담는 리스트 변수
    ArrayList<String> MusicNoteList = new ArrayList<>();
    // 악보의 계이름의 박자를 담는 리스트 변수
    ArrayList<String> MusicNoteBitList = new ArrayList<>();

    // 뒤로가기 버튼
    ImageView btn_back;

    // 1초마다 노래를 트는 스레드
    music_thread thread;

    // 연주 순서대로 갈 때 포커싱을 주기 위한 변수
    int focusing_cnt = 0;

    // SQLite에서 받아온 노래 비트를 담는 배열 변수
    int db_musicnote_bit[];

    String musicnote_bit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_test);

        btn_listen = findViewById(R.id.btn_listen);
        btn_stop = findViewById(R.id.btn_stop);
        btn_back = findViewById(R.id.img_back);

        // 뒤로가기 버튼
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //리사이클러뷰 변수에 위젯 대입
        mRecyclerView = findViewById(R.id.recycler_music_note);

        //이 설정을 사용하면 변경 사항을 알고 있는 경우 성능을 향상 시킬 수 있슴.
        mRecyclerView.setHasFixedSize(true);

        //LinearLayout으로 layoutManager을 정함
        layoutManager = new LinearLayoutManager(this);

        //리사이클러뷰에 위에서 지정한 layoutManager인 LinearLayout을 대입.
        mRecyclerView.setLayoutManager(layoutManager);

        //인텐트로 뭐 눌렀는지 아이디 받기
        Intent receiveIntent = getIntent();
        int mid = receiveIntent.getIntExtra("id",0);

        //디비에서 불러오기
        //SQLite db helper init 초기화
        db = new DBMyProductHelper_Read(this);
        music = db.getMusic(mid);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(music.getTitle());




        // 디비에서 계이름 코드를 받는 변수
        //musicnote_eng = "BDEFGABCDEFGAB";
        musicnote_eng = music.getScore();
        //Log.i("testLog", music.getBeatStr());
        Log.e("musicnote_eng.length(): ", musicnote_eng.length() + "");

        db_musicnote_bit = new int[musicnote_eng.length()];
        db_musicnote_bit = music.getBeat();
        Log.e("musicnote_bit.length: ", db_musicnote_bit.length + "");
        Log.e("musicnote_bit[0]: ", db_musicnote_bit[0] + "");
        Log.e("musicnote_bit[47]: ", db_musicnote_bit[47] + "");
        // 디비에서 계이름 코드를 받는 변수
        //musicnote_eng = "BDEFGABCDEFGAB";

        // 한글로 바꾸는 변수
        String musicnote_kor = "";

        musicnote_eng_array = musicnote_eng.split("");
        Log.e("musicnote_eng_array크기: ", musicnote_eng_array.length + "");

        for (int i = 0; i < musicnote_eng_array.length; i++){
            if (i != 0){
                if (musicnote_eng_array[i].equals("C")){

                    musicnote_kor += "도";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    //musicnote_bit += db_musicnote_bit[i-1];
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals("D")){

                    musicnote_kor += "레";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];
                }else if(musicnote_eng_array[i].equals("E")){

                    musicnote_kor += "미";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];
                }else if(musicnote_eng_array[i].equals("F")){

                    musicnote_kor += "파";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals("G")){

                    musicnote_kor += "솔";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals("A")){

                    musicnote_kor += "라";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals("B")){

                    musicnote_kor += "시";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals("H")){

                    musicnote_kor += "두";                       // 높은 도 임
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];

                }else if(musicnote_eng_array[i].equals(" ")){

                    musicnote_kor += " ";
                    // 해당 음계에 해당하는 박자수를 담는 변수
                    musicnote_bit += db_musicnote_bit[i-1];
                }

                // index0부터 시작함. bit는
            }else {

            }


        }


        Log.e("한글변환계이름: ", musicnote_kor);
        Log.e("해당노래의박자: ", musicnote_bit);

        // 블루투스 소켓연결
        mConnectedTask = new ConnectedTask(SocketHandler.getmBluetoothsocket(),SocketHandler.getmDeviceName());
        mConnectedTask.execute();


        sendMessage("P"); //연주모드로



        // 코드로 온 계이름을 한글로 변환 후 musicnote변수에 넣음
        musicnote = musicnote_kor;


        // 8개씩 악보를 담아서 배열로 보낸 뒤 초기화 하는 변수
        String array_value = "";
        // 8개씩 악보의 박자를 담아서 배열로 보낸 뒤 초기화 하는 변수
        String array_bit_value = "";

        // 문자열로 온 악보정보를 split로 나눠서 저장한 배열변수
        array = musicnote.split("");
        bit_array = musicnote_bit.split("");




        Log.e("배열에 담긴 악보 채우기 전: ", MusicNoteList + "");

        for (int i = 0; i < array.length; i++){

            // array_value라는 문자열에다가 계이름들을 계속 담음
            array_value += array[i];
            Log.e("array_value값: " , i+"번째: "+array[i]);

            // array_bit_value라는 문자열에다가 계이름의 박자를 계속 담음
            array_bit_value += bit_array[i];
            Log.e("array_bit_value값: " , i+"번째: "+bit_array[i]);

            // 8개가 되었을 때
            if(i % 8 == 0){

                // 0번째는 빈값이라서 1부터 음계가 나옴
                if (i != 0){

                    Log.e(i+"번째까지의 계이름 목록: ", array_value);
                    // 악보의 계이름을 담는 리스트 변수에 9번째까지의 계이름을 담음
                    MusicNoteList.add(array_value);
                    MusicNoteBitList.add(array_bit_value);

                    Log.e("배열에 담기는 중인 악보: ", MusicNoteList + "");
                    Log.e("배열에 담기는 중인 악보박자: ", MusicNoteBitList + "");

                    // 계이름담는 문자열 초기화
                    array_value = "";
                    array_bit_value = "";
                }
            }

            if(i == musicnote.length()){
                Log.e("남은 array_value값: ", array_value);
                Log.e("남은 array_bit_value값: ", array_bit_value);

                // 최근 수정사항
                if(i % 8 != 0){
                    MusicNoteList.add(array_value);
                    Log.e("배열에 담기는 중인 악보: ", MusicNoteList + "");

                    MusicNoteBitList.add(array_bit_value);
                    Log.e("배열에 담기는 중인 악보박자: ", MusicNoteBitList + "");
                }

//                if (!array_value.equals("O")){
//                    // 남은 계이름들을 마지막으로 배열에 넣음
//                    MusicNoteList.add(array_value);
//                    Log.e("배열에 담기는 중인 악보: ", MusicNoteList + "");
//                }
            }
        }


        Log.e("계이름 수: ", musicnote.length() + " ");
        Log.e("계이름 박자 수: ", musicnote_bit.length() + " ");

        Log.e("최종 배열에 담긴 악보: ", MusicNoteList + "");
        Log.e("최종 배열에 담긴 악보 박자: ", MusicNoteBitList + "");

        // 어댑터 생성자에 악보리스트를 넣음
        mAdapter = new MusicTest_Adapter(MusicNoteList, MusicNoteBitList);

        //
        mRecyclerView.setAdapter(mAdapter);

        // 악보전체를 어댑터에 보냄
        mAdapter.setMusicnote(musicnote);

        // 콜백 리스너
        mAdapter.ThreadFinishListener(MusicTest.this);

        // 중지 버튼 클릭 이벤트
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원곡중지 버튼 클릭 시 발생 메서드
                btn_listen_func_stop();
            }
        });

        // 원곡재생 클릭 이벤트
        btn_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 원곡듣기 버튼 클릭 시 발생 메서드
                btn_listen_func();
            }
        });

    }
    // 원곡중지 버튼 클릭 시 발생 메서드
    public void btn_listen_func_stop(){
        Log.e("노래중지", "중지중지");
        Log.e("현재 index_value값: ", index_value + "");

        // 연주 중지된 시점의 hight_position값을 담는 변수 / 필요 xx
        // index_value_stop = index_value;
        thread.interrupt();
        btn_stop.setVisibility(View.INVISIBLE);
        btn_listen.setVisibility(View.VISIBLE);
    }

    // 원곡듣기 버튼 클릭 시 발생 메서드
    public void btn_listen_func(){
        btn_listen.setVisibility(View.INVISIBLE);
        btn_stop.setVisibility(View.VISIBLE);
        Log.e("원곡재생 클릭", "~~~");

        thread = new music_thread();
        thread.start();
    }

    // 노래가 완전 끝났을 시 콜백함수로 finish라는 값이 넘어옴
    @Override
    public void onMusicFinish(String finish, Context context) {
        Log.e("어댑터서 넘어온 값: ", finish);
        if (finish != null){
            // bool_music를 다시 true로 바꾸어 줌
            bool_music = true;

            btn_listen.setVisibility(View.VISIBLE);
            btn_stop.setVisibility(View.INVISIBLE);

            // 악보 포커싱을 초기화 해줌
            focusing_cnt = 0;
        }
    }

    // 노래를 1초마다 실행시키는 스레드
    class music_thread extends Thread{

        @Override
        public void run() {
            super.run();
            Log.e("MusicNoteList사이즈: ", MusicNoteList.size() + "");


                try {

                    if (bool_music){
                        Log.e("bool_music값: ", bool_music + "");
                        for (int i = 1; i < array.length+1; i++){
                            // bool_music가 true일 때. 연주가 처음부터 연주될 때

                            // 초랑 hight_position값
                            index_value = i;


                            // 핸들러를 통해 UI를 바꿈
                            handler.sendEmptyMessage(1);

                            Log.e("index_value == i: ", index_value + "");
                            Log.e("array.length + 1: ", array.length + 1+ "");
                            //Log.e("musicnote_eng_array[i]: ",musicnote_eng_array[i] + "");

                            if (i == array.length){
                                break;
                            }

                            if (!musicnote_eng_array[i].equals(" ")) { //빈칸이 아닐때
                                // 아두이노로 블루투스 통신으로 음계를 보냄
                                //String sendMsg = musicnote_eng_array[i] + music.getBeat()[i-1];
                                String sendMsg = musicnote_eng_array[i] + music.getBeat()[i-1];
                                Log.i("testLog", "sendMsg "+sendMsg);
                                //Log.i("testLog", "music.getBeat()[i-1] :"+music.getBeat()[i-1] );
                                sendMessage(sendMsg);
                            }

                            //블루투스통신
                            Log.i("testLog", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + i + "rtest " + musicnote_eng_array[i]);

                            // 1초씩 딜레이를 줌
                            Thread.sleep(800);
                        }
                    }else{
                        for (int i = index_value; i < array.length+1; i++){
                            // bool_music가 true일 때. 연주가 처음부터 연주될 때

                            // 초랑 hight_position값

                            //index_value = i+1;   // 노래중지 시 다시 연주할때 다음 position값으로 이동
                            index_value = i;      //노래중지 시 다시 연주할때 현재 position값으로 이동
                            Log.e("index_value: ", index_value + "");

                            // 핸들러를 통해 UI를 바꿈
                            handler.sendEmptyMessage(1);

                            if (i == array.length){
                                break;
                            }

                            if (!musicnote_eng_array[i].equals(" ")) {
                                // 아두이노로 블루투스 통신으로 음계를 보냄
                                String sendMsg = musicnote_eng_array[i] + music.getBeat()[i-1];
                                Log.i("testLog", "sendMsg "+sendMsg);
                                //sendMessage(musicnote_eng_array[i]);
                                sendMessage(sendMsg);
                            }

                            //블루투스통신
                            Log.i("testLog", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + i + "rtest " + musicnote_eng_array[i]);

                            // 1초씩 딜레이를 줌
                            Thread.sleep(800);
                        }
                    }


                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Log.e("연주 스레드 중지", "중지");
                    bool_music = false;
                    e.printStackTrace();
                }




        }
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1){
                mAdapter.setHight_pos(index_value);
                mAdapter.notifyDataSetChanged();

                // 포커싱을 맞추기 위한 제어문
                if(index_value != 1){
                    // index_value가 8로 나누었을 시 나머지가 1일 때
                    if (index_value % 8 == 1){
                        Log.e("8나머지값: ", index_value % 8 + "");
                        focusing_cnt += 1;
                    }
                }
                // 현재 연주중인 음계를 포커싱을 맞추어 줌
                mRecyclerView.smoothScrollToPosition(focusing_cnt);
            }
        }
    };




//TODO 쓰레드 중지


    //=-=-=

    @Override
    protected void onDestroy() {
        super.onDestroy();
//            if ( mConnectedTask != null ) {
//                mConnectedTask.cancel(true);
//            }
        sendMessage("N");  //노말 모드로

        if (thread != null){
            thread.interrupt();
        }

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



}