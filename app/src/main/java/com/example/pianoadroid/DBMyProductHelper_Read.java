package com.example.pianoadroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

//연습노래 SQLite DB에 넣기위한 클래스 (지환씨가 사용할 부분)
//이 클래스 개체를 통해 노래를 db에 등록,수정,삭제 할거임.

//주요 함수는 onCreate, onUpgrade, onOpen 이며 데이타베이스 생성과 관리, 존재여부에 대한 역활을 한다.

//SQLiteDatabase 는 실질적으로 CRUD 를 수행하는데 쓰인다.
//CRUD :Create(생성), Read(읽기), Update(갱신), Delete(삭제)
//SQLiteOpenHelper를 사용하면 기존에 테이블이 있는지 없느지 판단하여 각각에 알맞게 데이터베이스 테이블을 업그레이드 시키거나 새로 생성할 수 있다.

//안드로이드(android) SQLite 데이타 베이스 다루기
//출처: https://mainia.tistory.com/670 [녹두장군 - 상상을 현실로]

/*사용하는 방법///////////

//SQLite db 개체 생성
private DBMyProductHelper db;

//SQLite db helper init 초기화
db = new DBMyProductHelper(this);

//새로운 노래 추가
db.addMusic(music);

//새로운 노래를(Music 개체를) db에 추가
db.addMusic(Music);

//id에 해당하는 Music을 db에서 가져오기
db.getMusic(id);

//모든 music 리스트를 가져온다
db.getAllMusic();

//db에서 가져온 Music 개수 가져오기
db.getMusicCount();

//매개변수로 넘어온 Music 개체를 업데이트.
db.updateMusic(music);

//매개변수로 넘어온 Music 개체를 삭제한다.
db.deleteMusic(music);

*/


