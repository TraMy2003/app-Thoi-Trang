package com.example.app_thoi_trang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.app_thoi_trang.R;
import com.example.app_thoi_trang.adapter.TopAdapter;
import com.example.app_thoi_trang.dao.ThongKeDAO;
import com.example.app_thoi_trang.model.Top;

import java.util.ArrayList;


public class TopFragment extends Fragment {
    ListView lv;
    ArrayList<Top> list;
    TopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        ThongKeDAO thongKeDAO = new ThongKeDAO(getActivity());
        list = (ArrayList<Top>) thongKeDAO.getTop();
        lv = v.findViewById(R.id.lvTop);
        adapter = new TopAdapter(getActivity(),this,list);
        lv.setAdapter(adapter);
        return v;

    }
}