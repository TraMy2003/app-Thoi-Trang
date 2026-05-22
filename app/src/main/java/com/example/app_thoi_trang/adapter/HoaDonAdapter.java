package com.example.app_thoi_trang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_thoi_trang.R;
import com.example.app_thoi_trang.dao.GiayDAO;
import com.example.app_thoi_trang.dao.KhachHangDAO;
import com.example.app_thoi_trang.fragment.HoaDonFragment;
import com.example.app_thoi_trang.model.Giay;
import com.example.app_thoi_trang.model.HoaDon;
import com.example.app_thoi_trang.model.KhachHang;

import java.util.ArrayList;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    private Context context;
    private HoaDonFragment fragment;
    private ArrayList<HoaDon> lists;
    private GiayDAO giayDAO;
    private KhachHangDAO khachHangDAO;

    public HoaDonAdapter(@NonNull Context context, HoaDonFragment fragment, ArrayList<HoaDon> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
        this.giayDAO = new GiayDAO(context);
        this.khachHangDAO = new KhachHangDAO(context);
    }

    private static class ViewHolder {
        TextView tvMaHD, tvTenKH, tvTenGiay, tvGiaMua, tvNgayMua, tvTrangThai;
        ImageView imgDelete;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hoa_don_item, parent, false);
            holder = new ViewHolder();
            holder.tvMaHD = convertView.findViewById(R.id.tvMaHD_item);
            holder.tvTenKH = convertView.findViewById(R.id.tvTenKH_HD_item);
            holder.tvTenGiay = convertView.findViewById(R.id.tvTenGiay_HD_item);
            holder.tvGiaMua = convertView.findViewById(R.id.tvGiaMua_HD_item);
            holder.tvNgayMua = convertView.findViewById(R.id.tvNgayMua_HD_item);
            holder.tvTrangThai = convertView.findViewById(R.id.tvTrangThai_HD_item);
            holder.imgDelete = convertView.findViewById(R.id.img_delete_HD);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final HoaDon item = lists.get(position);
        if (item != null) {
            holder.tvMaHD.setText("Mã HD: " + item.maHD);
            holder.tvGiaMua.setText("Giá: " + item.giaHD);
            holder.tvNgayMua.setText("Ngày: " + item.ngay);

            // Lấy tên giày
            Giay giay = giayDAO.getID(String.valueOf(item.maGiay));
            holder.tvTenGiay.setText("Sản phẩm: " + (giay.tenGiay != null ? giay.tenGiay : "N/A"));

            // Lấy tên khách hàng
            KhachHang khachHang = khachHangDAO.getID(String.valueOf(item.maKH));
            holder.tvTenKH.setText("Khách hàng: " + (khachHang.tenKH != null ? khachHang.tenKH : "N/A"));

            if (item.trangThai == 1) {
                holder.tvTrangThai.setTextColor(Color.GREEN);
                holder.tvTrangThai.setText("Đã thanh toán");
            } else {
                holder.tvTrangThai.setTextColor(Color.RED);
                holder.tvTrangThai.setText("Chưa thanh toán");
            }

            holder.imgDelete.setOnClickListener(v -> {
                if (fragment != null) {
                    fragment.xoaHoaDon(String.valueOf(item.maHD));
                }
            });
        }

        return convertView;
    }
}