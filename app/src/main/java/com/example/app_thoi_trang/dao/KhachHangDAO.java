package com.example.app_thoi_trang.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_thoi_trang.database.DbHelper;
import com.example.app_thoi_trang.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SQLiteDatabase db;

    public KhachHangDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(KhachHang ob){
        ContentValues values = new ContentValues();
        values.put("tenKH",ob.tenKH);
        values.put("diaChi",ob.diaChi);
        values.put("sdtKH",ob.sdtKH);
        return db.insert("KhachHang",null,values);
    }

    public int update(KhachHang ob){
        ContentValues values = new ContentValues();
        values.put("tenKH",ob.tenKH);
        values.put("diaChi",ob.diaChi);
        values.put("sdtKH",ob.sdtKH);
        return db.update("KhachHang",values,"maKH=?", new String[]{String.valueOf(ob.maKH)});
    }

    public int delete(String id){
        return db.delete("KhachHang","maKH=?", new String[]{id});
    }

    public List<KhachHang> getAll(){
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }

    public KhachHang getID(String id){
        KhachHang obKhachHang = new KhachHang();
        String[] args = new String[]{ id };
        Cursor c = db.rawQuery("SELECT maKH, tenKH, diaChi, sdtKH FROM KhachHang WHERE maKH=?", args);
        try {
            if(c.moveToFirst()){
                obKhachHang.maKH = c.getInt(0);
                obKhachHang.tenKH = c.getString(1);
                obKhachHang.sdtKH = c.getString(2);
                obKhachHang.diaChi = c.getString(3);
            }
        } finally {
            if (c != null) c.close();
        }
        return obKhachHang;
    }

    @SuppressLint("Range")
    private List<KhachHang> getData(String sql, String...selectionArgs){
        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        try {
            while (c.moveToNext()){
                KhachHang ob = new KhachHang();
                ob.maKH = c.getInt(c.getColumnIndex("maKH"));
                ob.tenKH = c.getString(c.getColumnIndex("tenKH"));
                ob.diaChi = c.getString(c.getColumnIndex("diaChi"));
                ob.sdtKH = c.getString(c.getColumnIndex("sdtKH"));
                list.add(ob);
            }
        } finally {
            if (c != null) c.close();
        }
        return list;
    }

    public List<KhachHang> getSearch_khachHang(String tenKH){
        String sql = "SELECT * FROM KhachHang WHERE tenKH LIKE '%"+tenKH+"%' ";
        return getData(sql);
    }
}
