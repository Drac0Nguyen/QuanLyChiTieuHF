package com.example.doanandroid.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.doanandroid.Models.TypeModel;
import com.example.doanandroid.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditTypeActivity extends AppCompatActivity {
    TypeModel type;
    EditText edtEdit;
    ImageView imgEditSwap;
    ImageButton imgButtonEdit;
    GridView gridView;
    GridViewAdapter gridAdapter;
    ArrayList<ImageIconModel> imgIconArray;
    private  int idHinhDuocChonEdit;
    String editRed = "", etGreen = "", etBlue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit );
        Intent intent = getIntent();
        type = (TypeModel)intent.getSerializableExtra( "type" );
        // Lay id cua hinh duoc chon
        idHinhDuocChonEdit = type.getID();
        edtEdit = (EditText)findViewById( R.id.edtEdit );
        imgEditSwap = (ImageView)findViewById( R.id.imgEditSwap );
        imgButtonEdit = (ImageButton)findViewById(R.id.imgBtnEdit);
        edtEdit.setText( type.getText() );
        int red = Integer.parseInt(type.getRed());
        int green = Integer.parseInt(type.getGreen());
        int blue = Integer.parseInt(type.getBlue());
        imgEditSwap.setBackgroundColor(Color.rgb(red,green,blue));
        byte[] hinh = type.getImgView();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        imgEditSwap.setImageBitmap( bitmap );

        gridView = (GridView)findViewById( R.id.gridView ) ;
        imgIconArray = new ArrayList<>();




        gridAdapter = new GridViewAdapter( EditTypeActivity.this,imgIconArray );
        gridView.setAdapter( gridAdapter );
        GetData();

        gridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                imgEditSwap.setImageResource( imgIconArray.get(position).getImg() );

                editRed = imgIconArray.get(position).getRed();
                etGreen = imgIconArray.get(position).getGreen();
                etBlue = imgIconArray.get(position).getBlue();

                int intRed = Integer.parseInt(editRed);
                int intGreen = Integer.parseInt(etGreen);
                int intBlue = Integer.parseInt(etBlue);

                imgEditSwap.setBackgroundColor(Color.rgb(intRed,intGreen,intBlue));







            }
        } );


        imgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyen hinh tu ImageView -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgEditSwap.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();
                String ten = edtEdit.getText().toString().trim();
                if(imgEditSwap == null ||ten.equals("") || editRed.equals(""))
                {
                    Toast.makeText(EditTypeActivity.this, "Vui lòng điền và chọn chủ đề cần chỉnh sửa", Toast.LENGTH_SHORT).show();
                }
                else {


                    MainActivity.database.Update_ChuDe(ten,editRed,etGreen,etBlue,hinhAnh,idHinhDuocChonEdit);
                    Toast.makeText(EditTypeActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (EditTypeActivity.this, UpdateCategoryActivity.class));


                }

            }
        });


    }
    public void GetData() {

        imgIconArray.add(new ImageIconModel(1, "66", "205", "27", R.drawable.salary));
        imgIconArray.add(new ImageIconModel(2, "114", "161", "101", R.drawable.eat));
        imgIconArray.add(new ImageIconModel(3, "76", "175", "80", R.drawable.sport));
        imgIconArray.add(new ImageIconModel(4, "138", "216", "12", R.drawable.drink));
        imgIconArray.add(new ImageIconModel(5, "108", "68", "115", R.drawable.laundry));
        imgIconArray.add(new ImageIconModel(6, "180", "195", "44", R.drawable.clothes));
        imgIconArray.add(new ImageIconModel(7, "202", "124", "5", R.drawable.phone));
        imgIconArray.add(new ImageIconModel(8, "181", "37", "166", R.drawable.elec_water));
        imgIconArray.add(new ImageIconModel(9, "172", "6", "63", R.drawable.educa));
        imgIconArray.add(new ImageIconModel(10, "138", "50", "22", R.drawable.shopping));
        imgIconArray.add(new ImageIconModel(11, "130", "142", "17", R.drawable.vehicle));
        imgIconArray.add(new ImageIconModel(12, "175", "111", "15", R.drawable.entertaiment));
        imgIconArray.add(new ImageIconModel(13, "73", "131", "126", R.drawable.travel));
        imgIconArray.add(new ImageIconModel(14, "151", "53", "22", R.drawable.cotuc));

        gridAdapter.notifyDataSetChanged();


    }

}