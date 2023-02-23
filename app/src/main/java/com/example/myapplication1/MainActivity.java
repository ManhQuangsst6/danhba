package com.example.myapplication1;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contactList;
    private adt listAdapter;
    private TextView etSearch;
    private ListView listContact;
    private Button btnAdd;
    private Button btnDelete;
    int selectedid=-1;
    ImageView call;
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        if(requestCode==100 && resultCode==100)
        {
            //đặt vào listData
            contactList.add(new Contact(id, name,phone,false ));
            listContact.setAdapter(listAdapter);
//            mysqlitedb.addContact(new Contact(id, "", name,tuoi ));
        }

        else if(requestCode==200 && resultCode==150) {
            contactList.set(selectedid, new Contact(id, name,phone,false ));
//            //cập nhật adapter
            listAdapter.notifyDataSetChanged();
            listContact.setAdapter(listAdapter);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=new ArrayList<>();
        contactList.add(new Contact(1,"dao manh quang","02384242",false));
        contactList.add(new Contact(2,"dao manh quan","02384242",false));
        contactList.add(new Contact(3,"dao manh nam","02384242",false));
        contactList.add(new Contact(4,"dao manh hung","02384242",false));


        listAdapter= new adt(contactList,this);
        listContact=findViewById(R.id.listView);
        btnAdd=findViewById(R.id.btnAdd);
        btnDelete=findViewById(R.id.btnDelete);
        etSearch=findViewById(R.id.etSearch);
        listContact.setAdapter(listAdapter);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            int num=contactList.size();
            ArrayList<Contact> a=new ArrayList<Contact>();
            @Override
            public void onClick(View view) {
                for (Contact item:contactList
                     ) {
                    if(item.isCheck()==true){
                        a.add(item);
                    }
                }
                for (Contact a1:a
                     ) {
                    contactList.remove(a1);
                }
                listAdapter= new adt(contactList,MainActivity.this);
                listContact.setAdapter(listAdapter);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity_Add.class);
                startActivityForResult(intent,100);
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listAdapter.getFilter().filter(s.toString());
                listAdapter.notifyDataSetChanged();
                //lvContact.setAdapter(listUserAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
