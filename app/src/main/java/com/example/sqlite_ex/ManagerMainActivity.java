package com.example.sqlite_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerMainActivity extends AppCompatActivity {

    Button btn_memberManage, btn_trainerManage, btn_lectureManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        btn_trainerManage = findViewById(R.id.btn_trainerManage);
        GO_trainerManage_UI();


        DatabaseHelper myDB = new DatabaseHelper(this);

        //myDB.insertData_trainer("김종국", "010-1234-5678", "서울");
        //myDB.insertData_member("이창현", "abcd1234!", "부산진구 개금동", "PT", "2022.05.22", 5, 2);
        //myDB.insertData_lecture(1, 20220622, 12, "PT", 5, 0, 1);
        //myDB.insertData_reservation(1, 1, 20220522, 12);
    }

    public void GO_trainerManage_UI(){
        btn_trainerManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerMainActivity.this, TrainerManage.class);
                startActivity(intent);
            }
        });
    }

}