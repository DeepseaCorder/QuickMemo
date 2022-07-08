package com.puresoftware.quickmemo.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import com.puresoftware.quickmemo.R;

public class SampleAcivity extends AppCompatActivity {

    Button btnSample1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_acivity);

        EditText edttext = findViewById(R.id.edt_text);
        edttext.setCursorVisible(false);

        btnSample1 = findViewById(R.id.btnSample1);

        
        // 인터페이스: 실행시킬 수 없고, 추상메소드만 가지고있음 (상수)
        // -> 스스로 객체를 생성할 수 없음
        // -> 1) Interface를 구현하는 하위클래스만들고 객체생성
        // -> 2) 익명클래스 (익명객체)


        // 1) 첫번째 방법
//        View.OnClickListener a = new OnClickListenr();
//        MyOnClickListner aaaa = new MyOnClickListner();
//
////        View (setOnClickListenr) -> Button (OnClickListenr 객체타입) ->
//        btnSample1.setOnClickListener(aaaa); // (OnClickListner -> parent,   MyOnClickListner -> child) => 업캐스팅
//
        // 2) 두번째 방법
        //

//        View.OnClickListener aaaa = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("gugu", "hello~!~!~");
//            }
//        };

//        View.OnClickListener a1 = new View.OnClickListener() {
//
//        };
//
//        SampleData d1 = new SampleData() {
//
//        };

//        btnSample1.setOnClickListener(aaaa);

        btnSample1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("gugu", "hello~");
            }
        });

//        new AdapterName.OnItemClickListener(){
//            @Override
//            public void onItemClick(View v, int pos){
//                // 클릭 이벤트에서 처리할 코드 작성
//            }
//        };


    }
}