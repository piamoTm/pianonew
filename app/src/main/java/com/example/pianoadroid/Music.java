package com.example.pianoadroid;

//연주곡, 작곡곡 으로 사용할 객체 클래스
//필드 :  고유아아디, 노래제목, 작곡가, 코드, 날짜
//연주곡에서는 날짜 사용안함.

//연습노래 목록과 작곡노래 목록 리사이클러뷰에서 사용됨.
//SQLite DB에 노래를 넣고 뺄때 사용될것.


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Music {

    private int id;//고유아이디
    private String title; //노래제목
    private String writer; //작곡가
    private String code; //코드
    private Calendar date; //작곡한 날짜

    public Music(int id, String title, String writer, String code, Calendar date) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.code = code;
        this.date = date;
    }

    public Music(int id, String title, String writer, String code) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.code = code;
        this.date = Calendar.getInstance(); // 기본값으로 오늘을 넣음.
    }

    public Music(int id, String title, String writer, String code, String dateStr) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.code = code;

        //String dateStr -> Calendar cal
        //  yyyy-MM-dd HH:mm:ss형식으로 들어온 dateStr를 Calendar 개체로 변환
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = (Calendar) cal.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    //날짜를 2019/11/7 형식으로 반환
    public String getDateStr(){
        String dateStr = date.get(Calendar.YEAR) + "/"
                +(date.get(Calendar.MONTH)+1) +"/"
                +date.get(Calendar.DATE);
        return dateStr;
    }

    //DB용입니다. 사용하지마세요.
    public String getDateStrForDB(){
        //db에 저장할때 사용하려고 만든 함수.

        //Calendar -> Text :Calendar개체에 든 날짜를 text형식으로 바꾸기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Calendar.getTime() : Calendar객체가 저장하고 있는 시간을 Date형태로 리턴.
        return dateFormat.format(date.getTime());
    }
}
