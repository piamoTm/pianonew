package com.example.pianoadroid;

import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//다른 소리내는 액티비티
//여기에서 버튼으로 [고양이] [피아노] [기타] 이런 메뉴버튼들이 보이고
//[고양이]를 선택하면 소리가 고양이로 바뀌는거임
//그래서 [고양이] 버튼을 눌러 버튼이 활성화상태일때 아두이노 피아노를 누르면
//안드로이드에서 고양이 소리가 나는거지 (아두이노에서는 빛만)

public class SoundChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private SoundPool soundPool;
    private int[] soundID;
    private int[] catSoundID;
    private int[] pianoSoundId;
    private int[] xylophoneSoundID;

    private final int DO = 0, RE = 1, MI = 2,FA = 3,SOL = 4,RA = 5, SI = 6,HDO = 7;

    //BlueTooth
    private static String mConnectedDeviceName = null;
    private static boolean isConnectionError = false;
    private ConnectedTask mConnectedTask = null;
    private static final String TAG = "soundChange";

    private Button[] buttons; //버튼들
    private LinearLayout[] lay_buttons;// 레이아웃 버튼들
    private boolean[] isClicked;// 버튼 활성화
    private final int cat = 0; //버튼활성화 배열,버튼배열 인덱스로 사용할거야
    private final int piano = 1;
    private final int xylophone = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        soundID = new int[8];
        catSoundID = new int[8];
        pianoSoundId = new int[8];
        xylophoneSoundID = new int[8];

        //고양이소리
        catSoundID[DO] = soundPool.load(this, R.raw.yattong_do,1);
        catSoundID[RE] = soundPool.load(this, R.raw.yattong_re,1);
        catSoundID[MI] = soundPool.load(this, R.raw.yattong,1);
        catSoundID[FA] = soundPool.load(this, R.raw.yattong_fa,1);
        catSoundID[SOL] = soundPool.load(this, R.raw.yattong_sol,1);
        catSoundID[RA] = soundPool.load(this, R.raw.yattong_ra,1);
        catSoundID[SI] = soundPool.load(this, R.raw.yattong_si,1);
        catSoundID[HDO] = soundPool.load(this, R.raw.yattong_hdo,1);
        //피아노소리
        pianoSoundId[DO] = soundPool.load(this, R.raw.sound1,1);
        pianoSoundId[RE] = soundPool.load(this, R.raw.sound2,1);
        pianoSoundId[MI] = soundPool.load(this, R.raw.sound3,1);
        pianoSoundId[FA] = soundPool.load(this, R.raw.sound4,1);
        pianoSoundId[SOL] = soundPool.load(this, R.raw.sound5,1);
        pianoSoundId[RA] = soundPool.load(this, R.raw.sound6,1);
        pianoSoundId[SI] = soundPool.load(this, R.raw.sound7,1);
        pianoSoundId[HDO] = soundPool.load(this, R.raw.sound8,1);
        //실로폰소리
        xylophoneSoundID[DO] = soundPool.load(this, R.raw.xylophone1,1);
        xylophoneSoundID[RE] = soundPool.load(this, R.raw.xylophone2,1);
        xylophoneSoundID[MI] = soundPool.load(this, R.raw.xylophone3,1);
        xylophoneSoundID[FA] = soundPool.load(this, R.raw.xylophone4,1);
        xylophoneSoundID[SOL] = soundPool.load(this, R.raw.xylophone5,1);
        xylophoneSoundID[RA] = soundPool.load(this, R.raw.xylophone6,1);
        xylophoneSoundID[SI] = soundPool.load(this, R.raw.xylophone7,1);
        xylophoneSoundID[HDO] = soundPool.load(this, R.raw.xylophone8,1);

        // 블루투스 소켓연결
        mConnectedTask = new ConnectedTask(SocketHandler.getmBluetoothsocket(),SocketHandler.getmDeviceName());
        mConnectedTask.execute(); //쓰레드실행

        int size = 3;

        //버튼 연결
        lay_buttons  = new LinearLayout[size];
        lay_buttons[cat] = (LinearLayout) findViewById(R.id.catlay_btn);
        lay_buttons[piano] = (LinearLayout)findViewById(R.id.pianolay_btn);
        lay_buttons[xylophone] = (LinearLayout)findViewById(R.id.xylolay_btn);
        lay_buttons[cat].setOnClickListener(this);
        lay_buttons[piano].setOnClickListener(this);
        lay_buttons[xylophone].setOnClickListener(this);
        //버튼 연결 ..이전
        buttons = new Button[size];
        buttons[cat] = (Button) findViewById(R.id.btn_cat);
        buttons[piano] = (Button)findViewById(R.id.btn_piano);
        buttons[xylophone] = (Button)findViewById(R.id.btn_xylophone);


        //버튼 비활성화 init
        isClicked = new boolean[size];
        for (boolean b: isClicked) {
            b = false;
        }

        ImageView backpressBtn = (ImageView)findViewById(R.id.backpressBtn2);
        backpressBtn.setOnClickListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mConnectedTask.cancel(true); //쓰레드종료
        sendMessage("N"); //아두이노를 기본모드로
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.catlay_btn : //고양이버튼 누름
                Log.i("testLog", "고양이");
                if(isClicked[cat]){ //활성화 -> 비활성화
                    soundID = null;
                    sendMessage("N"); //아두이노를 작곡모드로
                    buttons[cat].setTextColor(Color.BLACK);
                    isClicked[cat] = false;

                }else{ //비활성화->활성화
                    soundID = catSoundID;
                    sendMessage("S"); //아두이노를 작곡모드로
                    buttons[cat].setTextColor(Color.BLUE);
                    isClicked[cat] = true;
                    setAllCancelWithoutN(cat); //고양이 말고 다른건 모두 해제
                }
                break;

            case R.id.pianolay_btn :
                Log.i("testLog", "클래식피아노");
                if(isClicked[piano]){ //활성화 -> 비활성화
                    soundID = null;
                    sendMessage("N"); //아두이노를 작곡모드로
                    buttons[piano].setTextColor(Color.BLACK);
                    isClicked[piano] = false;

                }else{ //비활성화->활성화
                    soundID = pianoSoundId;
                    sendMessage("S"); //아두이노를 작곡모드로
                    buttons[piano].setTextColor(Color.BLUE);
                    isClicked[piano] = true;
                    setAllCancelWithoutN(piano); //피아노 말고 다른건 모두 해제
                }
                break;

            case R.id.xylolay_btn :
                Log.i("testLog", "실로폰");
                if(isClicked[xylophone]){ //활성화 -> 비활성화
                    soundID = null;
                    sendMessage("N"); //아두이노를 작곡모드로
                    buttons[xylophone].setTextColor(Color.BLACK);
                    isClicked[xylophone] = false;

                }else{ //비활성화->활성화
                    soundID = xylophoneSoundID;
                    sendMessage("S"); //아두이노를 작곡모드로
                    buttons[xylophone].setTextColor(Color.BLUE);
                    isClicked[xylophone] = true;
                    setAllCancelWithoutN(xylophone); //피아노 말고 다른건 모두 해제
                }
                break;

            case R.id.backpressBtn2:
                finish();
                break;
        }
    }

    //한꺼번에 비활성화 시키는 버튼 n으로 들어온거만 빼고
    void setAllCancelWithoutN(int n){
        for (int i = 0; i<isClicked.length; i++){
            if(i != n){
                isClicked[i]=false;
                buttons[i].setTextColor(Color.BLACK);
            }
        }
    }


    //소리내기
    void sound(String code){
        if(soundID == null){
            return;
        }
        switch (code){
            case "C" :
                soundPool.play(soundID[DO],1,1,0,0,1);
                break;
            case "D" :
                soundPool.play(soundID[RE],1,1,0,0,1);
                break;
            case "E" :
                soundPool.play(soundID[MI],1,1,0,0,1);
                break;
            case "F" :
                soundPool.play(soundID[FA],1,1,0,0,1);
                break;
            case "G" :
                soundPool.play(soundID[SOL],1,1,0,0,1);
                break;
            case "A" :
                soundPool.play(soundID[RA],1,1,0,0,1);
                break;
            case "B" :
                soundPool.play(soundID[SI],1,1,0,0,1);
                break;
            case "H" :
                soundPool.play(soundID[HDO],1,1,0,0,1);
                break;
        }
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
    //아두이노로 데이터 보내기
    void sendMessage(String msg){

        if ( mConnectedTask != null ) {
            mConnectedTask.write(msg.trim());
            Log.d(TAG, "메세지 전송 : " + msg);
        }
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
    //아두이노가 블루투스로 보내는 데이터를 받는 부분//
    public class ConnectedTask extends AsyncTask<Void, String, Boolean> {
        private InputStream mInputStream = null;
        private OutputStream mOutputStream = null;
        private BluetoothSocket mBluetoothSocket = null;

        //블루투스 소켓 연결
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
                if ( isCancelled() ) { //AsyncTask가 종료됨
                    //return false; //왜 false를 반환하지? false반환하면 꺼지는지?
                    //Result : doInBackground 메소드를 통해서 리턴되고, onPostExecute(Result r) 메소드의 인자값으로 들어오게 되는 값
                    //출처: https://androphil.tistory.com/359 [소림사의 홍반장!]
                    return false;
                }
                try {
                    int bytesAvailable = mInputStream.available();
                    if(bytesAvailable > 0) { //받은게 있다.
                        byte[] packetBytes = new byte[bytesAvailable]; //받은거 크기만큼 바이트배열 만들고
                        mInputStream.read(packetBytes); //받은거 읽음

                        for(int i=0; i<bytesAvailable; i++) {
                            byte b = packetBytes[i]; //한글자씩 꺼내
                            if(b == '\n') //개행문자면 (==끝이면)
                            {
                                byte[] encodedBytes = new byte[readBufferPosition];
                                System.arraycopy(readBuffer, 0, encodedBytes, 0,
                                        encodedBytes.length);

                                String recvMessage = new String(encodedBytes, "UTF-8"); //받은거 String에 넣고 인코더까지함. 이거쓰면됨.

                                readBufferPosition = 0; //다시 처음으로 (읽을거 다 읽었으니까)

                                Log.i("testLog", "recv message: " + recvMessage);
                                publishProgress(recvMessage); //onProgressUpdate()로 recvMessage를 넘김.
                                //return recvMessage;

                            }
                            else
                            {
                                //개행문자 아니면
                                readBuffer[readBufferPosition++] = b; //다음꺼 꺼내
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
            //진행중에 해당하는 값을 가지고 처리하고 싶은 일을 실행하는 메소드(일반적으로 프로그레스바의 값을 증가시키기 위해 사용)
            //출처: https://androphil.tistory.com/359 [소림사의 홍반장!]

            //recvMessage[0] = "C,123" ; //이렇게 옴
            Log.i("testLog", "[SoundChange] splite recv message: " + recvMessage[0]);

            String[] msg_split = recvMessage[0].trim().split(","); //recvMessage를 ','로 잘러
            Log.i("testLog", "[SoundChange] splite recv message: " + msg_split[0]);

            sound(msg_split[0]);


        }

        @Override //결과값을 가지고 처리하고 싶은 일을 실행하는 메소드
        protected void onPostExecute(Boolean isSucess) {
            super.onPostExecute( isSucess);

            if ( !isSucess ) {
                closeSocket();
                Log.d(TAG, "장치 연결이 끊어졌습니다");
                isConnectionError = true;
                showErrorDialog("장치 연결이 끊어졌습니다");
            }

            //recvMessage = "C,123" ; //이렇게 올거
            //String[] msg_split = recvMessage.trim().split(","); //recvMessage를 ','로 잘러
            //Log.i("testLog", "[SoundChange] splite recv message: " + msg_split[0]);

            //sound(msg_split[0]);
            //여기서 처리할려면 doInBackground에서 return 시켜야하는데 그럼 쓰레드가 끝나서 여기서 처리안하고
            //onProgressUpdate 여기에서 받아서 처리해보려

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

    //블루투스와의 연결이 끊겼을경우 다이어로그 띄워줄려고.
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
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
}
