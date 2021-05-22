package com.example.ql_store_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ql_store_truyen.Adapter.AdapterNhanVien;
import com.example.ql_store_truyen.Adapter.AdapterTruyen;
import com.example.ql_store_truyen.CSDL.Database;
import com.example.ql_store_truyen.CSDL.NhanVien;
import com.example.ql_store_truyen.CSDL.Truyen;

import java.util.ArrayList;

public class DS_NhanVien extends AppCompatActivity {
    final String DATABASE_NAME = "StoreTruyen.sqlite";
    SQLiteDatabase database;
    ListView listView;
    int vitri;
    ArrayList<NhanVien> list;
    AdapterNhanVien adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_nhanvien);
        addControls();
        loadData();
    }

    private void addControls() {
        LinearLayout LO_NhanVien = findViewById(R.id.LO_NhanVien);
        listView = findViewById(R.id.listNhanVien);
        list = new ArrayList<>();
        adapter = new AdapterNhanVien(this, list);
        listView.setAdapter(adapter);
        ImageButton add_NV = findViewById(R.id.dsnv_add);
        ImageButton cancel = findViewById(R.id.dsnv_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DS_NhanVien.this, MainActivity.class);
                DS_NhanVien.this.startActivity(intent);
            }
        });
        add_NV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DS_NhanVien.this, Add_NhanVien.class);
                DS_NhanVien.this.startActivity(intent);
            }
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DS_NhanVien.this, UP_NhanVien.class);
                intent.putExtra("ID", adapter.getItem(position).Id);
                DS_NhanVien.this.startActivity(intent);
           }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vitri = position;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_truyen, menu);
    }

    private void loadData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        list.clear();
        for (int i=0; i< cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String hoten = cursor.getString(1);
            String ngaysinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String diachi = cursor.getString(4);
            String taikhoan = cursor.getString(5);
            String matkhau = cursor.getString(6);
            String chucvu = cursor.getString(7);
            byte[] anhnv = cursor.getBlob(8);
            NhanVien t = new NhanVien(id,hoten,ngaysinh,sdt,diachi,taikhoan,matkhau,chucvu,anhnv);
            list.add(t);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Xác nhận xóa!");
        builder.setMessage("Bạn có chắc chắn muốn xóa");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(adapter.getItem(vitri).Id);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return super.onContextItemSelected(item);
    }
    private void delete(int idNV){
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.delete("NhanVien", "Id = ?", new String[]{idNV+""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        list.clear();
        for (int i=0; i< cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String hoten = cursor.getString(1);
            String ngaysinh = cursor.getString(2);
            String sdt = cursor.getString(3);
            String diachi = cursor.getString(4);
            String taikhoan = cursor.getString(5);
            String matkhau = cursor.getString(6);
            String chucvu = cursor.getString(7);
            byte[] anhnv = cursor.getBlob(8);
            NhanVien t = new NhanVien(id,hoten,ngaysinh,sdt,diachi,taikhoan,matkhau,chucvu,anhnv);
            list.add(t);
        }
        adapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }
}