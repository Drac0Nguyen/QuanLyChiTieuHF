package com.example.doanandroid.Controller;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Models.ThongTinChiTieuModel;
import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class XemChiTietActivity extends AppCompatActivity {
    Button btnChinhSua,btnXoa;
    ThongTinChiTieuModel type;
   private ImageView imgDetail;
   private TextView txtDanhMuc, txtMotaDetail, txtNgayDetail, txtGiaDetail, txtTheLoaiDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_xem_chi_tiet );

        btnChinhSua = (Button)findViewById( R.id.btnChinhSua );
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        txtDanhMuc = (TextView)findViewById(R.id.txtDanhMucDetail);
        txtMotaDetail = (TextView)findViewById(R.id.txtMotaDetail);
        txtNgayDetail = (TextView)findViewById(R.id.txtNgayDetail);
        txtGiaDetail = (TextView)findViewById(R.id.txtTien);
        txtTheLoaiDetail = (TextView)findViewById(R.id.txtTheLoaiDetail);
        btnXoa = (Button)findViewById(R.id.btnXoa);

        Intent intent = getIntent();
        type = (ThongTinChiTieuModel) intent.getSerializableExtra( "type" );
        // Lay id cua item duoc chon
        int id = type.getiD();
        byte[] hinh = type.getHinh();
        int red = Integer.parseInt(type.getRed());
        int green = Integer.parseInt(type.getGreen());
        int blue = Integer.parseInt(type.getBlue());
        String moTa = type.getMoTa();
        String ngay = type.getNgayThuNhap();
        float soTien = type.getGia();
        String theLoai = type.getTenTheLoai();





        String tenDanhMuc = type.getTenChuDe();

        // Chuyen byte[] ve image
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        imgDetail.setImageBitmap(bitmap);
        imgDetail.setBackgroundColor(Color.rgb(red,green,blue));
        txtDanhMuc.setText(tenDanhMuc);
        txtTheLoaiDetail.setText(theLoai);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);
        if(theLoai.equals("Thu Nhập"))
        {
            txtGiaDetail.setText("+" + df.format(soTien) + "");
        }
        else {
            txtGiaDetail.setText( "-" + df.format(soTien) + "");

        }

        txtMotaDetail.setText(moTa);
        txtNgayDetail.setText(ngay);


        btnChinhSua.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(XemChiTietActivity.this,ChinhSuaActivity.class);
                intent.putExtra("typeEdit", type);
                startActivity( intent );
            }
        } );

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(XemChiTietActivity.this);
                dialog.setTitle("Xác nhận");
                dialog.setIcon(R.drawable.icons8_bell_48);
                dialog.setMessage("Bạn có chắc muốn xóa "+tenDanhMuc+" ra khỏi danh sách?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity.database.Delete_LichSuThuChi(id);
                        Toast.makeText(XemChiTietActivity.this,"Xóa Thành Công",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(XemChiTietActivity.this, LichSuThuChiActivity.class));

                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });


    }
}