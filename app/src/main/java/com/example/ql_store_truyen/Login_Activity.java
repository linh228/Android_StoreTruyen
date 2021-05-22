package com.example.ql_store_truyen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ql_store_truyen.CSDL.Database;
import com.example.ql_store_truyen.CSDL.NhanVien;

public class Login_Activity extends AppCompatActivity {
    String DATABASE_NAME = "StoreTruyen.sqlite";
    String taikhoan, matkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        addevent();
    }
    private void addevent(){
        EditText Taikhoan = findViewById(R.id.LG_TK);
        EditText Matkhau = findViewById(R.id.LG_MK);
        Button btnlogin = findViewById(R.id.DangNhap);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Taikhoan.getText().toString().equals("")|Matkhau.getText().toString().equals("")){

                }
                else {
                    taikhoan = Taikhoan.getText().toString();
                    matkhau = Matkhau.getText().toString();
                    checkDN();
                }
            }
        });
    }
    private void checkDN(){
        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien Where TaiKhoan = ? AND MatKhau = ?",
                new String[]{taikhoan,matkhau});
        if (cursor.moveToFirst()){
            NhanVien.NguoiDung = new NhanVien(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getBlob(8));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Đăng nhập thành công");
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    Login_Activity.this.startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo");
            builder.setMessage("Sai tên đăng nhâp hoặc mật khẩu");
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            }
    }
}