package com.example.ql_store_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ql_store_truyen.CSDL.NhanVien;

public class MainActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        addevent();
    }
    private void addevent(){
        FrameLayout QL_T = findViewById(R.id.QL_T);
        FrameLayout QL_NV = findViewById(R.id.QL_NV);
        FrameLayout QL_TK = findViewById(R.id.QL_TK);
        btn = findViewById(R.id.btn_dangxuat);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NhanVien.NguoiDung.clear();
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        QL_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DS_Truyen.class);
                MainActivity.this.startActivity(intent);
            }
        });
        QL_NV.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DS_NhanVien.class);
                MainActivity.this.startActivity(intent);
            }
        });
        QL_TK.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TTTT.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}