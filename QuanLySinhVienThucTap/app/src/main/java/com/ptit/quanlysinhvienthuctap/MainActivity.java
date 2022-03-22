package com.ptit.quanlysinhvienthuctap;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ptit.quanlysinhvienthuctap.R;
import com.ptit.quanlysinhvienthuctap.fragment.GiangVienFragment;
import com.ptit.quanlysinhvienthuctap.fragment.SinhVienFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
//     db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleView();
        handleEvent();

    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }


    private void handleView() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_view, new SinhVienFragment(), null)
                .commit();
    }

    private void handleEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        replaceFragment(new SinhVienFragment());
                        break;
                    case R.id.page_2:
                        replaceFragment(new GiangVienFragment());
                        break;
                }
                return true;
            }
        });
    }


    void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, fragment, null)
                .commit();
    }
}