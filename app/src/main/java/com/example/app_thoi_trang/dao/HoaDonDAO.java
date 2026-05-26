package com.example.app_thoi_trang.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
}
package com.example.app_thoi_trang.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_thoi_trang.database.DbHelper;
import com.example.app_thoi_trang.model.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private SQLiteDatabase db;

    public HoaDonDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(HoaDon ob){
        ContentValues values = new ContentValues();
        values.put("maNV", ob.maNV);
        values.put("maKH",ob.maKH);
        values.put("maGiay",ob.maGiay);
        values.put("ngay",ob.ngay);
        values.put("giaHD",ob.giaHD);
        values.put("trangThai",ob.trangThai);
        return db.insert("HoaDon",null,values);
    }

    public int update(HoaDon ob){
        ContentValues values = new ContentValues();
        values.put("maNV", ob.maNV);
        values.put("maKH",ob.maKH);
        values.put("maGiay",ob.maGiay);
        values.put("ngay",ob.ngay);
        values.put("giaHD",ob.giaHD);
        values.put("trangThai",ob.trangThai);
        return db.update("HoaDon",values,"maHD=?", new String[]{String.valueOf(ob.maHD)});
    }

    public int delete(String id){
        return db.delete("HoaDon","maHD=?", new String[]{id});
    }

    public List<HoaDon> getAll(){
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }

    public HoaDon getID(String id){
        String sql = "SELECT * FROM HoaDon WHERE maHD=?";
        List<HoaDon> list = getData(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @SuppressLint("Range")
    private List<HoaDon> getData(String sql, String...selectionArgs){
        List<HoaDon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        try {
            while (c.moveToNext()){
                HoaDon ob = new HoaDon();
                ob.maHD = c.getInt(c.getColumnIndex("maHD"));
                ob.maNV = c.getInt(c.getColumnIndex("maNV"));
                ob.maKH = c.getInt(c.getColumnIndex("maKH"));
                ob.maGiay = c.getInt(c.getColumnIndex("maGiay"));
                ob.ngay = c.getString(c.getColumnIndex("ngay"));
                ob.giaHD = c.getString(c.getColumnIndex("giaHD"));
                ob.trangThai = c.getInt(c.getColumnIndex("trangThai"));
                list.add(ob);
            }
        } finally {
            if (c != null) c.close();
        }
        return list;
    }
}