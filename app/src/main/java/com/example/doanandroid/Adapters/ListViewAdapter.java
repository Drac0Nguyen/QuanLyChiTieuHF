package com.example.doanandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doanandroid.Controller.EditTypeActivity;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
   Context context;
   ArrayList<TypeModel> arrayList;

    public ListViewAdapter(Context context, ArrayList<TypeModel> arrayList) {
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
            convertView = inflater.inflate(R.layout.item_lv, null, true);
            holder.img =(ImageView)convertView.findViewById( R.id.imgIcon );
            holder.text = (TextView)convertView.findViewById( R.id.text );
            holder.imgButtonEdit=(ImageButton)convertView.findViewById( R.id.edtImgBtn );
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        TypeModel typeModel = arrayList.get(position);
        // Chuyen byte[] -> Bitmap
        byte[] hinh = typeModel.getImgView();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.img.setImageBitmap(bitmap);


        int red = Integer.parseInt(typeModel.getRed());
        int green = Integer.parseInt(typeModel.getGreen());
        int blue = Integer.parseInt(typeModel.getBlue());
        holder.img.setBackgroundColor(Color.rgb(red,green,blue));



            holder.text.setText( arrayList.get(position).getText() );
            holder.imgButtonEdit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditTypeActivity.class);
                    intent.putExtra( "type",arrayList.get(position) );
                    context.startActivity(intent);

                }
            } );




        return convertView;
    }
    private class ViewHolder{
        ImageView img;
        TextView text;
        ImageButton imgButtonEdit;
    }
}
