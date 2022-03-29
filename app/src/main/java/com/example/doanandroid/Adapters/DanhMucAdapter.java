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

import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class DanhMucAdapter extends BaseAdapter {
    Context context;
    ArrayList<TypeModel> arrayList;

    public DanhMucAdapter(Context context, ArrayList<TypeModel> arrayList) {
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
            convertView = inflater.inflate(R.layout.item_gridviewdanhmuc, null, true);
            holder.img =(ImageView)convertView.findViewById( R.id.imgIcon );
            holder.text = (TextView)convertView.findViewById( R.id.txtChuDe );

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        // Chuyen byte[] -> Bitmap
        byte[] hinh = arrayList.get(position).getImgView();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.img.setImageBitmap(bitmap);

        int red = Integer.parseInt(arrayList.get(position).getRed());
        int green = Integer.parseInt(arrayList.get(position).getGreen());
        int blue = Integer.parseInt(arrayList.get(position).getBlue());
        holder.img.setBackgroundColor(Color.rgb(red,green,blue));
        holder.text.setText( arrayList.get(position).getText() );

        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView text;
    }
}
