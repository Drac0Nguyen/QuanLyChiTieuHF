package com.example.doanandroid.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.example.doanandroid.Adapters.DanhMucAdapter;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddThuChiActivity extends AppCompatActivity {

    Button btnListThu,btnListChi;
    GridView gridView;
    DanhMucAdapter adapter;
    ArrayList<TypeModel>arrayList ; //arrayListThu
    ImageButton imgChonNgay ;
    TextView txtNgay, txtChuDeDaChon;
    Button btnSave;
    int theloai = 2;
    EditText etInPutMoney, etGhiChu;
    ImageView imgChuDeDaChon;
    int chude_id = 0;
    String insert_Red = "", insert_Green = "", insert_Blue = "", insert_ten = "";
    byte[]  insert_hinh = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thu_chi);
        AnhXa();
        arrayList = new ArrayList<>();
        adapter = new DanhMucAdapter( AddThuChiActivity.this,arrayList );
        gridView.setAdapter( adapter );
        SetNgayGioHienTai();
        GetDataChiTieu();

        etInPutMoney.addTextChangedListener(new NDigitCardFormatWatcher(etInPutMoney));
        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Chuyen doi byte[] ve Bitmap
                byte[] hinh = arrayList.get(position).getImgView();
                Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0, hinh.length);
                int red  = Integer.parseInt(arrayList.get(position).getRed());
                int green  = Integer.parseInt(arrayList.get(position).getGreen());
                int blue  = Integer.parseInt(arrayList.get(position).getBlue());


                // Lay id cua danh muc duoc chon

                chude_id = arrayList.get(position).getID();

                // Lay ten danh muc duoc chon

                insert_ten = arrayList.get(position).getText();

                String test = insert_ten;
                // Lay ma mau

                insert_Red = String.valueOf(red);
                insert_Green = String.valueOf(green);
                insert_Blue = String.valueOf(blue);

                // LAY HINH
                // click item to change background color
                imgChuDeDaChon.setImageBitmap(bitmap );
                imgChuDeDaChon.setBackgroundColor(Color.rgb(red,green,blue));
                txtChuDeDaChon.setText( arrayList.get(position).getText() );



            }
        } );
        btnListChi.setTextColor( Color.BLUE );
        btnListChi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataChiTieu();
                // Xac dinh the loai add
                theloai = 2; // 2 tuong trung cho Chi Tieu


                //click button to change button text color
                btnListChi.setTextColor( Color.BLUE );
                btnListThu.setTextColor( Color.WHITE );
                // re-initialize
                adapter = new DanhMucAdapter( AddThuChiActivity.this,arrayList );
                gridView.setAdapter( adapter );
            }
        } );
        btnListThu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetDataThuNhap();
                // Xac dinh the loai add
                theloai = 1; // 1 tuong trung cho Thu Nhap


                btnListThu.setTextColor( Color.BLUE );
                btnListChi.setTextColor( Color.WHITE );
                adapter = new DanhMucAdapter( AddThuChiActivity.this,arrayList );
                gridView.setAdapter( adapter );
            }
        } );
        imgChonNgay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        } );

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator('.');
                DecimalFormat df = new DecimalFormat("###,###,###,###", symbols);

                String ngayDuocChon =  txtNgay.getText().toString().trim();
                String soTien_chuoi = etInPutMoney.getText().toString();




                if(soTien_chuoi.equals("") )
                {
                    Toast.makeText(AddThuChiActivity.this,"Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();

                }
                else {

                    String ghiChu = etGhiChu.getText().toString();
                    int theLoai = theloai;



                    //    float soTien = Float.parseFloat(soTien_chuoi.replaceAll("\\." ,""));
                    float soTien = Float.parseFloat(soTien_chuoi.replaceAll(",",""));

                    float test = soTien;
//                    DecimalFormat df = new DecimalFormat("0");



                    if(soTien <= 0)
                    {
                        Toast.makeText(AddThuChiActivity.this,"Vui lòng nhập số tiền lớn hơn 0", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        if(  chude_id == 0)
                        {

                            Toast.makeText(AddThuChiActivity.this,"Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            // Chuyen tu imageview to byte[]
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgChuDeDaChon.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                            // Dong cuoi
                            byte[] insert_hinh = byteArray.toByteArray();
                            // Cat chuoi -> Lay ra thang nam duoc chon  MM/yyyy
                            String thangNamThuChi = ngayDuocChon.substring(3,10);  // begin - ( end - 1)
                            float thuNhapTrungBinh = 0;
                            float tongThuNhapThang = 0;
                            float tongChiTieuThang = 0;
                            Cursor data1 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 1 AND ThangThuNhap = '07/2021'");
                            {
                                while (data1.moveToNext())
                                {
                                    tongThuNhapThang = data1.getFloat(0);
                                }

                            }
                            Cursor data2 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 2 AND ThangThuNhap = '07/2021'");
                            {
                                while (data2.moveToNext())
                                {
                                    tongChiTieuThang = data2.getFloat(0);
                                }

                            }




                            Cursor data = MainActivity.database.QueryDataResult("SELECT Sum(GIA)/30 FROM ThuChiTheoNgay WHERE TheLoai = 1 AND ThangThuNhap = '07/2021'");
                            while (data.moveToNext())
                            {
                                thuNhapTrungBinh  = data.getFloat(0);

                            }


                            if(theLoai == 1)
                            {
                                MainActivity.database.Insert_ThuChiTheoNgay(ghiChu,soTien,ngayDuocChon,thangNamThuChi,chude_id,insert_ten,insert_Red,insert_Green,insert_Blue,insert_hinh,1);
                                Toast.makeText(AddThuChiActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddThuChiActivity.this,LichSuThuChiActivity.class));
                            }
                            else
                            {
                                if(tongThuNhapThang <= tongChiTieuThang  || (tongThuNhapThang - tongChiTieuThang) < soTien)
                                {
                                    if(theLoai == 2)
                                    {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddThuChiActivity.this);
                                        alertDialog.setTitle("Cảnh báo!");
                                        alertDialog.setIcon(R.drawable.icon_canhbao2);
                                        alertDialog.setMessage("Số tiền chi tiêu trong tháng này của bạn đã vượt mức cho phép. \n Tổng chi tiêu: "+df.format(tongChiTieuThang)+" \n Tổng thu nhập: "+df.format(tongThuNhapThang)+"");
                                        alertDialog.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.cancel();
                                                MainActivity.database.Insert_ThuChiTheoNgay(ghiChu,soTien,ngayDuocChon,thangNamThuChi,chude_id,insert_ten,insert_Red,insert_Green,insert_Blue,insert_hinh,theLoai);
                                                Toast.makeText(AddThuChiActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(AddThuChiActivity.this,LichSuThuChiActivity.class));
                                            }
                                        });
                                        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();


                                    }

                                }
                                else if (soTien > thuNhapTrungBinh && theLoai == 2)
                                {



                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddThuChiActivity.this);
                                    alertDialog.setTitle("Cảnh báo!");
                                    alertDialog.setIcon(R.drawable.icon_canhbao2);
                                    alertDialog.setMessage("Số tiền chi tiêu trong ngày của bạn đã vượt mức cho phép. \n Số tiền nên chi trong ngày: < "+ df.format(thuNhapTrungBinh)+"");
                                    alertDialog.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.cancel();
                                            MainActivity.database.Insert_ThuChiTheoNgay(ghiChu,soTien,ngayDuocChon,thangNamThuChi,chude_id,insert_ten,insert_Red,insert_Green,insert_Blue,insert_hinh,theLoai);

                                            Toast.makeText(AddThuChiActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();



                                            startActivity(new Intent(AddThuChiActivity.this,LichSuThuChiActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                }
                                else if(soTien < thuNhapTrungBinh && (tongThuNhapThang - tongChiTieuThang) > soTien && theLoai == 2 )
                                { MainActivity.database.Insert_ThuChiTheoNgay(ghiChu,soTien,ngayDuocChon,thangNamThuChi,chude_id,insert_ten,insert_Red,insert_Green,insert_Blue,insert_hinh,theLoai);

                                    Toast.makeText(AddThuChiActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();



                                    startActivity(new Intent(AddThuChiActivity.this,LichSuThuChiActivity.class));
                                }

                            }


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
    public void AnhXa()
    {
        ;
        gridView = (GridView)findViewById( R.id.gridView );
        btnListThu = (Button)findViewById( R.id.btnListThu );
        btnListChi = (Button)findViewById( R.id.btnListChi );
        imgChonNgay= (ImageButton)findViewById( R.id.imgChonNgay );
        txtNgay = (TextView)findViewById( R.id.txtNgay );
        btnSave = (Button)findViewById(R.id.btnLuu);
        etInPutMoney = (EditText)findViewById(R.id.etInputMoney);
        etGhiChu = (EditText)findViewById(R.id.edtGhiChu);
        txtChuDeDaChon=(TextView)findViewById( R.id.txtChuDeDaChon );
        imgChuDeDaChon = (ImageView)findViewById( R.id.imgChuDeDaChon );

    }







    public void ChonNgay()
    {
        Calendar calendar = Calendar.getInstance();

        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtNgay.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    public void SetNgayGioHienTai()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgay.setText(simpleDateFormat.format(calendar.getTime()));

    }





}