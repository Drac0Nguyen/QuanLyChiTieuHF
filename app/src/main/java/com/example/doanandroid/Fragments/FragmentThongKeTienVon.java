package com.example.doanandroid.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doanandroid.Adapters.ThongKeTienVonAdapter;
import com.example.doanandroid.Controller.MainActivity;
import com.example.doanandroid.Models.ThongKeTienVonModel;
import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentThongKeTienVon extends Fragment {
    View view;

    ThongKeTienVonAdapter adapter;
    ListView lvThongKeTienVon;
    ArrayList<ThongKeTienVonModel> arrayList;
    float tongThuNhap, tongChiTieu, SoDu;


    int year, month, dayOfMonth;
    DatePickerDialog datePicker;
    TextView txtNgay;
    String ngayDuocchon = "";
    String[] thangthunhap = {};
    ImageView imgChonNgay;
    Button btnThongKe;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_thongke_tienvon, container, false);

        lvThongKeTienVon = (ListView)view.findViewById( R.id.lvThongKeTienVon );
        txtNgay = (TextView) view.findViewById(R.id.txtNgayThongKeTienVon);
        imgChonNgay = (ImageView) view.findViewById(R.id.imgChonNgayThongKeTienVon);
        btnThongKe = (Button)view.findViewById(R.id.btnThongKeTienVon);
        SetNgayGioHienTai();
        ngayDuocchon  = txtNgay.getText().toString().trim();


        arrayList = new ArrayList<>();
        adapter = new ThongKeTienVonAdapter( getActivity(),arrayList );
        lvThongKeTienVon.setAdapter( adapter );
        GetData(ngayDuocchon);

        imgChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePicker = new DatePickerDialog(getActivity() ,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        //   String thangNamThuChi = txtNgay.getText().toString().substring(3,10);  // begin - ( end - 1)
                        String thangNamThuChi =   simpleDateFormat.format(calendar.getTime()).substring(3,10);
                        //   simpleDateFormat.format(calendar.getTime())
                        txtNgay.setText(thangNamThuChi);

                    }
                }, year, month, dayOfMonth);

                datePicker.show();

            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  GetData(txtNgay.getText().toString().trim());
            }
        });

        return view;
    }

    private void GetData(String ngayChon)
    {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###,###", symbols);

        String thangThuNhap = "";

        Cursor checkNull = MainActivity.database.QueryDataResult("SELECT ThangThuNhap FROM ThuChiTheoNgay WHERE  ThangThuNhap = '"+ngayChon+"' ");
        if(checkNull.getCount() > 0)
        {

            Cursor data3 = MainActivity.database.QueryDataResult("SELECT DISTINCT ThangThuNhap,Sum(Gia) FROM ThuChiTheoNgay  WHERE TheLoai = 1  and ThangThuNhap = '"+ngayChon+"' Group by ThangThuNhap");
            arrayList.clear();
            if(data3.getCount() <= 0)
            {
               tongThuNhap = 0;

                Cursor data2 = MainActivity.database.QueryDataResult("SELECT DISTINCT  ThangThuNhap,Sum(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 2 and ThangThuNhap = '"+ngayChon+"'  Group by ThangThuNhap");
                while (data2.moveToNext())
                {
                    thangThuNhap = data2.getString(0);
                    tongChiTieu = data2.getFloat(1);


                    SoDu = tongThuNhap - tongChiTieu;

                    arrayList.add(new ThongKeTienVonModel(thangThuNhap, df.format(tongThuNhap),df.format(tongChiTieu),df.format(SoDu)));

                }
            }
            else
            {
                while (data3.moveToNext())
                {
                   thangThuNhap = data3.getString(0);
                    // Chay 1 lan

                    tongThuNhap = data3.getFloat(1);

                    Cursor data2 = MainActivity.database.QueryDataResult("SELECT DISTINCT  ThangThuNhap,Sum(Gia) FROM ThuChiTheoNgay WHERE TheLoai = 2 and ThangThuNhap = '"+ngayChon+"'  Group by ThangThuNhap");
                    if(data2.getCount() <= 0)
                    {

                        arrayList.add(new ThongKeTienVonModel(thangThuNhap,df.format(tongThuNhap),"0",df.format(tongThuNhap)));
                    }
                    else
                    {
                        while (data2.moveToNext())
                        {

                            tongChiTieu = data2.getFloat(1);


                            SoDu = tongThuNhap - tongChiTieu;

                            arrayList.add(new ThongKeTienVonModel(thangThuNhap, df.format(tongThuNhap),df.format(tongChiTieu),df.format(SoDu)));

                        }

                    }



                }
            }




            adapter.notifyDataSetChanged();
        }
        else {


            arrayList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Tháng Hiện Tại Không Có Dữ Liệu Để Thống Kê", Toast.LENGTH_SHORT).show();
        }



        }


    public void SetNgayGioHienTai()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String xuLyThangNam = simpleDateFormat.format(calendar.getTime()).substring(3,10);
        txtNgay.setText(xuLyThangNam);

    }
    }




