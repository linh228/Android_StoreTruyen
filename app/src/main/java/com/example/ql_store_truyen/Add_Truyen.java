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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_Truyen extends AppCompatActivity {

    final String DATABASE_NAME = "StoreTruyen.sqlite";
    final  int RESQUEST_TAKE_PHOTO = 123;
    final  int RESQUEST_CHOOSE_PHOTO = 321;
    Button btnChup, btnChon, btnLuu;
    EditText TenT, TacGia, NXB, NoiDung, TheLoai, Gia, SoLuong;
    ImageView AnhT;
    ImageButton cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_truyen);
        addControls();
        SetEvent();
    }

    private void addControls() {
        btnChon = findViewById(R.id.add_CA_Truyen);
        btnChup = findViewById(R.id.add_CHA_Truyen);
        btnLuu = findViewById(R.id.add_S_Truyen);
        TenT = findViewById(R.id.add_Tent);
        TacGia = findViewById(R.id.add_Tacgia);
        NXB = findViewById(R.id.add_Nxb);
        NoiDung = findViewById(R.id.add_Noidung);
        TheLoai = findViewById(R.id.add_Theloai);
        Gia = findViewById(R.id.add_Giaban);
        SoLuong = findViewById(R.id.add_Soluong);
        AnhT = findViewById(R.id.add_Anht);
        cancel = findViewById(R.id.addT_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Truyen.this, DS_Truyen.class);
                Add_Truyen.this.startActivity(intent);
            }
        });
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

    private void SetEvent(){
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeIMG();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
    }

    private void insert(){
        String ten = TenT.getText().toString();
        String tacgia = TacGia.getText().toString();
        String nxb = NXB.getText().toString();
        String noidung = NoiDung.getText().toString();
        String theloai = TheLoai.getText().toString();
        int gia = Integer.parseInt(Gia.getText().toString());
        int soluong = Integer.parseInt(SoLuong.getText().toString());
        byte[] anh = getAnhImgview(AnhT);

        ContentValues contentValues = new ContentValues();
        contentValues.put("TenT", ten);
        contentValues.put("TacGia", tacgia);
        contentValues.put("NXB", nxb);
        contentValues.put("NoiDung", noidung);
        contentValues.put("TheLoai", theloai);
        contentValues.put("Gia", gia);
        contentValues.put("SoLuong", soluong);
        contentValues.put("AnhBia", anh);


        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.insert("Truyen",null ,contentValues);
        Toast.makeText(Add_Truyen.this, "Thêm truyện thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DS_Truyen.class);
        startActivity(intent);
    }
    public void cancel(){
        Intent intent = new Intent(this, DS_Truyen.class);
        startActivity(intent);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    AnhT.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                AnhT.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}