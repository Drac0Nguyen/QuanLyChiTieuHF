package com.example.doanandroid.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.doanandroid.Models.ThongKePhanLoaiModel;
import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ThongKePhanLoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongKePhanLoaiModel> arrayList;

    public ThongKePhanLoaiAdapter(Context context, ArrayList<ThongKePhanLoaiModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_thong_ke_phan_loai, null, true);
            holder.imgChuDeThongKe =(ImageView)convertView.findViewById( R.id.imgChuDeThongKe );
            holder.txtChuDeThongKe = (TextView)convertView.findViewById( R.id.txtChuDeThongKe );
            holder.txtPhanTram = (TextView)convertView.findViewById( R.id.txtPhanTram );
            holder.txtSoTienThongKe = (TextView)convertView.findViewById( R.id.txtSoTienThongKe );
            holder.progressBar = (ProgressBar)convertView.findViewById( R.id.progressBar );

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        // Chuye tu bytep[] to Bitmap

        ThongKePhanLoaiModel thongKe = arrayList.get(position);
        byte[] hinhAnh = thongKe.getImgChuDe();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.imgChuDeThongKe.setImageBitmap(bitmap);

        int red = Integer.parseInt(thongKe.getRed());
        int green = Integer.parseInt(thongKe.getGreen());
        int blue = Integer.parseInt(thongKe.getBlue());
        holder.imgChuDeThongKe.setBackgroundColor(Color.rgb(red,green,blue));


        holder.txtChuDeThongKe.setText( thongKe.getTenDanhMuc() );
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###,###,###,###",symbols);

       String tienThongKe =  String.valueOf(df.format(thongKe.getSoTien()));
        holder.txtSoTienThongKe.setText(tienThongKe );



        holder.txtPhanTram.setText( thongKe.getPhanTramHienThi() + "%");


        int soPhanTram = thongKe.getPhanTramProgressBar();
      //  int phanTram = Integer.parseInt( soPhanTram );
        holder.progressBar.setProgress( soPhanTram );

        return convertView;
    }
    public class ViewHolder{
        ImageView imgChuDeThongKe;
        TextView txtChuDeThongKe,txtPhanTram,txtSoTienThongKe;
        ProgressBar progressBar;
    }
}
