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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ptit.quanlysinhvienthuctap.ChiTietGiangVienActivity;
import com.ptit.quanlysinhvienthuctap.R;
import com.ptit.quanlysinhvienthuctap.adapter.GiangVienAdapter;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;

import java.util.ArrayList;
import java.util.List;


public class GiangVienFragment extends Fragment {

    public GiangVienFragment() {
        // Required empty public constructor
    }

    DatabaseHandler db;
    FrameLayout frameLayout;
    List<GiangVien> listGiangVien = new ArrayList<>();
    ListView listView;
    FloatingActionButton floatButton;
    GiangVienAdapter giangVienAdapter;
    Spinner spinnerKinhNghiem;
    private String[] itemKinhNghiem;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_giang_vien, container, false);
        init(view);
        handleView();
        handleEvent();
        return view;
    }

    private void init(View view) {
        db = new DatabaseHandler(getContext());
        listGiangVien = db.getAllGiangVien();
        frameLayout = view.findViewById(R.id.empty);
        floatButton = view.findViewById(R.id.float_button);
        itemKinhNghiem = getResources().getStringArray(R.array.soNamKinhNghiem);
        spinnerKinhNghiem = view.findViewById(R.id.spinner_kinh_nghiem);
        setDataSpinnerKinhNghiem();

        //listview
        listView = view.findViewById(R.id.listview);
        giangVienAdapter = new GiangVienAdapter(listGiangVien, getContext());
        listView.setAdapter(giangVienAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int roomId = listGiangVien.get(i).getId();
                goToDetail(roomId);
            }
        });
    }

    public void goToDetail(int id) {
        Intent intent = new Intent(getActivity(), ChiTietGiangVienActivity.class);
        intent.putExtra("giang_vien_id", id + "");
        startActivityForResult(intent, 2);
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
                Intent intent = new Intent(getActivity(), ChiTietGiangVienActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void setDataSpinnerKinhNghiem() {
        //handle tai san filter
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, itemKinhNghiem);
        spinnerKinhNghiem.setAdapter(adapterSpinner);
        spinnerKinhNghiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //get all
                    giangVienAdapter.updateReceiptsList(db.getAllGiangVien());
                } else {
                    giangVienAdapter.updateReceiptsList(db.getGiangVien10NamKinhNghiem());
                }
                if (giangVienAdapter.getCount() <= 0) {
                    frameLayout.setVisibility(View.VISIBLE);
                } else frameLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2) {
            listGiangVien.clear();
            listGiangVien = db.getAllGiangVien();
            giangVienAdapter.updateReceiptsList(listGiangVien);
        }
        if (listGiangVien.isEmpty()) {
            frameLayout.setVisibility(View.VISIBLE);
        } else frameLayout.setVisibility(View.INVISIBLE);
    }
}