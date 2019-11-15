package com.example.pianoadroid;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
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
    private final int WRITE_NEW = 111;//작곡하기 -> 새로만들기
    private final int WRITE_OLD = 222;//작곡하기 -> 기존곡

    private RecyclerView mMakeNoteRecycler;
    private MakeMusic_RecyclerAdapter adapter;
    private Button mPlayBtn,mSaveBtn;
    private TextView mTitleText;
    ArrayList<String> makeNotsArr;
    ArrayList<Integer> makeBeatArr;

    // 기존곡의 악보 사이즈
    int beforeSize;
    //무엇을 눌러 왔는지 (새로만들기/ 기존곡)인지
    int writeType;
    //기존곡 뮤직 객체
    Music oldmusic;

    //BlueTooth
    private static String mConnectedDeviceName = null;
    static boolean isConnectionError = false;
    ConnectedTask mConnectedTask = null;

    //SQLite db 개체 생성
    DBMyProductHelper_Write db;

    //쓰레드 관련
    Thread thread;
    boolean isThread = false;
    int count; // 리사이클러뷰 자리수 증가해서 배경색 변경
    int stopCnt =0; // 재생하기에서 멈춤한 자리 포커스값
    int focusing_cnt = 0; //리사이클러뷰 포커싱

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_make_music);

        ImageView mBackBtn = (ImageView)findViewById(R.id.back_btn);
        mSaveBtn = (Button)findViewById(R.id.saveBtn);
        mPlayBtn =(Button)findViewById(R.id.palyBtn);
        mTitleText = (TextView)findViewById(R.id.titleText);

        //SQLite db helper init 초기화
        db = new DBMyProductHelper_Write(this);

        //인텐트로 뭐 눌러왔는지 받기..
        Intent receiveIntent = getIntent();
        writeType = receiveIntent.getIntExtra("from", WRITE_NEW);//기본값은 새로만들기

        if(writeType == WRITE_NEW){ //새로만들기 버튼을 눌러 왔음 [+]

            //ArrayList를 사용해야하고

            makeNotsArr = new ArrayList<>();
            makeBeatArr = new ArrayList<>();

            infoshow("확인","no","건반을 눌러주세요:)");// 안내 다이얼로그

            //빈오선지일 때 버튼 비활성화
            mPlayBtn.setEnabled(false);
            mSaveBtn.setEnabled(false);

        }else if(writeType == WRITE_OLD) { //기존 곡을 눌러 왔음....

            //기존곡이기 때문에 수정전에는 비활성화
            mSaveBtn.setEnabled(false);

            //인텐트에서 뭐 눌렀는지 아이디 받기
            int mid = receiveIntent.getIntExtra("id", 1);
            //db에서 id로 Music 개체를 꺼내 사용해야함
             oldmusic = db.getMusic(mid);

            makeNotsArr = oldmusic.getScoreArr();
            makeBeatArr = oldmusic.getBeatArr();

            // 기존곡의 악보 사이즈 저장
            beforeSize = makeNotsArr.size();

            // 기존곡의 타이틀
            mTitleText.setText(oldmusic.getTitle());
            mTitleText.setVisibility(View.VISIBLE);

        }

        //리사이클러뷰 초기 세팅
        init(makeNotsArr, makeBeatArr );

        // 블루투스 소켓연결
        mConnectedTask = new ConnectedTask(SocketHandler.getmBluetoothsocket(),SocketHandler.getmDeviceName());
        mConnectedTask.execute();


        //아두이노로 작곡모드 보내기
        sendMessage("W");

        //백버튼 누르면 뒤로가는 이벤트 붙임
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 빈 악보 가 아닐때  띄워줌
                if (makeNotsArr.size() != 0) {
                    // 이전 곡이 아닌 새로운 곡일 경우
                    if(beforeSize == 0) {
                        infoshow("아니오", "ok", "악보를 저장하지 않고 나가시겠습니까? ");
                        // 이전 곡인데 수정이 되었을 경우

                    } else if(beforeSize != makeNotsArr.size()){
                        infoshow("아니오", "ok", "수정된 내용을 저장하지 않고 나가시겠습니까? ");
                    }else{
                        // 이전 곡인데 수정되지 않았을 경우
                        finish();
                    }
                }else{
                    //빈악보 일경우
                    finish();
                }

            }
        });


        // 작곡된 음악 play 버튼
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(mPlayBtn.getText().toString().equals("재 생 하 기")){
                   // 연주모드로 아두이노에 보냄
                   mPlayBtn.setText("중 지 하 기");
                   sendMessage("P");
                    isThread = true;
                    thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //아두이노에서 모드를 바꿀 때 딜레이를 줘야 가끔 포커싱 안맞는게 없어짐
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // 정지된  음표 자리값 가져와서함 count에 넘어서 시작함
                            count =stopCnt;
                            while (isThread){
                                Message msg = handler.obtainMessage();
                                // count가 증가하므로 음표 배경색이 바뀜
                                msg.arg1 = count;
                                // 핸들러 호출
                                handler.sendMessage(msg);

                                // 배열의 마지막값을 체크해서  whilw문 정지 시킴
                                // 안그러면 count 증가되서 에러
                                if(makeNotsArr.size() <= count){
                                    // 작곡모드로
                                    sendMessage("W");
                                    focusing_cnt =0;
                                    stopCnt = 0;
                                    break;// while 정지
                                }
                                Log.i("testLog_MakeMusic", "noteArrsize g확인2222");
                                    try {
                                        // 음계 배열에서 공란일때 아두이노에 보내지 않음
                                        if (!makeNotsArr.get(count).equals(" ")) {
                                            // 아두이노로 블루투스 통신으로 음계를 보냄
                                            //현재 계이름과 비트를 (ex)C1)
                                            String sendMsg =makeNotsArr.get(count) + makeBeatArr.get(count);
                                            Log.i("testLog_MakeMusic", "sendMsg "+sendMsg);
                                            sendMessage(sendMsg);
                                            // 딜레이
                                            Thread.sleep(800);
                                        }


                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                //쓰레드 위치값 늘리기
                                count++;
                                }
                        }
                    });
                    thread.start();
                // 중지하기 눌렀을 경우 재생하기로 바꿔주기
               }else if(mPlayBtn.getText().toString().equals("중 지 하 기") ){
                   mPlayBtn.setText("재 생 하 기");
                   Log.i("testLog", "정지된 쓰레드의 list값: stopCnt" + count);
                   stopCnt = count;
                   isThread = false;
                   thread.interrupt();
                   sendMessage("W");

               }
            }
        });

        //작곡된 노래 play list 에 저장
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("testLog", "makeNotsArr.size() :" + makeNotsArr.size());
                if(makeNotsArr.size() > 0){ //빈오선기가 아님 , 새로만들기 + 수정한게 여기로 들어오지
                    if(beforeSize == 0) {// 새로운곡
                        //제목 , 작곡자 입력 dialog
                        show();  // 제목 , 작곡자 입력  dialog-->db 저장 여기서 진행
                    }
                    else if(beforeSize != makeNotsArr.size()){ // 수정된 곡
                        saveshow();

                }
                }else if (makeNotsArr.size() == 0){
                    // 빈오선지 일경우 저장이 안됨
                    Toast.makeText(MakeMusic.this,"작곡된 음표가 없습니다 작곡 후 저장하세요!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 배열의 마지막값을 체크해서  whilw문 정지 시킴
            // 안그러면 count 증가되서 에러
            if(makeNotsArr.size() <= count) {
                mPlayBtn.setText("재 생 하 기");
                mMakeNoteRecycler.smoothScrollToPosition(0);
                adapter.setHighlightPos(-11);
                adapter.notifyDataSetChanged();

            }else{
                adapter.setHighlightPos(msg.arg1); //
                adapter.notifyDataSetChanged();
                // 포커싱을 맞추기 위한 제어문
                if(msg.arg1 != 1){
                    // index_value가 8로 나누었을 시 나머지가 1일 때
                    if (msg.arg1 % 8 == 1){
                        Log.e("8나머지값: ", msg.arg1 % 8 + "");
                        focusing_cnt += 1;
                    }
                }
                // 현재 연주중인 음계를 포커싱을 맞추어 줌
                mMakeNoteRecycler.smoothScrollToPosition(focusing_cnt);
            }

        }
    };
    @Override
    public void onBackPressed() {
        // 빈 악보 가 아닐때  띄워줌
        if (makeNotsArr.size() != 0) {
            // 이전 곡이 아닌 새로운 곡일 경우
            if(beforeSize == 0) {
                infoshow("아니오", "ok", "악보를 저장하지 않고 나가시겠습니까? ");
            // 이전 곡인데 수정이 되었을 경우
            } else if(beforeSize != makeNotsArr.size()){
                infoshow("아니오", "ok", "수정된 내용을 저장하지 않고 나가시겠습니까? ");
            }else{
                // 이전 곡인데 수정되지 않았을 경우
                super.onBackPressed();//finish
            }
        }else{
            //빈악보 일경우
            super.onBackPressed();//finish
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( mConnectedTask != null ) {
            mConnectedTask.cancel(true);
        }
        if (thread != null) {
            //  재생하기 중지 시키기
            isThread = false;
            thread.interrupt();
        }
        sendMessage("N");  //노말 모드로
    }


    //리사이클러뷰 초기 세팅//
    private void init(ArrayList<String> makeNotsArr, ArrayList<Integer> makeBeatArr) {
        mMakeNoteRecycler = (RecyclerView)findViewById(R.id.makenote);
        mMakeNoteRecycler.setHasFixedSize(true);// 불필요한 레이아웃과정을  피하게 만들어줌
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mMakeNoteRecycler.setLayoutManager(linearLayoutManager);

        //makeNotsArr = new ArrayList<>(); // 계이름
        //makeBeatArr = new ArrayList<>(); // 비트

        adapter = new MakeMusic_RecyclerAdapter(makeNotsArr,makeBeatArr,-11);
        mMakeNoteRecycler.setAdapter(adapter);
    }

    //아두이노로 데이터 보내기
    void sendMessage(String msg){

        if ( mConnectedTask != null ) {
            mConnectedTask.write(msg.trim());
            Log.d(TAG, "메세지 전송 : " + msg);
        }
    }


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
    public  class ConnectedTask extends AsyncTask<Void, String, Boolean> {
        private InputStream mInputStream = null;
        private OutputStream mOutputStream = null;
        private BluetoothSocket mBluetoothSocket = null;
        int chBeat ; // 아두이노에서 받아온 millise 컨버터하여 int형 박자로 바꿈

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

        //doInBackground :  정의한 AsyncTask를 execute 할 때 전해줄 값의 종류
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
                                // 리사이클러뷰의 아답타에 데이터 보내기
                                // 받아온 계이름과 milllis를 String 배열로 나눔 (ex..C,134로 들어옴)
                                //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
                                String[] note_split = recvMessage.trim().split(",");
//                                Log.d(TAG, "make splite recv message: " + note_split[0]);//계이름
//                                Log.d(TAG, "make splite recv message Beat: " + convertBeat(note_split[1])+"박//");//박자

                                // 박자 컨버터 메소드에서 millis에서 1로 반환
                                chBeat  = convertBeat(note_split[1]); // 박자 컨버터 1박이면 1보냄
                                makeNotsArr.add(note_split[0]); //CC  A
                                makeBeatArr.add(chBeat);        //13001

                                //==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
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

        //onProgressUpdate : 진행상황을 업데이트할 때 전달할 값의 종류
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

            if(writeType == WRITE_NEW) {
                //오선지에 음표가 들어왔을 경우 활성화
                if (makeNotsArr.size() == 1) {
                    mPlayBtn.setEnabled(true);  // 재생하기
                    mSaveBtn.setEnabled(true);  // 저장하기 버튼
                }
            }else if(writeType == WRITE_OLD){
                mSaveBtn.setEnabled(true);
            }

        }

        //onPostExecute :  AsyncTask 가 끝난 뒤  결과값의 종류
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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
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

        // 종료 하겠다고 하면 액티비티 종료됨
        if(okFinish.equals("ok")){
            builder.setNegativeButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
        builder.show();
    }

    //저장
    void saveshow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("수정 사항을 저장 하시겠습니까 ?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        oldmusic.setBeat(readBeat());
                        oldmusic.setScore(readNots());
                        db.updateMusic(oldmusic);

                        //플레이 리스트로
                        Intent intent = new Intent();
                        intent.putExtra("result_msg", true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });


            builder.setNegativeButton("아니요",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

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
                String strTitle = title_make.getText().toString(); //제목
                String strName = name_make.getText().toString(); //작곡자
                //Toast.makeText(getApplicationContext(), strTitle+"/"+strName,Toast.LENGTH_LONG).show();


                // 뮤직 객체에 넣고 dblite 메소드에 넣어줄것
                //새로운 노래를(Music 개체를) db에 추가
                //Music(String title, String writer, String score, int[] beat)
                // 노래제목, 작곡자 , 악보, 비트를 저장한다
                //id는 자동으로 됨
                 Music music = new Music(strTitle, strName, readNots(),readBeat());
                 db.addMusic(music);
                 dialog.dismiss();

                //플레이 리스트로
                Intent intent = new Intent();
                intent.putExtra("result_msg", true);
                setResult(RESULT_OK, intent);
                finish();
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
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
    //작곡된 전체 계이름 악보 가져오기
    public String readNots(){
        //노래전체 악보 가져오기
        String sb = "";
        for (int i = 0; i < makeNotsArr.size(); i++) {
                sb += makeNotsArr.get(i);
        }
       // Log.i("MakeMusic_wholeNotes","wholeNotes_확인 :"+sb);
        return sb;
    }
    // 작곡된 전체 계이름 악보 비트 가져오기
    private int[] readBeat(){
        int[] dbBeat = new int[makeBeatArr.size()];
        for(int i=0; i< makeBeatArr.size(); i++){
            dbBeat[i] = makeBeatArr.get(i);
            //Log.i("MakeMusic_전체 비트 확인","전체적인 비트_확인 :"+dbBeat[i]);
        }
        return dbBeat;
    }

    // 아두이노 millis 컨버터
    //눌러진 만큼의 밀리수를 박자로 바꿔줌
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
