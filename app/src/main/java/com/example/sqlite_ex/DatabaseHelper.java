package com.example.sqlite_ex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "Health.db";  //데이터베이스 명

    final static String  TABLE_TRAINER = "trainer_table"; //트레이너 테이블 명
    final static String  TABLE_MEMBER = "member_table"; //회원 테이블 명
    final static String  TABLE_LECTURE = "lecture_table"; //강의 테이블 명
    final static String  TABLE_RESERVATION = "reservation_table"; //예약 테이블 명

    //TODO 트레이너 테이블(스키마설계)
    public static final String T_COL_1_PK = "trainer_Num";
    public static final String T_COL_2 = "Name";
    public static final String T_COL_3 = "Phone";
    public static final String T_COL_4 = "Address";

    //TODO 회원 테이블(스키마설계)
    public static final String M_COL_1_PK = "member_ID";
    public static final String M_COL_2 = "member_Name";
    public static final String M_COL_3 = "Password";
    public static final String M_COL_4 = "Address";
    public static final String M_COL_5 = "Membership_Name"; //회원권명
    public static final String M_COL_6 = "joindate"; //최초가입일
    public static final String M_COL_7 = "membership_count"; //남은 회원권 횟수
    public static final String M_COL_8_FK = "trainerNum"; //담당 트레이너 식별번호(FK)

    //TODO 강의 테이블(스키마설계)
    public static final String L_COL_1_PK = "lecture_Num"; //PK
    public static final String L_COL_2 = "date"; //PK(부분키)
    public static final String L_COL_3 = "hour"; //PK(부분키)
    public static final String L_COL_4 = "lecture_Name"; //강의명
    public static final String L_COL_5 = "available_Num"; //가용인원
    public static final String L_COL_6 = "waiting"; //대기인원
    public static final String L_COL_7_FK = "trainerNum"; //담당 트레이너 식별번호(FK)

    //TODO 예약 테이블(스키마설계)
    public static final String R_COL_1_FK = "member_ID"; // 회원ID(FK)
    public static final String R_COL_2_FK = "lecture_Num"; // 강의식별번호(FK)
    public static final String R_COL_3_FK = "date"; // 날짜(FK)
    public static final String R_COL_4_FK = "hour"; // 시간(FK)


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRAINER + "(trainer_Num INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT NOT NULL, Phone TEXT NOT NULL, Address TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_MEMBER + "(member_ID INTEGER PRIMARY KEY AUTOINCREMENT, member_Name TEXT, Password TEXT, Address TEXT, Membership_Name TEXT, joindate TEXT, membership_count INTEGER, trainerNum INTEGER, FOREIGN KEY (trainerNum) REFERENCES TABLE_TRAINER(trainer_Num))");
        db.execSQL("CREATE TABLE " + TABLE_LECTURE + "(lecture_Num INTEGER, date INTEGER, hour INTEGER, lecture_Name TEXT, available_Num INTEGER, waiting INTEGER, trainerNum INTEGER, FOREIGN KEY (trainerNum) REFERENCES TABLE_TRAINER(trainer_Num), " +
                "PRIMARY KEY (lecture_Num, date, hour))");
        db.execSQL("CREATE TABLE " + TABLE_RESERVATION + "(member_ID INTEGER, lecture_Num INTEGER, date INTEGER, hour INTEGER," +
                " FOREIGN KEY (member_ID) REFERENCES TABLE_MEMBER(member_ID), FOREIGN KEY (lecture_Num) REFERENCES TABLE_LECTURE(lecture_Num), FOREIGN KEY (date) REFERENCES TABLE_LECTURE(date), FOREIGN KEY (hour) REFERENCES TABLE_LECTURE(hour)," +
                " PRIMARY KEY (member_ID, lecture_Num, date, hour))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LECTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATION);

        onCreate(db);
    }


    //트레이너 정보 추가하기
    public boolean insertData_trainer(String name, String phone, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_COL_2, name);
        contentValues.put(T_COL_3, phone);
        contentValues.put(T_COL_4, address);
        long result = db.insert(TABLE_TRAINER, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //회원 정보 추가하기
    public boolean insertData_member(String member_Name, String Password, String Address, String Membership_Name, String joindate, Integer membership_count, Integer trainerNum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(M_COL_2, member_Name);
        contentValues1.put(M_COL_3, Password);
        contentValues1.put(M_COL_4, Address);
        contentValues1.put(M_COL_5, Membership_Name);
        contentValues1.put(M_COL_6, joindate);
        contentValues1.put(M_COL_7, membership_count);
        contentValues1.put(M_COL_8_FK, trainerNum);


        long result = db.insert(TABLE_MEMBER, null, contentValues1);
        if(result == -1)
            return false;
        else
            return true;
    }

    //강의 정보 추가하기
    public boolean insertData_lecture(Integer lecture_Num, Integer date, Integer hour, String lecture_Name, Integer available_Num, Integer waiting, Integer trainerNum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(L_COL_1_PK, lecture_Num);
        contentValues2.put(L_COL_2, date);
        contentValues2.put(L_COL_3, hour);
        contentValues2.put(L_COL_4, lecture_Name);
        contentValues2.put(L_COL_5, available_Num);
        contentValues2.put(L_COL_6, waiting);
        contentValues2.put(L_COL_7_FK, trainerNum);

        long result = db.insert(TABLE_LECTURE, null, contentValues2);
        if(result == -1)
            return false;
        else
            return true;
    }


    //예약 정보 추가하기
    public boolean insertData_reservation(Integer member_ID, Integer lecture_Num, Integer date, Integer hour){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(R_COL_1_FK, member_ID);
        contentValues3.put(R_COL_2_FK, lecture_Num);
        contentValues3.put(R_COL_3_FK, date);
        contentValues3.put(R_COL_4_FK, hour);

        long result = db.insert(TABLE_RESERVATION, null, contentValues3);
        if(result == -1)
            return false;
        else
            return true;
    }


    //데이터베이스 삭제하기
    public Integer deleteData(String trainer_Num){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TRAINER, "ID = ? ", new String[]{trainer_Num});
    }

    //데이터베이스 항목 읽어오기 Read
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_TRAINER, null);
        return res;
    }

    //데이터베이스 수정하기
    public boolean updateData(String trainer_Num, String name, String phone, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T_COL_1_PK, trainer_Num);
        contentValues.put(T_COL_2, name);
        contentValues.put(T_COL_3, phone);
        contentValues.put(T_COL_4, address);
        db.update(TABLE_TRAINER, contentValues, "ID = ?", new String[] { trainer_Num });
        return true;
    }
}
