package com.example.doanandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doanandroid.Models.ThongKeTienVonModel;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class ThongKeTienVonAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThongKeTienVonModel>arrayList;

    public ThongKeTienVonAdapter(Context context, ArrayList<ThongKeTienVonModel> arrayList) {
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
            convertView = inflater.inflate(R.layout.item_thong_ke_tien_von, null, true);
            holder.txtThang =(TextView) convertView.findViewById( R.id.txtThang );
            holder.txtThuNhap = (TextView)convertView.findViewById( R.id.txtThuNhapThang );
            holder.txtChiTieu = (TextView)convertView.findViewById( R.id.txtChiTieuThang );
            holder.txtSoDu = (TextView)convertView.findViewById( R.id.txtSoDuThang );


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }



             holder.txtThang.setText( arrayList.get(position).getThang() );
            holder.txtThuNhap.setText( arrayList.get(position).getThunhap() );
            holder.txtChiTieu.setText( arrayList.get(position).getChitieu() );
            holder.txtSoDu.setText( arrayList.get(position).getSodu() );







        return convertView;
    }
    public class ViewHolder{

        TextView txtThang,txtThuNhap,txtChiTieu,txtSoDu;

    }
}
