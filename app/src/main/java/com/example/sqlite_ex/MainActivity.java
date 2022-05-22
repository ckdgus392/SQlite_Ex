package com.example.sqlite_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    EditText editTextName, editTextPhone, editTextAddress;
    Button buttonInsert, btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);

        buttonInsert = findViewById(R.id.buttonInsert);
        btn_Back = findViewById(R.id.btn_Back);

        AddData();
        GO_Back_UI();

    }

    //뒤로가기 버튼을 눌렸을 때
    public void GO_Back_UI(){
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrainerManage.class);
                startActivity(intent);
            }
        });
    }


    //데이터 추가하기
    public void AddData(){
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData_trainer(editTextName.getText().toString(),
                        editTextPhone.getText().toString(),
                        editTextAddress.getText().toString());

                if(isInserted == true){
                    Toast.makeText(MainActivity.this, "데이터추가 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TrainerManage.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "데이터 추가실패", Toast.LENGTH_SHORT).show();

            }
        });
    }
}