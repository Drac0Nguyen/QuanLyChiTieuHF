package com.example.doanandroid.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Adapters.ChooseTopicAdapter;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class ChooseTopicActivity extends AppCompatActivity {

    ListView lvChooseTopic;
    ChooseTopicAdapter adapter;
    ArrayList<TypeModel> arrayList;
    private  Button btnThuNhap, btnChiTieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_topic);
        lvChooseTopic = (ListView)findViewById(R.id.lvChooseTopic);
        btnThuNhap = (Button)findViewById(R.id.btnThuNhapTopic);
        btnChiTieu = (Button)findViewById(R.id.btnChiTieuTopic);
        arrayList = new ArrayList<>();
        adapter = new ChooseTopicAdapter(this, arrayList);
        lvChooseTopic.setAdapter(adapter);
        GetDataThuNhap();

        btnThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataThuNhap();
            }
        });
        btnChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataChiTieu();
            }
        });

        lvChooseTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ChooseTopicActivity.this, arrayList.get(position).getText() +"", Toast.LENGTH_SHORT ).show();



                String ten =    arrayList.get(position).getText();
                byte[] mang = arrayList.get(position).getImgView();
               String red = arrayList.get(position).getRed();
                String green = arrayList.get(position).getGreen();
                String blue = arrayList.get(position).getBlue();
               Intent intent = new Intent(ChooseTopicActivity.this,AddThuChiActivity.class);
                intent.putExtra("dulieu", ten);
                intent.putExtra("red",red);
                intent.putExtra("green",green);
                intent.putExtra("blue",blue);
                intent.putExtra("mangHinh", mang);
                startActivity(intent);

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