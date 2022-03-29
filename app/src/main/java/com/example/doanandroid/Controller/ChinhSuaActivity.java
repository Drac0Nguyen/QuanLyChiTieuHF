package com.example.doanandroid.Controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Adapters.DanhMucAdapter;
import com.example.doanandroid.Models.ThongTinChiTieuModel;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChinhSuaActivity extends AppCompatActivity {

    Button btnListThu, btnListChi, btnCapNhat;
    GridView gridView;
    DanhMucAdapter adapter;
    ArrayList<TypeModel> arrayListThu, arrayList;
    ImageButton imgChonNgay;
    TextView txtNgay, txtChuDeDaChon, txtTenDanhMuc;
    ImageView imgChuDeDaChon;
    DatePickerDialog datePicker;
    int year, month, dayOfMonth;
    Calendar calendar;
    ThongTinChiTieuModel type2;
    EditText etEditPrice, etEditNote;
    int theloai = 2;
    int chude_id = 0;
    String insert_Red = "", insert_Green = "", insert_Blue = "", insert_ten = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua);
        gridView = (GridView) findViewById(R.id.gridView);
        btnListThu = (Button) findViewById(R.id.btnListThu);
        btnListChi = (Button) findViewById(R.id.btnListChi);
        imgChonNgay = (ImageButton) findViewById(R.id.imgChonNgay);

        txtNgay = (TextView) findViewById(R.id.txtEditNgayDetail);
        txtChuDeDaChon = (TextView) findViewById(R.id.txtEditTenDanhMuc);
        imgChuDeDaChon = (ImageView) findViewById(R.id.imgEditDanhMucDetail);
        etEditPrice = (EditText)findViewById(R.id.etPriceDetail);
        etEditNote = (EditText)findViewById(R.id.edtEditGhiChuDetail);
        btnCapNhat = (Button)findViewById(R.id.btnCapNhat);
        txtTenDanhMuc = (TextView)findViewById(R.id.txtEditTenDanhMuc);



        Intent intent = getIntent();
        type2 = (ThongTinChiTieuModel) intent.getSerializableExtra("typeEdit");
        int id = type2.getiD();
        byte[] hinh = type2.getHinh();
        int red = Integer.parseInt(type2.getRed());
        int green = Integer.parseInt(type2.getGreen());
        int blue = Integer.parseInt(type2.getBlue());
        String moTa = type2.getMoTa();
        String ngay = type2.getNgayThuNhap();
        float soTien = type2.getGia();
        String theLoai = type2.getTenTheLoai();

        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        imgChuDeDaChon.setImageBitmap(bitmap);
        imgChuDeDaChon.setBackgroundColor(Color.rgb(red,green,blue));
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###", symbols);
        etEditPrice.setText(df.format(soTien) + "");
        etEditNote.setText(moTa);
        txtNgay.setText(ngay);



        etEditPrice.addTextChangedListener(new NDigitCardFormatWatcher(etEditPrice));



        arrayList = new ArrayList<>();


        adapter = new DanhMucAdapter(ChinhSuaActivity.this, arrayList);
        gridView.setAdapter(adapter);
        GetDataChiTieu();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                byte[] hinh = arrayList.get(position).getImgView();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinh, 0, hinh.length);
                int red2 = Integer.parseInt(arrayList.get(position).getRed());
                int green2 = Integer.parseInt(arrayList.get(position).getGreen());
                int blue2 = Integer.parseInt(arrayList.get(position).getBlue());
                imgChuDeDaChon.setImageBitmap(bitmap);
                imgChuDeDaChon.setBackgroundColor(Color.rgb(red2,green2,blue2));
                txtChuDeDaChon.setText(arrayList.get(position).getText());
                chude_id = arrayList.get(position).getID();

                // Lay ten danh muc duoc chon

                insert_ten = arrayList.get(position).getText();

                String test = insert_ten;
                // Lay ma mau

                insert_Red = String.valueOf(red2);
                insert_Green = String.valueOf(green2);
                insert_Blue = String.valueOf(blue2);





            }
        });
        btnListChi.setTextColor(Color.BLUE);
        btnListChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theloai = 2;
                GetDataChiTieu();
                //click button to change button text color
                btnListChi.setTextColor(Color.BLUE);
                btnListThu.setTextColor(Color.WHITE);
                // re-initialize
                adapter = new DanhMucAdapter(ChinhSuaActivity.this, arrayList);
                gridView.setAdapter(adapter);

            }
        });
        btnListThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theloai = 1;
                GetDataThuNhap();
                btnListThu.setTextColor(Color.BLUE);
                btnListChi.setTextColor(Color.WHITE);
                adapter = new DanhMucAdapter(ChinhSuaActivity.this, arrayList);
                gridView.setAdapter(adapter);
            }
        });
        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(ChinhSuaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        txtNgay.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, dayOfMonth);
                datePicker.show();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inPutPrice = etEditPrice.getText().toString();

                float soTien = Float.parseFloat(inPutPrice.replaceAll("," , ""));



                int theLoai = theloai;
                String ngayDuocChon = txtNgay.getText().toString().trim();
                String ghiChu = etEditNote.getText().toString().trim();

                if(inPutPrice.equals("") )
                {
                    Toast.makeText(ChinhSuaActivity.this,"Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();

                }
                else {
                    if (soTien <= 0) {
                        Toast.makeText(ChinhSuaActivity.this, "Vui lòng nhập số tiền lớn hơn 0", Toast.LENGTH_SHORT).show();
                    } else {
                        if (chude_id == 0) {
                            Toast.makeText(ChinhSuaActivity.this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();

                        } else {


                            // Chuyen tu imageview to byte[]
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgChuDeDaChon.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                            // Dong cuoi
                            byte[] insert_hinh = byteArray.toByteArray();

                            // Cat chuoi -> Lay ra thang nam duoc chon  MM/yyyy
                            String thangNamThuChi = ngayDuocChon.substring(3, 10);  // begin - ( end - 1)
                            MainActivity.database.Update_LichSuThuChi(moTa,soTien,ngayDuocChon,thangNamThuChi,chude_id,insert_ten, insert_Red, insert_Green
                                    , insert_Blue, insert_hinh,theLoai,id);
                            Toast.makeText(ChinhSuaActivity.this,"Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ChinhSuaActivity.this,LichSuThuChiActivity.class));
                        }
                    }
                }


                        }
        });

    }
    public void GetDataThuNhap()
    {
        Cursor data = MainActivity.database.QueryDataResult("SELECT * FROM ChuDe WHERE theloai = 1");
        arrayList.clear();
        while (data.moveToNext())
        {



            arrayList.add(new TypeModel (
                    data.getInt(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getBlob(5),
                    data.getInt(6)));


        }
        adapter.notifyDataSetChanged();
    }

    public void GetDataChiTieu()
    {
        Cursor data = MainActivity.database.QueryDataResult("SELECT * FROM ChuDe WHERE theloai = 2");
        arrayList.clear();
        while (data.moveToNext())
        {

            arrayList.add(new TypeModel (
                    data.getInt(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getBlob(5),
                    data.getInt(6)));

        }
        adapter.notifyDataSetChanged();
    }

}
