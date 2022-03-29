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
import android.widget.TextView;

import com.example.doanandroid.Models.ThongTinChiTieuModel;
import com.example.doanandroid.Models.TypeModel;

import com.example.doanandroid.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class LichSuAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongTinChiTieuModel>arrayList;

    public LichSuAdapter(Context context, ArrayList<ThongTinChiTieuModel> arrayList) {
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
        ViewHolder holder ;
        if(convertView==null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lich_su, null, true);
            holder.imgIconChuDe =(ImageView)convertView.findViewById( R.id.imgIconChuDe );
            holder.txtChuDe = (TextView)convertView.findViewById( R.id.txtChuDe );
            holder.txtSoTien=(TextView)convertView.findViewById( R.id.txtSoTien );

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        // Chuyen tu byte[] sang Bitmap

        ThongTinChiTieuModel model = arrayList.get(position);
        byte[] hinh = arrayList.get(position).getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.imgIconChuDe.setImageBitmap(bitmap);

        int red  = Integer.parseInt(arrayList.get(position).getRed());
        int green  = Integer.parseInt(arrayList.get(position).getGreen());
        int blue  = Integer.parseInt(arrayList.get(position).getBlue());
        holder.imgIconChuDe.setBackgroundColor(Color.rgb(red,green,blue));
        holder.txtChuDe.setText( arrayList.get(position).getTenChuDe() );


//        holder.txtSoTien.setText(   String.valueOf(arrayList.get(position).getGia()));
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###,###,###", symbols);

        String theLoai = arrayList.get(position).getTenTheLoai();

        if(theLoai.equals("Thu Nhập"))
        {
             String  gia = String.valueOf(df.format(arrayList.get(position).getGia()));


            holder.txtSoTien.setText("+" + gia);
        }
        else
        {
            String gia2 = String.valueOf(df.format(arrayList.get(position).getGia()));
            holder.txtSoTien.setText("-" + gia2);
        }







        return convertView;
    }
    private class ViewHolder{
        ImageView imgIconChuDe;
        TextView txtChuDe,txtSoTien;
    }
}
