package com.example.ql_store_truyen.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ql_store_truyen.CSDL.NhanVien;
import com.example.ql_store_truyen.CSDL.Truyen;
import com.example.ql_store_truyen.R;

import java.util.ArrayList;

public class AdapterNhanVien extends BaseAdapter {
    Activity context;
    ArrayList<NhanVien> list;

    public AdapterNhanVien(Activity context, ArrayList<NhanVien> List){
        this.context = context;
        list = List;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NhanVien getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.detail_nhanvien, null);
        ImageView AnhNV = row.findViewById(R.id.DS_AnhNV);
        TextView TenNV = row.findViewById(R.id.DS_TenNV);
        NhanVien nhanVien = list.get(i);
        MenuItem Del = row.findViewById(R.id.Del_Truyen);
        TenNV.setText(nhanVien.HoTen);
        if (nhanVien.AnhNV!=null){
            Bitmap img = BitmapFactory.decodeByteArray(nhanVien.AnhNV, 0, nhanVien.AnhNV.length);
            AnhNV.setImageBitmap(img);
        }
        else {
            AnhNV.setImageResource(R.drawable.ic_account);
        }
        return row;
    }
}
