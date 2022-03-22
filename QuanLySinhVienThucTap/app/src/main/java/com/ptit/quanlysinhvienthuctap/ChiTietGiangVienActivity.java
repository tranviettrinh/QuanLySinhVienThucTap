package com.ptit.quanlysinhvienthuctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ptit.quanlysinhvienthuctap.database.DatabaseHandler;
import com.ptit.quanlysinhvienthuctap.model.GiangVien;

public class ChiTietGiangVienActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TextInputEditText edtGiangVienName, edtGiangVienId, edtGiangVienNamSinh, edtExp;
    private TextInputLayout tilGiangVienName, tilGiangVienId, tilGiangVienNamSinh, tilExp;
    DatabaseHandler databaseHelper;
    String typeChange = "";
    String giangVienID;
    private String giangVienName, giangVienNamSinh, giangVienExp;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_giang_vien);
        init();
        if(giangVienID!= null)
            setDataPhong(giangVienID);
    }

    private void setDataPhong(String giangVienID) {
        GiangVien giangVien = databaseHelper.getGiangVienById(giangVienID);
        edtGiangVienId.setText(giangVien.getId() + "");
        edtGiangVienName.setText(giangVien.getName());
        edtGiangVienNamSinh.setText(giangVien.getBod());
        edtExp.setText(giangVien.getExp()+"");
    }

    private void init() {
        edtGiangVienId = findViewById(R.id.edt_sinh_vien_id);
        edtGiangVienId.setFocusable(false);
        edtGiangVienId.setEnabled(false);
        edtGiangVienName = findViewById(R.id.edt_sinh_vien_name);
        edtGiangVienNamSinh = findViewById(R.id.edt_sinh_vien_nam_sinh);
        edtExp = findViewById(R.id.edt_sinh_vien_que_quan);
        tilGiangVienId = findViewById(R.id.til_sinh_vien_id);
        tilGiangVienName = findViewById(R.id.til_sinh_vien_name);
        tilGiangVienNamSinh = findViewById(R.id.til_sinh_vien_nam_sinh);
        tilExp = findViewById(R.id.til_sinh_vien_que_quan);
        toolbar = findViewById(R.id.toolbar);
        btnSubmit = findViewById(R.id.btn_submit);
        setToolbar();
        databaseHelper = new DatabaseHandler(getBaseContext());
        giangVienID = getIntent().getStringExtra("giang_vien_id");

        if(giangVienID == null)
            tilGiangVienId.setVisibility(View.GONE);
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
        String regex = "[0-9]+";
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(giangVienName)) {
            tilGiangVienName.setError("Không để trống");
            focusView = tilGiangVienName;
            cancel = true;
        } else if (TextUtils.isEmpty(giangVienNamSinh)) {
            tilGiangVienNamSinh.setError("Không để trống");
            focusView = tilGiangVienNamSinh;
            cancel = true;
        } else if (TextUtils.isEmpty(giangVienExp)) {
            tilExp.setError("Không để trống");
            focusView = tilExp;
            cancel = true;
        }else if (!giangVienExp.matches(regex)) {
            tilExp.setError("Không phải số");
            focusView = tilExp;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if(giangVienID != null){
                int id = Integer.parseInt(giangVienID);
                GiangVien giangVien = new GiangVien(id, giangVienName, giangVienNamSinh, Integer.parseInt(giangVienExp));
                Boolean kq = databaseHelper.updateGiangVien(giangVien);
                if (kq) {
                    Toast.makeText(getBaseContext(), "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else{
                GiangVien giangVien = new GiangVien(giangVienName, giangVienNamSinh, Integer.parseInt(giangVienExp));
                Boolean kq = databaseHelper.addGiangVien(giangVien);
                if (kq) {
                    Toast.makeText(getBaseContext(), "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public void getDataInput() {
        giangVienName = edtGiangVienName.getText().toString().trim();
        giangVienNamSinh = edtGiangVienNamSinh.getText().toString().trim();
        giangVienExp = edtExp.getText().toString().trim();
    }

    public void setDefaultError() {
        tilGiangVienNamSinh.setError(null);
        tilGiangVienId.setError(null);
        tilGiangVienName.setError(null);
    }
}