public class DBMyProductHelper_Read extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name 데이터베이스 이름
    private static final String DATABASE_NAME = "MusicDB";

    // Contacts table name 테이블명
    private static final String TABLE_NAME_MY_PRODUCTS = "ReadMusic"; //연습노래 테이블명

    //ReadMusic table Columns names 컬럼명
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_WRITER = "writer";
    private static final String KEY_SCORE = "score";
    private static final String KEY_BEAT = "beat";

    public DBMyProductHelper_Read(Context context) {
        //세번째 인수 factory 는 표준 cursor를 이용할 경우 null 로 지정
        //출처: https://aroundck.tistory.com/238 [돼지왕 왕돼지 놀이터]
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override //CREATE TABLE//
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //DB가 처음 만들어질때 호출됨. 여기서 테이블 생성및 초기 레코드 삽입
        Log.i("DBLog", "[DBMyProductHelper_Read] CREATE TABLE ");

        String CREATE_MY_PRODUCT_TABLE = "CREATE TABLE " + TABLE_NAME_MY_PRODUCTS + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //0
                + KEY_TITLE + " TEXT, " //1
                + KEY_WRITER + " TEXT, " //2
                + KEY_SCORE + " TEXT, " //3
                + KEY_BEAT + " TEXT " //4
                + ")";

        sqLiteDatabase.execSQL(CREATE_MY_PRODUCT_TABLE);
    }

    @Override //UPGRADING TABLE
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop older table if existed
        // DB를 업그레이드 할 때 호출. (DB version이 바뀌었을 때)
        // 기존 테이블 삭제&새로 생성 하거나 ALTER TABLE 로 Schema 수정
        Log.i("DBLog", "[DBMyProductHelper_Read] UPGRADE TABLE ");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MY_PRODUCTS);

        //create tables again
        onCreate(sqLiteDatabase);

    }

    //하단 함수들에 대한 메뉴판 입니다. 여기서 보고 필요한 함수를 사용하세요//
    /*//CRUD :Create(생성), Read(읽기), Update(갱신), Delete(삭제)//
     * addMusic(Music) : 새로운 노래를(Music 개체를) db에 추가
     * getMusic(id) : id에 해당하는 Music을 db에서 가져오기
     * getAllMusic() : 모든 music 리스트를 가져온다
     * getMusicCount() : db에서 가져온 Music 개수 가져오기
     * updateMusic(music) : 매개변수로 넘어온 Music 개체를 업데이트.
     * deleteMusic(music) : 매개변수로 넘어온 Music 개체를 삭제한다.
     */


    /* 새로운 Music을 db에 추가
     * parms : Music 개채
     * 매개변수로 넘겨받은 Music 개체의 값을 ContentValues 개체 생성 후 값을 채워넣는다.
     * (ContentValues는 데이터베이스에 값을 집어 넣기위한 매개체)
     */
    public void addMusic (Music music){

        Log.i("DBLog", "[DBMyProductHelper_Read] addMusic()");

        //getWritableDatabase(): 읽고 쓰기 위해 DB 연다. 권한이 없거나 디스크가 가득 차면 실패
        SQLiteDatabase db = this.getWritableDatabase();

        //Content Values를 통해 각 column의 명칭과 값을 짝지어준 후 insert()를 통해 테이블에 데이터를 삽입
        ContentValues values = new ContentValues();
        values.put(KEY_ID, music.getId());
        values.put(KEY_TITLE, music.getTitle());
        values.put(KEY_WRITER, music.getWriter());
        values.put(KEY_SCORE, music.getCode());
        values.put(KEY_BEAT,music.getBeatStr());

        //새로운 row 추가
        //insert(String table, String nullColumnHack, ContentValues values)
        //Convenience method for inserting a row into the database.
        long rowId = db.insert(TABLE_NAME_MY_PRODUCTS, null, values);
        //insert()는 방금 삽입한 row의 id를 반환한다. 실패시 -1.

        Log.i("testLog","[DBMyProductHelper_Read] addMusic() 방금삽입한 row id: "+rowId);

        //연결 종료
        db.close();

        //take_id : 방금 삽입한 row의 id. 실패시 -1.
        //return rowId;
    }


    /* db에서 music 개체를 가져온다
     * parms : 까내고자 하는 Music 아이디
     * id에 해당하는 Music을 db에서 해당하는 하나의 행값을 가져온다.
     */
    public Music getMusic(int id){

        Log.i("DBLog", "[DBMyProductHelper_Read] getMusic()");

        //getReadableDatabase () :: 읽기전용으로 DB를 불러온다.
        //이 때 생성된 DB가 없으면 onCreate(); DB가 있지만 버전이 바뀌었다면 onUpgrade();를 호출한다.
        SQLiteDatabase db = this.getReadableDatabase();

        //android.database.Cursor
        // database 클래스인걸보아 database에서 사용하는 놈인가 봄
        // 안드로이드에서는 DB에서 가져온 데이터를 쉽게 처리하기 위해서 Cursor 라는 인터페이스를 제공해 줍니다.
        // Cursor는 기본적으로 DB에서 값을 가져와서 마치 실제 Table의 한 행(Row), 한 행(Row) 을 참조하는 것 처럼 사용 할 수 있게 해 줍니다.
        // php에서 select하고 한줄씩 rows에 담아 사용하듯이 여기는 cursor에서 한줄씩 사용하나보네
        //
        Cursor cursor = db.query(TABLE_NAME_MY_PRODUCTS,
                new String[]{KEY_ID, KEY_TITLE, KEY_WRITER, KEY_SCORE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        //cusor로 가져올때 번호
        //KEY_ID + INTEGER  //0
        //KEY_TITLE + " TEXT, " //1
        //KEY_WRITER + " TEXT, " //2
        //KEY_CODE + "  TEXT " //3
        //KEY_BEAT /TEXT / 4

        //Music(int id, String title, String writer, String code, String beatStr) <-생성자
        Music music = new Music(
                Integer.parseInt(cursor.getString(0)), //id
                cursor.getString(1),     //title
                cursor.getString(2),    //writer
                cursor.getString(3),     //code
                cursor.getString(4)     //beat
        );

        return music;
    }

    /* db에서 모든 music 리스트를 가져온다
     * parms :
     *
     */
    public ArrayList<Music> getAllMusic(){
        Log.i("DBLog", "[DBMyProductHelper_Read] getAllMusic()");

        ArrayList<Music> musicArr = new ArrayList<Music>();

        //select all query
        String selectQuery = "SELECT * FROM " + TABLE_NAME_MY_PRODUCTS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                //Music(int id, String title, String writer, String code, String beatStr) <-생성자
                Music music = new Music(
                        Integer.parseInt(cursor.getString(0)), //id
                        cursor.getString(1),     //title
                        cursor.getString(2),    //writer
                        cursor.getString(3),     //code
                        cursor.getString(4)     //beat
                );

                //adding myProduct to list
                musicArr.add(music);
            }while (cursor.moveToNext());
        }
        return musicArr;

    }

    /* db에서 가져온 Music 개수 가져오기
     * parms :
     * 테이블에 포함된 데이터 숫자세기
     * 리턴받은 Cursor 객체에서 getCount 함수를 사용해 개수정보를 가져온다.
     */
    public int getMusicCount(){
        Log.i("DBLog", "[DBMyProductHelper_Read] getMusicCount()");

        String countQuery = "SELECT * FROM " + TABLE_NAME_MY_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    /* Music 개체 없데이트
     * parms : 없데이트 하고자하는 Music 개체
     * 매개변수로 넘어온 Music 개체를 업데이트 한다.
     * 매개변수로 넘어온 Music 개체의 id를 기준으로 업데이트
     * 업데이트 해야할 id는 매개변수 개체에 포함되어있으므로 getter함수로 꺼내 사용.
     */
    public int updateMusic(Music music){
        Log.i("DBLog", "[DBMyProductHelper_Read] updateMusic()");

        //getWritableDatabase(): 읽고 쓰기 위해 DB 연다. 권한이 없거나 디스크가 가득 차면 실패
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, music.getTitle());
        values.put(KEY_WRITER, music.getWriter());
        values.put(KEY_SCORE, music.getCode());
        values.put(KEY_BEAT,music.getBeatStr());

        //return : update row count
        return db.update(TABLE_NAME_MY_PRODUCTS, values, KEY_ID + " = ? ", new String[]{String.valueOf(music.getId())});
    }

    /* Music 삭제하기 *
     * 매개변수로 넘어온 Music 객체에 해당하는 값을 삭제한다.
     * 직접 쿼리를 작성해 삭제하고자 한다면 rawQuery()를 사용하면 된다.
     * 삭제한 row갯수를 반환한다.
     */
    public int deleteMusic (Music music){
        Log.i("DBLog", "[DBMyProductHelper_Read] deleteMusic()");

        //getWritableDatabase(): 읽고 쓰기 위해 DB 연다. 권한이 없거나 디스크가 가득 차면 실패
        SQLiteDatabase db = this.getWritableDatabase();

        int deletedRowCount = db.delete(TABLE_NAME_MY_PRODUCTS, KEY_ID+ " = ?",
                new String[]{String.valueOf(music.getId())});
        db.close();

        Log.i("DBLog","[DBMyProductHelper_Read] 삭제된 row 개수 :"+deletedRowCount);

        return deletedRowCount;// 삭제된 row갯수.
    }

}
