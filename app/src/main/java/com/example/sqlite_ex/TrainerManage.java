package com.example.sqlite_ex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainerManage extends AppCompatActivity {

    Button btn_trainerRegister, btn_trainerInquiry, btn_Back;

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_manage);

        myDB = new DatabaseHelper(this);

        btn_trainerRegister = findViewById(R.id.btn_trainerRegister);
        btn_trainerInquiry = findViewById(R.id.btn_trainerInquiry);
        btn_Back = findViewById(R.id.btn_Back);

        viewAll();
        GO_Register_UI();
        GO_Back_ManageMain_UI();
    }

    // 트레이너 정보 등록 화면으로 가는 버튼(트레이너 등록)
    public void GO_Register_UI(){
        btn_trainerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerManage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //
    public void GO_Back_ManageMain_UI(){
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerManage.this, ManagerMainActivity.class);
                startActivity(intent);
            }
        });
    }

    // 데이터 읽어오기(조회하기)
    public void viewAll(){
        btn_trainerInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount() == 0){
                    ShowMessage("실패", "데이터를 찾을 수 없습니다.");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while(res.moveToNext()){
                    buffer.append("\n트레이너 식별번호: " + res.getString(0) + "\n");
                    buffer.append("이름: " + res.getString(1) + "\n");
                    buffer.append("전화번호: " + res.getString(2) + "\n");
                    buffer.append("주소: " + res.getString(3) + "\n\n");

                }
                ShowMessage("                   트레이너 목록", buffer.toString());

            }
        });
    }

    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}