package com.example.doanandroid.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.doanandroid.Models.ImageIconModel;
import com.example.doanandroid.Models.TypeModel;

import com.example.doanandroid.R;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<ImageIconModel> arrayList;

    public GridViewAdapter(Context context, ArrayList<ImageIconModel> arrayList) {
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
            holder = new GridViewAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_gridview, null, true);
            holder.img= (ImageView) convertView.findViewById( R.id.imgBtn ) ;
            convertView.setTag(holder);
        }
        else {
            holder = (GridViewAdapter.ViewHolder)convertView.getTag();
        }

        ImageIconModel hinhAnh = arrayList.get(position);
//        // Chuyen Byte[] -> Bitmap
//        byte[] anhHinh = hinhAnh.getImg();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(anhHinh, 0, anhHinh.length);
        holder.img.setImageResource( arrayList.get(position).getImg() );
        int red = Integer.parseInt(hinhAnh.getRed());
        int green = Integer.parseInt(hinhAnh.getGreen());
        int blue = Integer.parseInt(hinhAnh.getBlue());
        holder.img.setBackgroundColor(Color.rgb(red,green,blue));

//        holder.img.setImageResource( arrayList.get(position).getImg() );


        return convertView;
    }
    public class ViewHolder{
        ImageView img;
    }
}
