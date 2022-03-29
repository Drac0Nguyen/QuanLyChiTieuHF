package com.example.doanandroid.Controller;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Database.Database;
import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {

    public  static Database database;
    boolean doubleBackToExitPressedOnce = false;
    TextView txtSoDu, txtDisplayName;
    Button btnEditUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       database = new Database(this, "quanlychitieu.sqlite", null, 1);



     database.KhoiTaoBang();

      Cursor cursor =   database.QueryDataResult("SELECT Count(*) FROM TheLoaiThuChi");
     cursor.moveToFirst();
     int icount = cursor.getInt(0);
     if(icount >0)
     {

     }
     else {
          database.Insert_TheLoai(1,"Thu Nhập");
          database.Insert_TheLoai(2,"Chi Tiêu");


     }

     Cursor cursor5 = database.QueryDataResult("SELECT * FROM DisplayName");
     if(cursor5.getCount() <= 0)
     {
            database.Insert_DisplayName("Tên Hiển Thị");
     }
     else
     {

     }
      //  database.QueryNonDataResult("DELETE FROM ThuChiTheoNgay");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txtSoDu = (TextView)findViewById(R.id.txtSoDUMain);
        btnEditUser = (Button)findViewById(R.id.btnEditUser);
        txtDisplayName = (TextView)findViewById(R.id.txtname1);
        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DialogEditUser();
            }
        });

        GetSoDuHienTai();
        GetDisplayName();

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát!", Toast.LENGTH_SHORT).show();
        // Thu Vien: adroid os
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }


    public void ConvertActivity(View view) {
        switch (view.getId())
        {
            case R.id.relativeIncomeSpending: startActivity(new Intent(MainActivity.this, AddThuChiActivity.class));
                break;
            case R.id.relativeHistory: startActivity(new Intent(MainActivity.this, LichSuThuChiActivity.class));
                break;
            case R.id.relativeStatistical: startActivity(new Intent(MainActivity.this, ThongKeActivity.class));
                break;
            case R.id.relativeEditCategory: startActivity(new Intent(MainActivity.this, UpdateCategoryActivity.class));
                break;
        }
    }
    private void GetSoDuHienTai()
    {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);
        float tongThuNhap = 0;
        float tongChiTieu = 0;
        float soDu = 0;
        Cursor cursor = database.QueryDataResult("SELECT Sum(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 1");
        if(cursor.getCount() <= 0)
        {
            tongThuNhap = 0;
        }
        else
        {
            while (cursor.moveToNext())
            {
                tongThuNhap = cursor.getFloat(0);
            }

        }
        Cursor cursor2 = database.QueryDataResult("SELECT Sum(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 2");
        if(cursor2.getCount() <= 0)
        {
            tongChiTieu = 0;
        }
        else
        {
            while (cursor2.moveToNext())
            {
                tongChiTieu = cursor2.getFloat(0);
            }
        }

        soDu = tongThuNhap - tongChiTieu;

        txtSoDu.setText("Số dư hiện tại: " + df.format(soDu) +"VNĐ");

    }
    private void DialogEditUser()
    {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_edit_username);
        // Xet Khong Cho Cham Vao Ben Ngoai Dialog De Huy
        dialog.setCanceledOnTouchOutside(false);
        // Anh Xa
        EditText etUserName = (EditText)dialog.findViewById(R.id.etUserName);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSaveUserName);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancleUserName);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenHienThi = etUserName.getText().toString().trim();
                String test = tenHienThi;
                if(tenHienThi.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.Update_DisplayName(tenHienThi);
                    Toast.makeText(MainActivity.this, "Đã Lưu", Toast.LENGTH_SHORT).show();
                    GetDisplayName();
                    dialog.cancel();

                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
    private void GetDisplayName()
    {
        Cursor cursor = database.QueryDataResult("SELECT TenHienThi FROM DisplayName");
        if(cursor.getCount() <= 0)
        {
            txtDisplayName.setText("Tên Hiển Thị");
        }
        else
        {
            while (cursor.moveToNext())
            {
                String ten = cursor.getString(0);
                txtDisplayName.setText(ten);
            }

        }
    }
}

