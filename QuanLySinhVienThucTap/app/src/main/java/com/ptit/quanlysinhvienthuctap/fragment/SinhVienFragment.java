package com.ptit.quanlysinhvienthuctap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ptit.quanlysinhvienthuctap.R;
import com.ptit.quanlysinhvienthuctap.ChiTietSinhVienActivity;
import com.ptit.quanlysinhvienthuctap.adapter.SinhVienAdapter;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;
import com.ptit.quanlysinhvienthuctap.model.SinhVien;

import java.util.ArrayList;
import java.util.List;


public class SinhVienFragment extends Fragment {


    public SinhVienFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sinh_vien, container, false);
//    }
    DatabaseHandler db;
    FrameLayout frameLayout;
    List<GiangVien> listGiangVien = new ArrayList<>();
    List<SinhVien> listSinhVien = new ArrayList<>();
    ListView listView;
    FloatingActionButton floatButton;
    SinhVienAdapter sinhVienAdapter;
    Spinner spGiangVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sinh_vien, container, false);
        init(view);
        handleView();
        handleEvent();
        setDataSpinnerGianVien();
        return view;
    }

    private void handleView() {
        if (listGiangVien.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else
            frameLayout.setVisibility(View.INVISIBLE);
    }

    private void handleEvent() {
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietSinhVienActivity.class);
                startActivityForResult(intent, 3);//nhận kết quả và cập nhật lại giao diện
            }
        });
    }

    private void init(View view) {
        listView = view.findViewById(R.id.listview);
        db = new DatabaseHandler(getContext());
        listGiangVien = db.getAllGiangVien();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);
        listSinhVien = db.getAllSinhVien();
        spGiangVien = view.findViewById(R.id.spinner_giang_vien);
        //listview
        listView = view.findViewById(R.id.listview);
        sinhVienAdapter = new SinhVienAdapter(listSinhVien, getContext(),db.getAllGiangVien());
        listView.setAdapter(sinhVienAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int sinhVienID = listSinhVien.get(i).getId();
                goToDetail(sinhVienID);
            }
        });
    }
    public void goToDetail(int id) {
        Intent intent = new Intent(getActivity(), ChiTietSinhVienActivity.class);
        intent.putExtra("sinh_vien_id", String.valueOf(id));
        startActivityForResult(intent, 4);//nhận kết quả và cập nhật lại giao diện
    }
    public void setDataSpinnerGianVien() {
        listGiangVien = db.getAllGiangVien();
        List<String> listNameGiangVien = new ArrayList<>();
        listNameGiangVien.add("Tất cả");
        for (GiangVien gv : listGiangVien) {
            listNameGiangVien.add(gv.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listNameGiangVien);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spGiangVien.setAdapter(adapter);
        this.spGiangVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sinhVienAdapter.updateReceiptsList(db.getAllSinhVien());
                } else {
                    GiangVien gv = listGiangVien.get(position - 1);
                    List<SinhVien> a = db.getSinhVienCuaGV(gv.getId());
                    sinhVienAdapter.updateReceiptsList(db.getSinhVienCuaGV(gv.getId()));
                }
                if(sinhVienAdapter.getCount()<=0){
                    frameLayout.setVisibility(View.VISIBLE);
                }else frameLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 || requestCode == 4) {
            List<SinhVien> list = db.getAllSinhVien();
            sinhVienAdapter.updateReceiptsList(list);
            if(list.isEmpty()){
                frameLayout.setVisibility(View.VISIBLE);
            }else frameLayout.setVisibility(View.INVISIBLE);
        }
    }
}