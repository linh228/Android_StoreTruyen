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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ql_store_truyen.Adapter.AdapterTruyen;
import com.example.ql_store_truyen.CSDL.Database;
import com.example.ql_store_truyen.CSDL.Truyen;

import java.util.ArrayList;

public class DS_Truyen extends AppCompatActivity {
    final String DATABASE_NAME = "StoreTruyen.sqlite";
    SQLiteDatabase database;
    ListView listView;
    int vitri;
    ArrayList<Truyen> list;
    AdapterTruyen adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_truyen);
        addControls();
        loadData();
    }

    private void addControls() {
        LinearLayout LO_Truyen = findViewById(R.id.LO_truyen);
        listView = findViewById(R.id.listTruyen);
        list = new ArrayList<>();
        adapter = new AdapterTruyen(this, list);
        listView.setAdapter(adapter);
        ImageButton add_truyen = findViewById(R.id.add_truyen);
        ImageButton cancel = findViewById(R.id.ds_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DS_Truyen.this, MainActivity.class);
                DS_Truyen.this.startActivity(intent);
            }
        });
        add_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DS_Truyen.this, Add_Truyen.class);
                DS_Truyen.this.startActivity(intent);
            }
        });

        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DS_Truyen.this, UP_Truyen.class);
                intent.putExtra("ID", adapter.getItem(position).Id);
                DS_Truyen.this.startActivity(intent);
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
        Cursor cursor = database.rawQuery("SELECT * FROM Truyen", null);
        list.clear();
        for (int i=0; i< cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String tent = cursor.getString(1);
            String tacgia = cursor.getString(2);
            String nxb = cursor.getString(3);
            String noidung = cursor.getString(4);
            String theloai = cursor.getString(5);
            int gia = cursor.getInt(6);
            int soluong = cursor.getInt(7);
            byte[] anhbia = cursor.getBlob(8);
            Truyen t = new Truyen(id,tent,tacgia,nxb,noidung,theloai,gia,soluong,anhbia);
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
    private void delete(int idtruyen){
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.delete("Truyen", "Id = ?", new String[]{idtruyen+""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Truyen", null);
        list.clear();
        for (int i=0; i< cursor.getCount(); i++){
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String tent = cursor.getString(1);
            String tacgia = cursor.getString(2);
            String nxb = cursor.getString(3);
            String noidung = cursor.getString(4);
            String theloai = cursor.getString(5);
            int gia = cursor.getInt(6);
            int soluong = cursor.getInt(7);
            byte[] anhbia = cursor.getBlob(8);
            Truyen t = new Truyen(id,tent,tacgia,nxb,noidung,theloai,gia,soluong,anhbia);
            list.add(t);
        }
        adapter.notifyDataSetChanged();
        finish();
        startActivity(getIntent());
    }
}