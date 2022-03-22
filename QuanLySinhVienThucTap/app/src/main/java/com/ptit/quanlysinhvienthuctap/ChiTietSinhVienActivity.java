package com.ptit.quanlysinhvienthuctap;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;
import com.ptit.quanlysinhvienthuctap.model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSinhVienActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText edtSinhVienName, edtSinhVienId, edtSinhVienNamSinh, edtSinhVienQueQuan;
    private TextInputLayout tilSinhVienName, tilSinhVienId, tilSinhVienNamSinh, tilSinhVienQueQuan;
    DatabaseHandler databaseHelper;
    Spinner spinner;
    String typeChange = "";
    String sinhVienID;
    private String sinhVienName, sinhVienNamSinh, sinhVienQueQuan;
    private Button btnSubmit;
    SinhVien sinhVien;
    List<GiangVien> listGiangVien = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sinh_vien);
        init();
        if (sinhVienID != null)
            setDataPhong(sinhVienID);
        handleView();

    }

    private void handleView() {
        List<String> tenPhong = new ArrayList<>();
        tenPhong.add("Trống");
        for (GiangVien i : listGiangVien) {
            tenPhong.add(i.getName());
        }
        setSpinner(tenPhong);
        boolean isExist = false;
        if (sinhVienID != null) {
            int viTriSpnnerGiangVien = 0;
            for (GiangVien i : listGiangVien) {
                if (i.getId() == sinhVien.getTeacherId()) {
                    viTriSpnnerGiangVien = listGiangVien.indexOf(i);
                    isExist = true;
                    break;
                }
            }
            spinner.setSelection(!isExist ? 0 : (viTriSpnnerGiangVien + 1));
        }
    }

    private void setDataPhong(String giangVienID) {
        sinhVien = databaseHelper.getSinhVienById(giangVienID);
        edtSinhVienId.setText(sinhVien.getId() + "");
        edtSinhVienName.setText(sinhVien.getName());
        edtSinhVienNamSinh.setText(sinhVien.getBod());
        edtSinhVienQueQuan.setText(sinhVien.getAddress() + "");
    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        edtSinhVienId = findViewById(R.id.edt_sinh_vien_id);
        edtSinhVienId.setFocusable(false);
        edtSinhVienId.setEnabled(false);
        edtSinhVienName = findViewById(R.id.edt_sinh_vien_name);
        edtSinhVienNamSinh = findViewById(R.id.edt_sinh_vien_nam_sinh);
        edtSinhVienQueQuan = findViewById(R.id.edt_sinh_vien_que_quan);
        tilSinhVienId = findViewById(R.id.til_sinh_vien_id);
        tilSinhVienName = findViewById(R.id.til_sinh_vien_name);
        tilSinhVienNamSinh = findViewById(R.id.til_sinh_vien_nam_sinh);
        tilSinhVienQueQuan = findViewById(R.id.til_sinh_vien_que_quan);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btn_submit);
        setToolbar();
        databaseHelper = new DatabaseHandler(getBaseContext());
        listGiangVien = databaseHelper.getAllGiangVien();
        sinhVienID = getIntent().getStringExtra("sinh_vien_id");

        if (sinhVienID == null)
            tilSinhVienId.setVisibility(View.GONE);
        else tilSinhVienId.setVisibility(View.VISIBLE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editRoom();
            }
        });
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editRoom() {
        getDataInput();
        setDefaultError();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(sinhVienName)) {
            tilSinhVienName.setError("Không để trống");
            focusView = tilSinhVienName;
            cancel = true;
        } else if (TextUtils.isEmpty(sinhVienNamSinh)) {
            tilSinhVienNamSinh.setError("Không để trống");
            focusView = tilSinhVienNamSinh;
            cancel = true;
        } else if (TextUtils.isEmpty(sinhVienQueQuan)) {
            tilSinhVienQueQuan.setError("Không để trống");
            focusView = tilSinhVienQueQuan;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (sinhVienID != null) {
                int id = Integer.parseInt(sinhVienID);
                SinhVien sinhVien = new SinhVien(id, sinhVienName, sinhVienNamSinh, sinhVienQueQuan,
                        spinner.getSelectedItemPosition() == 0 ? 0
                                : listGiangVien.get(spinner.getSelectedItemPosition() - 1).getId());
                Boolean kq = databaseHelper.updateSinhVien(sinhVien);
                if (kq) {
                    Toast.makeText(getBaseContext(), "Cập nhật sinh viên thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                int id = spinner.getSelectedItemPosition() == 0 ? 0 : listGiangVien.get(spinner.getSelectedItemPosition() - 1).getId();
                SinhVien sinhVien = new SinhVien(sinhVienName, sinhVienNamSinh, sinhVienQueQuan,
                        spinner.getSelectedItemPosition() == 0 ? 0
                                : listGiangVien.get(spinner.getSelectedItemPosition() - 1).getId());
                Boolean kq = databaseHelper.addSinhVien(sinhVien);
                if (kq) {
                    Toast.makeText(getBaseContext(), "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void getDataInput() {
        sinhVienName = edtSinhVienName.getText().toString().trim();
        sinhVienNamSinh = edtSinhVienNamSinh.getText().toString().trim();
        sinhVienQueQuan = edtSinhVienQueQuan.getText().toString().trim();
    }

    public void setDefaultError() {
        tilSinhVienNamSinh.setError(null);
        tilSinhVienId.setError(null);
        tilSinhVienName.setError(null);
    }

    void setSpinner(List<String> strings) {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, strings);
        spinner.setAdapter(adapter);
    }
}