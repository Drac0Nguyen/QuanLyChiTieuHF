package com.example.doanandroid.Controller;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doanandroid.Fragments.FragmentThongKeDanhMuc;
import com.example.doanandroid.Fragments.FragmentThongKeTienVon;
import com.example.doanandroid.R;

public class ThongKeActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        FragmentThongKeDanhMuc fragment = new FragmentThongKeDanhMuc();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameThongKe, fragment);
        fragmentTransaction.commit();



     //   FragmentThongKeDanhMuc fragment = (FragmentThongKeDanhMuc) getSupportFragmentManager().findFragmentById(R.id.fragThongKeDanhMuc);

    }

    public void AddFragment(View view) {

        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment =  null;
        switch (view.getId())
        {
            case R.id.btnThongKeDanhMuc: fragment = new FragmentThongKeDanhMuc();
                break;
            case R.id.btnThongKeThuChi: fragment = new FragmentThongKeTienVon();

        }
        fragmentTransaction.replace(R.id.frameThongKe, fragment);
        fragmentTransaction.commit();
    }
}