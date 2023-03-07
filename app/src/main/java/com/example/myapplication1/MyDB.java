package com.example.myapplication1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {
    public static final String TableName = "ContactTable";
    //tên các cột trong bảng
    public static final String Id = "Id";
    public static final String Name = "Fullname";
    public static final String IsCheck = "IsCheck";
    public static final String SDT = "SDT";

    private Context context;
    public MyDB(Context context, String name,
                SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo câu sql để tạo bảng TableContact
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer Primary key  , "
                + Name + " Text,"
                + SDT + " Text,"
                + IsCheck + " Integer)";
        //chạy câu truy vấn SQL để tạo bảng
        db.execSQL(sqlCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //xóa bảng TableContact đã có
        db.execSQL("Drop table if exists " + TableName);
        //tạo lại
        onCreate(db);
    }
    //hàm thêm một contact vào bảng TableContact
    public void addContact(Contact user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, user.getId());
        value.put(Name, user.getName());
        value.put(SDT, user.getSdt());
        value.put(IsCheck, user.isCheck());
        long num=db.insert(TableName,null, value);
        if(num==1) Toast.makeText(context, "Them Thanh cong", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Them that bai", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void updateContact(int id, Contact user)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, user.getId());
        value.put(Name, user.getName());
        value.put(SDT, user.getSdt());
        value.put(IsCheck, user.isCheck());
        long num= db.update(TableName, value,Id + "=?",
                new String[]{String.valueOf(id)});
        if(num==1) Toast.makeText(context, "Sua Thanh cong", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Sua that bai", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void deleteContact(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete From " + TableName + " Where ID=" + id;
        //db.delete(TblName, ID + "=?",new String[]{String.valueOf(id)});
        db.execSQL(sql);
        db.close();
    }
    //lấy tất cả các dòng của bảng TableContact trả về dạng ArrayList
    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> list = new ArrayList<>();
        //câu truy vấn
        String sql = "Select * from " + TableName;
        //lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();
        //chạy câu truy vấn trả về dạng Cursor
        Cursor cursor = db.rawQuery(sql,null);
        //tạo ArrayList<Contact> để trả về;
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                Contact user = new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3));
                list.add(user);
            }
        return list;
    }
}
