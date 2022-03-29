package com.example.doanandroid.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanandroid.Adapters.GridViewAdapter;
import com.example.doanandroid.Models.ImageIconModel;
import com.example.doanandroid.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddTypeActivity extends AppCompatActivity {

    EditText edtAdd;
    GridView gridView;
    GridViewAdapter gridAdapter;
    ImageView imgSwap;
    ImageButton imgBtnAdd;
    ArrayList<ImageIconModel> imgIconArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_type );
        edtAdd = (EditText)findViewById( R.id.edtAdd );
        imgBtnAdd =(ImageButton) findViewById( R.id.imgBtnAddType );
        imgSwap = (ImageView)findViewById( R.id.imgSwap );
        gridView = (GridView)findViewById( R.id.gridView ) ;
        // Doc xem nguoi dung cho chu de Thu Nhap hay Chi Tieu
        Intent intent = getIntent();
        int getThuChi = intent.getIntExtra("theloaithuchi", 1);


        imgIconArray = new ArrayList<>();
//        imgIconArray.add(new ImageIconModel( R.drawable.ic_add ));
        gridAdapter = new GridViewAdapter( AddTypeActivity.this,imgIconArray );
        gridView.setAdapter( gridAdapter );
        GetData();

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                 byte[] hinhConvert =  imgIconArray.get(position).getImg();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(hinhConvert,0,hinhConvert.length);
//                imgSwap.setImageBitmap( bitmap );
                imgSwap.setImageResource( imgIconArray.get(position).getImg() );
               // imgSwap.setImageDrawable(imgIconArray.get(position).getImg());
                int red = Integer.parseInt(imgIconArray.get(position).getRed());
                int green = Integer.parseInt(imgIconArray.get(position).getGreen());
                int blue = Integer.parseInt(imgIconArray.get(position).getBlue());

                 imgSwap.setBackgroundColor(Color.rgb(red,green,blue));
                //Add Type
                imgBtnAdd.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Chuyen tu ImageView -> Byte[]
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgSwap.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                        byte[] hinhAnh = byteArray.toByteArray();

                        String ten = edtAdd.getText().toString().trim();
                        String redString = String.valueOf(red);
                        String greenString = String.valueOf(green);
                        String blueString = String.valueOf(blue);

                        if( ten.equals("")  || hinhAnh == null || redString.equals("")  )
                        {
                            Toast.makeText(AddTypeActivity.this,"Vui lòng chọn hình và nhập tên cho chủ đề", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // 1: Thu Nhap
                            // 2: Chi Tieu
                            MainActivity.database.Insert_ChuDe(ten,redString,greenString,blueString,hinhAnh,getThuChi);
                            Toast.makeText(AddTypeActivity.this,"Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddTypeActivity.this, UpdateCategoryActivity.class));

                        }








                    }
                } );
            }
        } );


    }
    public void GetData()
    {
       imgIconArray.add(new ImageIconModel(1,"66","205","27",R.drawable.salary ));
        imgIconArray.add(new ImageIconModel(2,"114","161","101",R.drawable.eat ));
        imgIconArray.add(new ImageIconModel(3,"76","175","80",R.drawable.sport ));
        imgIconArray.add(new ImageIconModel(4,"138","216","12",R.drawable.drink ));
        imgIconArray.add(new ImageIconModel(5,"108","68","115",R.drawable.laundry ));
        imgIconArray.add(new ImageIconModel(6,"180","195","44",R.drawable.clothes ));
        imgIconArray.add(new ImageIconModel(7,"202","124","5",R.drawable.phone ));
        imgIconArray.add(new ImageIconModel(8,"181","37","166",R.drawable.elec_water ));
        imgIconArray.add(new ImageIconModel(9,"172","6","63",R.drawable.educa ));
        imgIconArray.add(new ImageIconModel(10,"138","50","22",R.drawable.shopping ));
        imgIconArray.add(new ImageIconModel(11,"130","142","17",R.drawable.vehicle ));
        imgIconArray.add(new ImageIconModel(12,"175","111","15",R.drawable.entertaiment ));
        imgIconArray.add(new ImageIconModel(13,"73","131","126",R.drawable.travel ));
        imgIconArray.add(new ImageIconModel(14,"151","53","22",R.drawable.cotuc ));
    }
}