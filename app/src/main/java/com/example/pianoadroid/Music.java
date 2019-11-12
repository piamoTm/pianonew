package com.example.pianoadroid;

//연주곡, 작곡곡 으로 사용할 객체 클래스
//필드 :  고유아아디, 노래제목, 작곡가, 코드, 날짜 ,박자
//연주곡에서는 날짜 사용안함.

//연습노래 목록과 작곡노래 목록 리사이클러뷰에서 사용됨.
//SQLite DB에 노래를 넣고 뺄때 사용될것.


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Music {

    private int id;//고유아이디
    private String title; //노래제목
    private String writer; //작곡가
    private String score; //악보 "CCDDCCDD CCFFAA CCCCAA" <-이런 모양
    private Calendar date; //작곡한 날짜
    private int[] beat; //박자
    //"CCGGaaG FFEEDDC GGFFEEDGGFFEED CCGGaaG FFEEDDC " <-코드
    // 11111120111111201111112111111201111112011111120  <-박자
    //코드와 박자는 위아래 매칭됩니다. 코드의 인덱스로 해당 박자를 뽑아낼수있습닌다. (code[i]의 박자는 beat[i] 이렇게)


    //생성자
    public Music(){
        this.date = Calendar.getInstance(); // 기본값으로 오늘을 넣음.
    }

    public Music(String title, String writer, String score, int[] beat) {
        this();
        this.title = title;
        this.writer = writer;
        this.score = score;
        this.beat = beat;
    }

    public Music(int id, String title, String writer, String score, Calendar date, int[] beat) {
        this(id,title,writer,score,beat);
        this.date = date;
    }

    public Music(int id, String title, String writer, String score, int[] beat) {
        this(id,title,writer,score);
        this.beat = beat;
    }

    public Music(int id, String title, String writer, String score) {
        this();
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.score = score;
        //this.date = Calendar.getInstance(); // 기본값으로 오늘을 넣음
    }
    //db용_생성자 (Write)
    public Music(int id, String title, String writer, String score, String dateStr, String beatStr){
        this(id,title,writer,score);
        setDate(dateStr);
        setBeat(beatStr);
    }
    //db용 생성자 (read)
    public Music(int id, String title, String writer, String score,  String beatStr){
        this(id,title,writer,score);
        setBeat(beatStr);
    }

    //getter & setter
    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) { this.title = title; }
    public String getWriter() {return writer;}
    public void setWriter(String writer) {this.writer = writer;}
    public String getScore() {return score;}
    public void setScore(String score) {this.score = score;}
    public Calendar getDate() {return date;}
    public void setDate(Calendar date) {this.date = date;}
    public int[] getBeat() {return beat;}
    public void setBeat(int[] beat) {this.beat = beat;}


    //날짜를 "2019/11/7"형식으로 반환함.
    public String getDateStr(){
        String dateStr = date.get(Calendar.YEAR) + "/"
                +(date.get(Calendar.MONTH)+1) +"/"
                +date.get(Calendar.DATE);
        return dateStr;
    }

    //코드에 음표('C')하나를 악보(score)에 추가
    public void addNote(String note){
        this.score += note;
    }

    //악보의 길이 반환
    public int getScoreLen(){
        return score.length();
    }

    ///////////////////////////////////////////////////////////
    //db Helper에서 사용하는 함수 다른곳에선 사용할때는 매개변수 형식에 유의//
    ///////////////////////////////////////////////////////////

    //dateStr는 yyyy-MM-dd HH:mm:ss형식
    public void setDate (String dateStr){

        //yyyy-MM-dd HH:mm:ss형식으로 들어온 dateStr를 Calendar 개체로 변환
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = (Calendar) cal.clone();
    }
    //db Helper에서 사용하는 함수. 다른곳에서 사용할때는 매개변수 beatStr 형식에 유의
    public void setBeat(String beatStr){
        //Log.i("testLog", "setBeat() :" + beatStr);
        //String 형식으로 들어온 beatStr을 하나씩띄어서 int형 배열로 바꿔서 반환.
        //  "11111120111111201111112111111201111112011111120" -> 1,1,1,1,1,1,2,0,1,1,1,1,1,1,2,0,1,1,1,1...1,2,0

        int[] beatArr = new int[beatStr.length()]; //beatStr의 길이만큼 배열을 만듦.

        String[] beatSplit = beatStr.split(""); //beatStr을 한글자씩 자름.

        for(int i=1; i<beatSplit.length; i++){
            //한글자씩 자른 beatStr을(String type) -> beatArr배열에 Int로 바꿔 넣는다.
            beatArr[i-1] = Integer.parseInt(beatSplit[i]);
            //Log.i("testLog", "["+(i-1)+"] " + beatArr[i-1]);
        }
        this.beat = beatArr;
    }

    public String getBeatStr(){
        //int[] beat를 String 형식으로 바꿔서 반환
        //  1,1,1,1,1,1,2,0,1,1,...1,2,0 -> "11111120111111201111112111111201111112011111120"
        String beatStr = "";

        for (int b: beat
             ) {
            beatStr += b+"";
        }
        return beatStr;
    }


    //DB용입니다. 사용하지마세요.//
    public String getDateStrForDB(){
        //db에 저장할때 사용하려고 만든 함수.

        //Calendar -> Text :Calendar개체에 든 날짜를 text형식으로 바꾸기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Calendar.getTime() : Calendar객체가 저장하고 있는 시간을 Date형태로 리턴.
        return dateFormat.format(date.getTime());
    }


}
