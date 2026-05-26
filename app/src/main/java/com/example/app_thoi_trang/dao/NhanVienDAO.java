package com.example.app_thoi_trang.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_thoi_trang.database.DbHelper;
import com.example.app_thoi_trang.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private SQLiteDatabase db;

    public NhanVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien ob){
        ContentValues values = new ContentValues();
        values.put("hoTenNV",ob.hoTenNV);
        values.put("tenDN",ob.tenDN);
        values.put("matKhau",ob.matKhau);
        values.put("sdtNV",ob.sdtNV);
        values.put("hinhNV", ob.hinhNV);
        return db.insert("NhanVien",null,values);
    }

    public int updatePass(NhanVien ob){
        ContentValues values = new ContentValues();
        values.put("hoTenNV",ob.hoTenNV);
        values.put("tenDN",ob.tenDN);
        values.put("matKhau",ob.matKhau);
        values.put("sdtNV",ob.sdtNV);
        values.put("hinhNV", ob.hinhNV);
        return db.update("NhanVien",values,"maNV=?",new String[]{String.valueOf(ob.maNV)});
    }

    public int delete(String id){
        return db.delete("NhanVien","maNV=?",new String[]{id});
    }

    @SuppressLint("Range")
    public List<NhanVien> getData(String sql, String...selectionArgs){
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        try {
            while (c.moveToNext()){
                NhanVien ob = new NhanVien();
                ob.maNV = c.getInt(c.getColumnIndex("maNV"));
                ob.hoTenNV = c.getString(c.getColumnIndex("hoTenNV"));
                ob.tenDN = c.getString(c.getColumnIndex("tenDN"));
                ob.matKhau = c.getString(c.getColumnIndex("matKhau"));
                ob.sdtNV = c.getString(c.getColumnIndex("sdtNV"));
                ob.hinhNV = c.getBlob(c.getColumnIndex("hinhNV"));
                list.add(ob);
            }
        } finally {
            if (c != null) c.close();
        }
        return list;
    }

    public List<NhanVien> getAll(){
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    public NhanVien getID(String id){
        String sql = "SELECT * FROM NhanVien WHERE tenDN=?";
        List<NhanVien> list = getData(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM NhanVien WHERE tenDN=? AND matKhau=?";
        List<NhanVien> list= getData(sql, id, password);
        if(list.size() == 0)
            return -1;
        return 1;
    }

    @SuppressLint("Range")
    public List<NhanVien> getTenDangNhapNV(String name){
        List<NhanVien> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM NhanVien WHERE hoTenNV like ?",new String[]{"%" + name + "%"});
        try {
            if (c.moveToFirst()){
                NhanVien ob = new NhanVien();
                ob.maNV = c.getInt(c.getColumnIndex("maNV"));
                ob.hoTenNV = c.getString(c.getColumnIndex("hoTenNV"));
                ob.tenDN = c.getString(c.getColumnIndex("tenDN"));
                ob.matKhau = c.getString(c.getColumnIndex("matKhau"));
                ob.sdtNV = c.getString(c.getColumnIndex("sdtNV"));
                ob.hinhNV = c.getBlob(c.getColumnIndex("hinhNV"));
                list.add(ob);
            }
        } finally {
            if (c != null) c.close();
        }
        return list;
    }

    public List<NhanVien> getSearch_nhanVien(String tenDN){
        String sql = "SELECT * FROM NhanVien WHERE tenDN LIKE '%"+tenDN+"%' ";
        return getData(sql);
    }
}
