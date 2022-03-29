package com.example.doanandroid.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Adapters.LichSuAdapter;
import com.example.doanandroid.Models.ThongTinChiTieuModel;
import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LichSuThuChiActivity extends AppCompatActivity {
    LichSuAdapter adapter;
    ArrayList<ThongTinChiTieuModel>arrayList;
    ListView lv;
    ImageButton add;
    TextView txtNgayThangNam, txtThuNhap, txtChiTieu, txtSoDu;
    DatePickerDialog datePicker;
    int year,month,dayOfMonth;
    Calendar calendar;
    float tongthunhap, tongchitieu;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_thu_chi);
        lv = (ListView)findViewById( R.id.lvLichSu );
        add = (ImageButton)findViewById( R.id.imgBtnAdd );
        txtNgayThangNam = (TextView)findViewById( R.id.txtNgay );
        txtThuNhap = (TextView)findViewById(R.id.txtThuNhap);
        txtChiTieu = (TextView)findViewById(R.id.txtChiTieu);
        txtSoDu = (TextView)findViewById(R.id.txtSoDu);
        arrayList = new ArrayList<>();


        adapter = new LichSuAdapter( LichSuThuChiActivity.this,arrayList );
        lv.setAdapter( adapter );






        lv.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LichSuThuChiActivity.this, XemChiTietActivity.class);
                intent.putExtra( "type",arrayList.get(position) );
                startActivity(intent);
            }
        } );

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayThangNam.setText( simpleDateFormat.format( calendar.getTime() ) );
        String ngayChon = txtNgayThangNam.getText().toString().trim();


        add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichSuThuChiActivity.this,AddThuChiActivity.class);
                startActivity( intent );
            }
        } );

        GetTongThuNhapNgay(ngayChon);
        GetTongChiTieuNgay(ngayChon);
        GetLichSuThuChi(ngayChon);
        GetSoDu();


    }
    public void perform_action(View v){

        calendar= Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker = new DatePickerDialog( LichSuThuChiActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set( year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
              txtNgayThangNam.setText( simpleDateFormat.format( calendar.getTime() ) );
                String ngayChon = txtNgayThangNam.getText().toString().trim();
                GetTongThuNhapNgay(ngayChon);
                GetTongChiTieuNgay(ngayChon);
                GetLichSuThuChi(ngayChon);
                GetSoDu();
            }
        },year,month,dayOfMonth );
        datePicker.show();
    }

    public void GetLichSuThuChi(String ngayDaChon)
    {
        Cursor data = MainActivity.database.QueryDataResult("SELECT a.ID, a.MoTa, a.Gia, a.NgayThuNhap, a.ThangThuNhap, b.TenTheLoai, a.DanhMuc_ID, a.TenDanhMuc, a.Red, a.Green, a.Blue, a.Hinh  FROM ThuChiTheoNgay as a, TheLoaiThuChi as b WHERE a.TheLoai = b.ID  and NgayThuNhap = '"+ngayDaChon+"'");

        arrayList.clear();
        while (data.moveToNext())
        {
                int id_thuchi = data.getInt(0);
                String moTa = data.getString(1);
                float gia = data.getFloat(2);
                String ngayThuNhap = data.getString(3);
                String thangThuNhap = data.getString(4);
                String tenTheLoai = data.getString(5);
                int danhMuc_ID = data.getInt(6);
                String tenChuDe = data.getString(7);
                String red = data.getString(8);
                String green = data.getString(9);
                String blue = data.getString(10);
                byte[] hinh = data.getBlob(11);
                arrayList.add(new ThongTinChiTieuModel(id_thuchi,moTa,gia,ngayThuNhap,thangThuNhap,tenTheLoai,danhMuc_ID,tenChuDe,red,green,blue,hinh));





        }
        adapter.notifyDataSetChanged();


    }
    public void GetTongThuNhapNgay(String ngayDuocChon )
    {
        Cursor data = MainActivity.database.QueryDataResult("SELECT SUM(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 1 AND NgayThuNhap = '"+ngayDuocChon+"'");
        while (data.moveToNext())
        {
             tongthunhap = data.getFloat(0);
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);
            txtThuNhap.setText(String.valueOf(df.format(tongthunhap)));
        }
    }
    public void GetTongChiTieuNgay(String ngayDuocChon)
    {

        Cursor data = MainActivity.database.QueryDataResult("SELECT SUM(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 2 AND NgayThuNhap = '"+ngayDuocChon+"'");
        while (data.moveToNext())
        {
             tongchitieu = data.getFloat(0);
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);
            txtChiTieu.setText(String.valueOf(df.format(tongchitieu)));
        }
    }

    public void GetSoDu()
    {
        float soDu  = tongthunhap - tongchitieu;
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);
        txtSoDu.setText(String.valueOf(df.format(soDu)));


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LichSuThuChiActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
