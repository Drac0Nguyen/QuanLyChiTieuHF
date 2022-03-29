package com.example.doanandroid.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void QueryNonDataResult(String sql)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
    }
    public Cursor QueryDataResult(String sql)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
     return    sqLiteDatabase.rawQuery(sql,null);

    }
    public void KhoiTaoBang()
    {
        String khoiTao = "CREATE TABLE IF NOT EXISTS";
        String iD = "ID INTEGER PRIMARY KEY AUTOINCREMENT";
        // Bang 1
        String bangThuChi = "TheLoaiThuChi";
        // Bang 2
        String bangChuDe = "ChuDe";
        // Bang 3
        String thuChiTheoNgay = "ThuChiTheoNgay";
        // Bang 4
        String displayName = "DisplayName";



        // Bang 1
        QueryNonDataResult(""+khoiTao+" "+bangThuChi+"(ID INTEGER PRIMARY KEY, TenTheLoai NVARCHAR(15))");

        // Bang 2
        QueryNonDataResult(""+khoiTao+" "+bangChuDe+"("+iD+"," +
                        " TenChuDe NVARCHAR(50), " +
                        "Red VARCHAR(3), " +
                        "Green VARCHAR(3), " +
                        "Blue VARCHAR(3), Hinh BLOB, TheLoai INTEGER, " +
                        "FOREIGN KEY(TheLoai) REFERENCES TheLoaiThuChi(ID))");
        // Bang 3
        QueryNonDataResult(""+khoiTao+" "+thuChiTheoNgay+"("+iD+" ,MoTa NVARCHAR(50), Gia FLOAT ,NgayThuNhap VARCHAR(20), ThangThuNhap VARCHAR(20), DanhMuc_ID INTEGER, TenDanhMuc NVARCHAR(30) ,Red VARCHAR(3), Green VARCHAR(3), Blue VARCHAR(3),  Hinh BLOB, TheLoai INTEGER, FOREIGN KEY(TheLoai) REFERENCES TheLoaiThuChi(ID))");

        QueryNonDataResult(""+khoiTao+" "+displayName+"("+iD+", TenHienThi NVARCHAR(20))");
    }
    // Insert TheLoai
    public void Insert_TheLoai(int id,String ten)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO TheLoaiThuChi VALUES(?,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindDouble(1, id);
        sqLiteStatement.bindString(2,ten);

        sqLiteStatement.executeInsert();
    }

    // Insert ChuDe
    public void Insert_ChuDe(String ten, String red, String green, String blue, byte[] hinh, int theloai)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO ChuDe VALUES(null,?,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1, ten);
        sqLiteStatement.bindString(2, red);
        sqLiteStatement.bindString(3, green);
        sqLiteStatement.bindString(4, blue);
        sqLiteStatement.bindBlob(5, hinh);
        sqLiteStatement.bindDouble(6,theloai);

        sqLiteStatement.executeInsert();
    }
    // Update ChuDe
    public void Update_ChuDe(String ten, String red, String green, String blue ,byte[] hinh, int id)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "UPDATE ChuDe SET TenChuDe = ?, Red = ? , Green = ?, Blue = ? ,Hinh = ? WHERE ID = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.bindString(1,ten);
        sqLiteStatement.bindString(2,red);
        sqLiteStatement.bindString(3,green);
        sqLiteStatement.bindString(4,blue);
        sqLiteStatement.bindBlob(5,hinh);
        sqLiteStatement.bindDouble(6,id);
        sqLiteStatement.executeUpdateDelete();
    }

    // Delete ChuDe
    public void Delete_ChuDe(int id)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "DELETE FROM ChuDe WHERE ID = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();


        sqLiteStatement.bindDouble(1, id);


        sqLiteStatement.executeUpdateDelete();
    }


    // Insert ThuChiTheoNgay
    public void Insert_ThuChiTheoNgay(String mota, float gia, String ngaythunhap, String thangthunhap,  int chude_id, String tenchude, String red, String green, String blue, byte[] hinh ,int theloai_id)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO ThuChiTheoNgay VALUES(null,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();


        sqLiteStatement.bindString(1, mota);
        sqLiteStatement.bindDouble(2, gia);
        sqLiteStatement.bindString(3, ngaythunhap);
        sqLiteStatement.bindString(4, thangthunhap);
        sqLiteStatement.bindDouble(5, chude_id);
        sqLiteStatement.bindString(6, tenchude);
        sqLiteStatement.bindString(7, red);
        sqLiteStatement.bindString(8, green);
        sqLiteStatement.bindString(9, blue);
        sqLiteStatement.bindBlob(10, hinh);
        sqLiteStatement.bindDouble(11, theloai_id);

        sqLiteStatement.executeInsert();
    }
    // Update LichSuThuChi
    public void Update_LichSuThuChi(String mota, float gia, String ngaythunhap, String thangthunhap,  int danhMuc_id, String tenchude, String red, String green, String blue, byte[] hinh ,int theloai_id, int ID_ThuChi)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "UPDATE ThuChiTheoNgay SET MoTa = ?, Gia = ?, NgayThuNhap = ?, ThangThuNhap = ?, DanhMuc_ID = ?, TenDanhMuc = ?, Red = ?, Green = ?, Blue = ?, Hinh = ?, TheLoai = ? WHERE ID = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();


        sqLiteStatement.bindString(1, mota);
        sqLiteStatement.bindDouble(2, gia);
        sqLiteStatement.bindString(3, ngaythunhap);
        sqLiteStatement.bindString(4, thangthunhap);
        sqLiteStatement.bindDouble(5, danhMuc_id);
        sqLiteStatement.bindString(6, tenchude);
        sqLiteStatement.bindString(7, red);
        sqLiteStatement.bindString(8, green);
        sqLiteStatement.bindString(9, blue);
        sqLiteStatement.bindBlob(10, hinh);
        sqLiteStatement.bindDouble(11, theloai_id);
        sqLiteStatement.bindDouble(12, ID_ThuChi);

        sqLiteStatement.executeUpdateDelete();
    }
    // Update LichSuThuChi
    public void Delete_LichSuThuChi(int id)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "DELETE FROM ThuChiTheoNgay WHERE ID = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindDouble(1, id);
        sqLiteStatement.executeUpdateDelete();
    }


    // Update Tên Hiển Thị
    public void Update_DisplayName(String tenHienThi)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "UPDATE  DisplayName SET TenHienThi = ?";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1, tenHienThi);
        sqLiteStatement.executeUpdateDelete();
    }


    // Insert Default DisplayName
    public void Insert_DisplayName(String ten)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "INSERT INTO DisplayName VALUES(null,?)";
        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1,ten);

        sqLiteStatement.executeInsert();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
