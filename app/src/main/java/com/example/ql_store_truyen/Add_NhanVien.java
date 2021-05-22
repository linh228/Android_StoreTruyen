package com.example.ql_store_truyen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ql_store_truyen.CSDL.Database;

public class Add_NhanVien extends AppCompatActivity {
    final String DATABASE_NAME = "StoreTruyen.sqlite";
    final  int RESQUEST_TAKE_PHOTO = 123;
    final  int RESQUEST_CHOOSE_PHOTO = 321;
    Button save, remove;
    ImageButton cancel;
    EditText HoTen, NgaySinh, SDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nhanvien);
        addControls();
        SetEvent();
    }

    private void addControls() {
        save = findViewById(R.id.add_nhanvien_S);
        remove = findViewById(R.id.add_nhanvien_R);
        HoTen = findViewById(R.id.add_nhanvien_HoTen);
        NgaySinh = findViewById(R.id.add_nhanvien_NgaySinh);
        SDT = findViewById(R.id.add_nhanvien_SDT);
        cancel = findViewById(R.id.addnv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_NhanVien.this, DS_NhanVien.class);
                Add_NhanVien.this.startActivity(intent);
            }
        });
    }

    private void SetEvent(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoTen.setText("");
                NgaySinh.setText("");
                SDT.setText("");
            }
        });
    }

    private void insert(){
        String tk, mk;
        String hoten = HoTen.getText().toString();
        String ngaysinh = NgaySinh.getText().toString();
        String sdt = SDT.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("HoTen", hoten);
        contentValues.put("NgaySinh", ngaysinh);
        contentValues.put("SDT", sdt);
        contentValues.put("ChucVu", "Nhân viên");

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.insert("NhanVien",null ,contentValues);

        SQLiteDatabase database1 = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        if (cursor.moveToLast()){
            contentValues.clear();
            contentValues.put("HoTen", hoten);
            contentValues.put("NgaySinh", ngaysinh);
            contentValues.put("SDT", sdt);
            contentValues.put("ChucVu", "Nhân viên");
            contentValues.put("TaiKhoan", "nhanvien"+cursor.getInt(0));
            contentValues.put("MatKhau", sdt);
            tk = "nhanvien"+cursor.getInt(0);
            mk = sdt+"";
            database.update("NhanVien", contentValues, "Id = ?", new String[]{cursor.getInt(0)+""});
            Toast.makeText(this, "Đã tạo tài khoản thành công",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo!");
            builder.setMessage("Đã thêm nhân viên mới.\nTài khoản: "+tk+"\nMật khẩu: "+mk);
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Add_NhanVien.this, DS_NhanVien.class);
                    Add_NhanVien.this.startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}