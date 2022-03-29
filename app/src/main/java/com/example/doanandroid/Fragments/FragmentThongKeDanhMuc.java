package com.example.doanandroid.Fragments;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doanandroid.Adapters.ThongKePhanLoaiAdapter;
import com.example.doanandroid.Controller.MainActivity;
import com.example.doanandroid.Models.ThongKePhanLoaiModel;
import com.example.doanandroid.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class FragmentThongKeDanhMuc extends Fragment implements OnChartValueSelectedListener {
    View view;
    private PieChart mChart;
    ListView lvThongKePhanLoai;
    ArrayList<ThongKePhanLoaiModel> arrayList;
    ThongKePhanLoaiAdapter adapter;
    Button btnThuNhap, btnChiTieu;
    ImageView imgChonNgay;
    int year, month, dayOfMonth;
    DatePickerDialog datePicker;
    TextView txtNgay;
    String ngayDuocchon = "";
    int checkButtonClick = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_thongke_danhmuc, container, false);
        lvThongKePhanLoai = (ListView)view.findViewById(R.id.lvThongKePhanLoai);
        btnThuNhap = (Button)view.findViewById(R.id.btnThongKeThuNhap);
        btnChiTieu = (Button)view.findViewById(R.id.btnThongKeChiTieu);
        imgChonNgay = (ImageView) view.findViewById(R.id.imgChonNgay);
        txtNgay = (TextView) view.findViewById(R.id.txtNgay);


        mChart = (PieChart) view.findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("Thống Kê Danh Mục");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);
        mChart.setNoDataText("Chưa có dữ liệu để thống kê");

        Paint paint = mChart.getPaint(PieChart.PAINT_INFO);
        paint.setTextSize(80);
        SetNgayGioHienTai();
        checkButtonClick = 1;
         ngayDuocchon  = txtNgay.getText().toString().trim();
        addDataSetThuNhap(mChart,ngayDuocchon);
        mChart.setOnChartValueSelectedListener(this);
        arrayList = new ArrayList<>();
    //    arrayList.add(new ThongKePhanLoaiModel(R.drawable.ic_calendar,"Gifts","15","999999"));

        adapter = new ThongKePhanLoaiAdapter(getActivity(), arrayList);
        lvThongKePhanLoai.setAdapter(adapter);
        AddDaTaThuNhapToArrayList(ngayDuocchon);

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
            btnThuNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkButtonClick = 1;
                    ngayDuocchon = txtNgay.getText().toString();
                    AddDaTaThuNhapToArrayList(ngayDuocchon);
                    addDataSetThuNhap(mChart,ngayDuocchon);
                    mChart.notifyDataSetChanged();

                }
            });
            btnChiTieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkButtonClick = 2;
                    ngayDuocchon = txtNgay.getText().toString();
                    AddDaTaChiTieuToArrayList(ngayDuocchon);
                    addDataSetChiTieu(mChart,ngayDuocchon);

                }
            });

        return view;
    }
    private void addDataSetThuNhap(PieChart pieChart, String thangDuocChon) {
        // Them dau % dang sau value, con lenh o duoi kia nua


        // yEntrys = Số LIỆU % CỦA TỪNG MÀU
        // xEntrys = Tiêu đề của mỗi tháng


        Cursor checkNull = MainActivity.database.QueryDataResult("SELECT Count(*) FROM ThuChiTheoNgay WHERE TheLoai = 1 and ThangThuNhap = '" + ngayDuocchon + "'");
        checkNull.moveToFirst();
        int icount = checkNull.getInt(0);
        Cursor cursor = MainActivity.database.QueryDataResult("SELECT DISTINCT  a.TenDanhMuc,  Sum(a.gia), a.Red, a.Green, a.Blue FROM ThuChiTheoNgay as a,  TheLoaiThuChi as b WHERE  a.TheLoai = b.ID and a.TheLoai = 1 and a.ThangThuNhap = '" + ngayDuocchon + "'  Group by a.DanhMuc_ID");

        if (icount > 0) {
            pieChart.setUsePercentValues(true);
            ArrayList<PieEntry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();
            ArrayList<PieDataSet> data = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            String tenChuDe = "";
            int red = 0;
            int blue = 0;
            int green = 0;
            float testTien = 0;
            while (cursor.moveToNext()) {
                tenChuDe = cursor.getString(0);
                float soluong = cursor.getFloat(1);
                red = Integer.parseInt(cursor.getString(2));
                green = Integer.parseInt(cursor.getString(3));
                blue = Integer.parseInt(cursor.getString(4));
                Cursor data2 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 1 AND ThangThuNhap = '" + ngayDuocchon + "' ");
                while (data2.moveToNext()) {
                    float tongTien = data2.getFloat(0);
                    float tienDaTinhPhanTram = (soluong / tongTien) * 100;
                    String phantram = String.valueOf(tienDaTinhPhanTram);
                    String sotien = String.valueOf(soluong);
                    int[] getRed = {red};
                    int[] getGreen = {green};
                    int[] getBlue = {blue};
                    String[] xData = {tenChuDe};
                    float[] yData = {tienDaTinhPhanTram};
                    for (int i = 0; i < xData.length; i++) {
                        yEntrys.add(new PieEntry(yData[i], xData[i]));
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        for (int f = 0; f < getRed.length; f++) {
                            colors.add(Color.rgb(getRed[f], getGreen[f], getBlue[f]));
                            pieDataSet.setColors(colors);
                            PieData pieData = new PieData(pieDataSet);
                            // Them dau % dang sau value
                            pieData.setValueFormatter(new PercentFormatter(pieChart));

                            pieChart.setData(pieData);
                            pieChart.invalidate();
                            pieChart.getDescription().setEnabled(false);
                        }

                    }

                }


            }


        } else {
            // Bat Dau
            pieChart.setUsePercentValues(true);
            ArrayList<PieEntry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();
            ArrayList<PieDataSet> data = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();

            String[] xData = {""};
            float[] yData = {0};
            for (int i = 0; i < xData.length; i++) {
                yEntrys.add(new PieEntry(yData[i], xData[i]));
                PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                colors.add(Color.rgb(232,202,132));
                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                // Them dau % dang sau value
                pieData.setValueFormatter(new PercentFormatter(pieChart));

                pieChart.setData(null);
                pieChart.invalidate();
                pieChart.getDescription().setEnabled(false);
            }

        }
        // Ket Thuc
    }












    private void addDataSetChiTieu(PieChart pieChart, String thangDuocChon) {



        Cursor checkNull = MainActivity.database.QueryDataResult("SELECT Count(*) FROM ThuChiTheoNgay WHERE TheLoai = 2 and ThangThuNhap = '" + ngayDuocchon + "'");
        checkNull.moveToFirst();
        int icount = checkNull.getInt(0);
        if(icount > 0)

        {
            // Them dau % dang sau value, con lenh o duoi kia nua
            pieChart.setUsePercentValues(true);
            ArrayList<PieEntry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();
            ArrayList<PieDataSet> data = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();
            // yEntrys = Số LIỆU % CỦA TỪNG MÀU
            // xEntrys = Tiêu đề của mỗi tháng
            String tenChuDe = "";
            int red = 0;
            int blue = 0;
            int green = 0;
            float testTien = 0;
            Cursor cursor = MainActivity.database.QueryDataResult("SELECT DISTINCT  a.TenDanhMuc,  Sum(a.gia), a.Red, a.Green, a.Blue FROM ThuChiTheoNgay as a,  TheLoaiThuChi as b WHERE  a.TheLoai = b.ID and a.TheLoai = 2 and a.ThangThuNhap = '"+ngayDuocchon+"'  Group by a.DanhMuc_ID");
            while (cursor.moveToNext()) {
                tenChuDe = cursor.getString(0);
                float soluong = cursor.getFloat(1);
                red = Integer.parseInt(cursor.getString(2));
                green = Integer.parseInt(cursor.getString(3));
                blue = Integer.parseInt(cursor.getString(4));
                Cursor data2 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 2 AND ThangThuNhap = '"+ngayDuocchon+"'");
                while (data2.moveToNext())
                {
                    float tongTien = data2.getFloat(0);
                    float tienDaTinhPhanTram =(soluong/tongTien)*100;
                    String phantram = String.valueOf(tienDaTinhPhanTram);
                    String sotien = String.valueOf(soluong);
                    int[] getRed = {red};
                    int[] getGreen = {green};
                    int[] getBlue = {blue};
                    String[] xData = {tenChuDe};
                    float[] yData = {tienDaTinhPhanTram};
                    for (int i = 0; i < xData.length; i++) {
                        yEntrys.add(new PieEntry(yData[i], xData[i] ));
                    }
                    PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
                    pieDataSet.setSliceSpace(2);
                    pieDataSet.setValueTextSize(12);
                    for(int f = 0;f<getRed.length; f++)
                    {
                        colors.add( Color.rgb(getRed[f],getGreen[f],getBlue[f]));
                        pieDataSet.setColors(colors);
                        PieData pieData = new PieData(pieDataSet);
                        // Them dau % dang sau value
                        pieData.setValueFormatter(new PercentFormatter(pieChart));
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.getDescription().setEnabled(false);
                    }
                }
            }


        }
        else
        {

            // Bat Dau
            pieChart.setUsePercentValues(true);
            ArrayList<PieEntry> yEntrys = new ArrayList<>();
            ArrayList<String> xEntrys = new ArrayList<>();
            ArrayList<PieDataSet> data = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<>();

            String[] xData = {""};
            float[] yData = {0};
            for (int i = 0; i < xData.length; i++) {
                yEntrys.add(new PieEntry(yData[i], xData[i]));
                PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                pieDataSet.setColors(colors);
                PieData pieData = new PieData(pieDataSet);
                // Them dau % dang sau value
                pieData.setValueFormatter(new PercentFormatter(pieChart));

                pieChart.setData(null);
                pieChart.invalidate();
                pieChart.getDescription().setEnabled(false);
            }

        }






    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    public void AddDaTaThuNhapToArrayList(String thangDuocChon)
    {

        String tenChuDe = "";
        String red,green,blue = "";
        byte[] hinh = null;

        Cursor  cursor = MainActivity.database.QueryDataResult("SELECT DISTINCT  a.TenDanhMuc,  Sum(a.gia), a.Red, a.Green, a.Blue, a.Hinh FROM ThuChiTheoNgay as a,  TheLoaiThuChi as b WHERE  a.TheLoai = b.ID and a.TheLoai = 1 and a.ThangThuNhap = '"+ngayDuocchon+"'  Group by a.DanhMuc_ID");
        arrayList.clear();
        while (cursor.moveToNext()) {

            tenChuDe = cursor.getString(0);
            float soluong = cursor.getFloat(1);
            red = cursor.getString(2);
            green = cursor.getString(3);
            blue = cursor.getString(4);
            hinh = cursor.getBlob(5);


            Cursor data2 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 1 AND ThangThuNhap = '"+ngayDuocchon+"'");
            while (data2.moveToNext()) {
                float tongTien = data2.getFloat(0);

                DecimalFormat df = new DecimalFormat("0.00");
                float tienDaTinhPhanTram = (soluong / tongTien) * 100;

                String phanTramHienThi = String.valueOf(df.format(tienDaTinhPhanTram));


                DecimalFormat df2 = new DecimalFormat("0");

                int phanTramProgressBar = Integer.parseInt(df2.format(tienDaTinhPhanTram));

                arrayList.add(new ThongKePhanLoaiModel(hinh, tenChuDe, phanTramHienThi, soluong,phanTramProgressBar ,red,green,blue));


            }
        }
        adapter.notifyDataSetChanged();

        }
    public void AddDaTaChiTieuToArrayList(String thangDuocChon)
    {

        String tenChuDe = "";
        String red,green,blue = "";
        byte[] hinh = null;


        float testTien = 0;
        Cursor  cursor = MainActivity.database.QueryDataResult("SELECT DISTINCT  a.TenDanhMuc,  Sum(a.gia), a.Red, a.Green, a.Blue, a.Hinh FROM ThuChiTheoNgay as a,  TheLoaiThuChi as b WHERE  a.TheLoai = b.ID and a.TheLoai = 2 and a.ThangThuNhap = '"+ngayDuocchon+"'  Group by a.DanhMuc_ID");
        arrayList.clear();
        while (cursor.moveToNext()) {

            tenChuDe = cursor.getString(0);
            float soluong = cursor.getFloat(1);
            red = cursor.getString(2);
            green = cursor.getString(3);
            blue = cursor.getString(4);
            hinh = cursor.getBlob(5);

            Cursor data2 = MainActivity.database.QueryDataResult("SELECT Sum(GIA) FROM ThuChiTheoNgay WHERE TheLoai = 2 AND ThangThuNhap = '"+ngayDuocchon+"'");
            while (data2.moveToNext()) {
                float tongTien = data2.getFloat(0);

                DecimalFormat df = new DecimalFormat("0.00");
                float tienDaTinhPhanTram = (soluong / tongTien) * 100;

                String phanTramHienThi = String.valueOf(df.format(tienDaTinhPhanTram));


                DecimalFormat df2 = new DecimalFormat("0");

                int phanTramProgressBar = Integer.parseInt(df2.format(tienDaTinhPhanTram));



                arrayList.add(new ThongKePhanLoaiModel(hinh, tenChuDe, phanTramHienThi, soluong,phanTramProgressBar ,red,green,blue));


            }

        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected() {

    }
    public void SetNgayGioHienTai()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String xuLyThangNam = simpleDateFormat.format(calendar.getTime()).substring(3,10);
        txtNgay.setText(xuLyThangNam);

    }

}
