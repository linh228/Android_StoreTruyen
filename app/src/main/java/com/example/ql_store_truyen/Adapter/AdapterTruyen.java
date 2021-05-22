package com.example.ql_store_truyen.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.ql_store_truyen.CSDL.Database;
import com.example.ql_store_truyen.R;
import com.example.ql_store_truyen.CSDL.Truyen;
import com.example.ql_store_truyen.UP_Truyen;

import java.util.ArrayList;

public class AdapterTruyen extends BaseAdapter {
    Activity context;
    ArrayList<Truyen> List;

    public AdapterTruyen(Activity context, ArrayList<Truyen> list) {
        this.context = context;
        List = list;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Truyen getItem(int i) {
        return List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return List.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.detail_truyen, null);
        ImageView AnhBia = row.findViewById(R.id.AnhBia);
        TextView TenT = row.findViewById(R.id.TenT);
        Truyen truyen = List.get(i);
        LinearLayout LO_Truyen = row.findViewById(R.id.LO_truyen);
        MenuItem Del = row.findViewById(R.id.Del_Truyen);
        TenT.setText(truyen.TenT);
        if (truyen.AnhBia!=null){
            Bitmap img = BitmapFactory.decodeByteArray(truyen.AnhBia, 0, truyen.AnhBia.length);
            AnhBia.setImageBitmap(img);
        }
        else {
            AnhBia.setImageResource(R.drawable.ic_truyen);
        }
        return row;
    }

}
