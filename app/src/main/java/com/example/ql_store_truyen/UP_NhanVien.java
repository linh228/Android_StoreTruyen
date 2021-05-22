package com.example.ql_store_truyen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ql_store_truyen.CSDL.Database;
import com.example.ql_store_truyen.CSDL.NhanVien;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UP_NhanVien extends AppCompatActivity {

    final String DATABASE_NAME = "StoreTruyen.sqlite";
    final  int RESQUEST_TAKE_PHOTO = 123;
    final  int RESQUEST_CHOOSE_PHOTO = 321;
    int id = 0;
    EditText hoten, ngaysinh, sdt, diachi, taikhoan, matkhau, chucvu;
    Button chon, chup , luu;
    ImageView anhnv;
    ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_nhanvien);
        addControls();
        setEvent();
        loadNV();
    }
    private void loadNV(){
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien where Id = ?", new String[]{id+""});
        cursor.moveToFirst();
        hoten.setText(cursor.getString(1));
        ngaysinh.setText(cursor.getString(2));
        sdt.setText(cursor.getString(3));
        diachi.setText(cursor.getString(4));
        taikhoan.setText(cursor.getString(5));
        matkhau.setText(cursor.getString(6));
        chucvu.setText(cursor.getString(7));
        if (cursor.getBlob(8)!= null){
            byte[] img = cursor.getBlob(8);
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 200, 200 , true);
            anhnv.setImageBitmap(bmp);
        }
        else {
            anhnv.setImageResource(R.drawable.ic_account);
        }
    }

    private void takeIMG(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }

    private byte[] getAnhImgview(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap ori = drawable.getBitmap();
        Bitmap bmp = Bitmap.createScaledBitmap(ori, 200, 200 , true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    anhnv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                anhnv.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addControls(){
        hoten = findViewById(R.id.upnv_HoTen);
        ngaysinh = findViewById(R.id.upnv_NgaySinh);
        sdt = findViewById(R.id.upnv_SDT);
        diachi = findViewById(R.id.upnv_DiaChi);
        taikhoan = findViewById(R.id.upnv_TaiKhoan);
        matkhau = findViewById(R.id.upnv_MatKhau);
        chucvu = findViewById(R.id.upnv_ChucVu);
        anhnv = findViewById(R.id.upnv_Anh);
        chon = findViewById(R.id.upnv_ChonA);
        chup = findViewById(R.id.upnv_ChupA);
        luu = findViewById(R.id.upnv_Luu);
        cancel = findViewById(R.id.upnv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UP_NhanVien.this, DS_NhanVien.class);
                UP_NhanVien.this.startActivity(intent);
            }
        });
    }

    private void setEvent(){
        chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        chup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeIMG();
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuTTTT();
            }
        });

    }

    private void luuTTTT(){
        String Hoten = hoten.getText().toString();
        String Ngaysinh = ngaysinh.getText().toString();
        String Sdt = sdt.getText().toString();
        String Diachi = diachi.getText().toString();
        String Taikhoan = taikhoan.getText().toString();
        String Matkhau = matkhau.getText().toString();
        String Chucvu = chucvu.getText().toString();
        byte[] anh = getAnhImgview(anhnv);

        ContentValues contentValues = new ContentValues();
        contentValues.put("HoTen", Hoten);
        contentValues.put("NgaySinh", Ngaysinh);
        contentValues.put("SDT", Sdt);
        contentValues.put("DiaChi", Diachi);
        contentValues.put("TaiKhoan", Taikhoan);
        contentValues.put("MatKhau", Matkhau);
        contentValues.put("ChucVu", Chucvu);
        contentValues.put("AnhNV", anh);

        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.update("NhanVien", contentValues,"Id = ?", new String[] {id + ""});
        Toast.makeText(UP_NhanVien.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DS_NhanVien.class);
        startActivity(intent);
    }

}