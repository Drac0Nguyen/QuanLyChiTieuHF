package com.example.doanandroid.Controller;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Adapters.ListViewAdapter;
import com.example.doanandroid.Database.Database;
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.util.ArrayList;

public class UpdateCategoryActivity extends AppCompatActivity {

    public static Database database;
    ListView lv;
    ListViewAdapter adapter;
    ArrayList<TypeModel> arrayList;
    TextView textView;
    ImageView img;
    LinearLayout lnAdd;
    Button btnThuNhap, btnChiTieu;
    int ThuChi = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);





        btnThuNhap = (Button)findViewById(R.id.btnThuNhap);
        btnChiTieu = (Button)findViewById(R.id.btnChiTieu);


        btnThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuChi = 1;
                GetDataThuNhap();
            }
        });
        btnChiTieu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ThuChi = 2;
                GetDataChiTieu();
            }

        });



        lv = (ListView)findViewById( R.id.lvItem );
        img = (ImageView)findViewById( R.id.imgIcon );
        lnAdd = (LinearLayout)findViewById( R.id.lnAdd );
        arrayList = new ArrayList<>();
//        arrayList.add(new TypeModel( R.drawable.cho_1,"Thực phẩm" ));

        adapter = new ListViewAdapter( UpdateCategoryActivity.this,arrayList );
        lv.setAdapter( adapter );

        GetDataThuNhap();
        lv.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder dialog = new AlertDialog.Builder( UpdateCategoryActivity.this );
                dialog.setTitle( "Delete " );
                dialog.setMessage( "Do you want to delete it ?" );
                dialog.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       MainActivity.database.Delete_ChuDe(arrayList.get(position).getID());
                        adapter.notifyDataSetChanged();
                        lv.setAdapter( adapter );
                        GetDataThuNhap();

                        Toast.makeText( UpdateCategoryActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT ).show();
                    }
                } );
                dialog.setPositiveButton( "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                } );
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

                return false;
            }
        } );

        lnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateCategoryActivity.this, AddTypeActivity.class);
                intent.putExtra("theloaithuchi", ThuChi);
                startActivity( intent );
            }
        } );


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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateCategoryActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}