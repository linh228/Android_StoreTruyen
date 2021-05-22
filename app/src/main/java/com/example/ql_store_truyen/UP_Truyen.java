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

public class UP_Truyen extends AppCompatActivity {
    final String DATABASE_NAME = "StoreTruyen.sqlite";
    final  int RESQUEST_TAKE_PHOTO = 123;
    final  int RESQUEST_CHOOSE_PHOTO = 321;
    int id = 0;
    Button btnChup, btnChon, btnLuu;
    EditText TenT, TacGia, NXB, NoiDung, TheLoai, Gia, SoLuong;
    ImageButton cancel;
    ImageView AnhT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_truyen);
        addControls();
        SetEvent();
        LD_Truyen();
    }

    private void LD_Truyen() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Truyen where Id = ?", new String[]{id+""});
        cursor.moveToFirst();
        TenT.setText(cursor.getString(1));
        TacGia.setText(cursor.getString(2));
        NXB.setText(cursor.getString(3));
        NoiDung.setText(cursor.getString(4));
        TheLoai.setText(cursor.getString(5));
        Gia.setText(cursor.getString(6));
        SoLuong.setText(cursor.getString(7));
        byte[] img = cursor.getBlob(8);
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        AnhT.setImageBitmap(bitmap);
    }

    private void addControls() {
        btnChon = findViewById(R.id.CA_Truyen);
        btnChup = findViewById(R.id.CHA_Truyen);
        btnLuu = findViewById(R.id.S_Truyen);
        TenT = findViewById(R.id.Tent);
        TacGia = findViewById(R.id.Tacgia);
        NXB = findViewById(R.id.Nxb);
        NoiDung = findViewById(R.id.Noidung);
        TheLoai = findViewById(R.id.Theloai);
        Gia = findViewById(R.id.Giaban);
        SoLuong = findViewById(R.id.Soluong);
        AnhT = findViewById(R.id.Anht);
        cancel = findViewById(R.id.upT_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UP_Truyen.this, DS_Truyen.class);
                UP_Truyen.this.startActivity(intent);
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
                update();
            }
        });
    }

    private void update(){
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
        database.update("Truyen", contentValues,"Id = ?", new String[] {id + ""});
        Toast.makeText(UP_Truyen.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
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