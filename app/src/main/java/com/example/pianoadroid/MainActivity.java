package com.example.pianoadroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity  implements View.OnTouchListener{

    //좌표를 출력할 텍스트뷰
 //   private TextView mTvCoord;
    //터치 위에 원이 그려지는 사용자 정의 뷰
    private TipsView mTipsView,mTipsView2,mTipsView3,mTipsView4,mTipsView5,mTipsView6,mTipsView7,mTipsView8,mTipsView9;

    //터치 이벤트의 좌표를 받아올 변수//juh2Test
    private float x = -1, y = -1;
    //최상단 RelativeLayout
    private RelativeLayout RL,RL2,RL3,RL4,RL5,RL6,RL7,RL8,RL9;
    private ImageView imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //xml에 정의한 뷰들을 불러옴
//        RL = (RelativeLayout)findViewById(R.id.RL);
//        RL2 = (RelativeLayout)findViewById(R.id.RL2);
//        RL3 = (RelativeLayout)findViewById(R.id.RL3);
//        RL4 = (RelativeLayout)findViewById(R.id.RL4);
//        RL5 = (RelativeLayout)findViewById(R.id.RL5);
//        RL6 = (RelativeLayout)findViewById(R.id.RL6);
//        RL7 = (RelativeLayout)findViewById(R.id.RL7);
//        RL8 = (RelativeLayout)findViewById(R.id.RL8);
//        RL9 = (RelativeLayout)findViewById(R.id.RL9);
//        //mTvCoord = (TextView)findViewById(R.id.tvCoord);
//        mTipsView = (TipsView)findViewById(R.id.tipsView);
//        mTipsView2 = (TipsView)findViewById(R.id.tipsView2);
//        mTipsView3 = (TipsView)findViewById(R.id.tipsView3);
//        mTipsView4 = (TipsView)findViewById(R.id.tipsView4);
//        mTipsView5 = (TipsView)findViewById(R.id.tipsView5);
//        mTipsView6 = (TipsView)findViewById(R.id.tipsView6);
//        mTipsView7 = (TipsView)findViewById(R.id.tipsView7);
//        mTipsView8 = (TipsView)findViewById(R.id.tipsView8);
//        mTipsView9 = (TipsView)findViewById(R.id.tipsView9);
       // imgs =(ImageView)findViewById(R.id.RefirImage);
        //텍스트뷰에 텍스트 삽입
       // mTvCoord.setText("Touch!!!");
        //Tips뷰를 터치 클릭 리스너 등록
        mTipsView.setOnTouchListener(this);
        mTipsView2.setOnTouchListener(this);
        mTipsView3.setOnTouchListener(this);
        mTipsView4.setOnTouchListener(this);
        mTipsView5.setOnTouchListener(this);
        mTipsView6.setOnTouchListener(this);
        mTipsView7.setOnTouchListener(this);
        mTipsView8.setOnTouchListener(this);
        mTipsView9.setOnTouchListener(this);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dd);

       // imgs.setImageBitmap(bitmap);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            //Down이 발생한 경우
            case MotionEvent.ACTION_DOWN :

                x = event.getX();
                y = event.getY();

                //내가 누른 자리의 좌표를 String 값으로 표현할 변수
                String str;
                str = "Coordinate : ( " + (int)x + ", " + (int)y + " )";
              //  mTvCoord.setText(str);
                //터치 한 곳에 이미지를 표현하기 위해 동적으로 ImageView 생성
//                ImageView img = new ImageView(this);
//                img.setImageResource(R.drawable.crotchet_1);
//
//
//                //이미지가 저장될 곳의 x,y좌표를 표현
//                img.setX(x-40);
//                img.setY(y - 90);
//                //최상단 릴레이티브 레이아웃에 이미지를 Add
//                RL.addView(img);
                Log.i("SDFSDFSDf", "sdfsdfsdfds"+ v.getTag());
//                if(v.getId() == R.id.tipsView) {
//                    ImageView img = new ImageView(this);
//                    img.setImageResource(R.drawable.crotchet_1);
//
//
//                    //이미지가 저장될 곳의 x,y좌표를 표현
//                    img.setX(x - 40);
//                    img.setY(y - 90);
//                  //  Log.i("SDFSDFSDf", img.getX());
//                    RL.addView(img);
//                }//else if()
//                        break;
//        case R.id.RL2:
//        ImageView img2 = new ImageView(this);
//        img2.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img2.setX(x-40);
//        img2.setY(y - 90);
//        RL2.addView(img2);
//        break;
//        case R.id.RL3:
//        ImageView img3 = new ImageView(this);
//        img3.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img3.setX(x-40);
//        img3.setY(y - 90);
//        RL3.addView(img3);
//        break;
//        case R.id.RL4:
//        ImageView img4 = new ImageView(this);
//        img4.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img4.setX(x-40);
//        img4.setY(y - 90);
//        RL4.addView(img4);
//        break;
//        case R.id.RL5:
//        ImageView img5 = new ImageView(this);
//        img5.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img5.setX(x-40);
//        img5.setY(y - 90);
//        RL5.addView(img5);
//        break;
//        case R.id.RL6:
//        ImageView img6 = new ImageView(this);
//        img6.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img6.setX(x-40);
//        img6.setY(y - 90);
//        RL6.addView(img6);
//        break;
//        case R.id.RL7:
//        ImageView img7 = new ImageView(this);
//        img7.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img7.setX(x-40);
//        img7.setY(y - 90);
//        RL7.addView(img7);
//        break;
//        case R.id.RL8:
//        ImageView img8 = new ImageView(this);
//        img8.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img8.setX(x-40);
//        img8.setY(y - 90);
//        RL8.addView(img8);
//        break;
//        case R.id.RL9:
//        ImageView img9 = new ImageView(this);
//        img9.setImageResource(R.drawable.crotchet_1);
//
//
//        //이미지가 저장될 곳의 x,y좌표를 표현
//        img9.setX(x-40);
//        img9.setY(y - 90);
//        RL9.addView(img9);
//        break;
//    }
                break;
            //Up이 발생한 경우
            case MotionEvent.ACTION_UP :
              //  mTvCoord.setText("Touch !!!");
                break;

        }
        //false를 반환하여 뷰 내에 재정의한 onTouchEvent 메소드로 이벤트를 전달한다
        return false;
    }


}
