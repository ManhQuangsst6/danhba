package com.example.myapplication1;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contactList;
    private adt listAdapter;
    private TextView etSearch;
    private ListView listContact;
    private Button btnAdd;
    private Button btnDelete;
    int selectedid=-1;
    private MyDB myDB;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.sort_user,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.menuSortGiam:
                Collections.sort(contactList,new LastNameComparator());
                listAdapter= new adt(contactList,this);
                listContact.setAdapter(listAdapter);
                //sap xếp theo tên
                break;
            case R.id.menuSortTang:
                //sắp xếp theo tuoi
                Collections.sort(contactList,new LastNameComparator());
                Collections.reverse(contactList);
                listAdapter= new adt(contactList,this);
                listContact.setAdapter(listAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class LastNameComparator implements Comparator<Contact> {
        @Override
        public int compare(Contact name1, Contact name2) {
            String[] parts1 = name1.getName().split(" ");
            String[] parts2 = name2.getName().split(" ");

            String lastName1 = parts1[parts1.length - 1];
            String lastName2 = parts2[parts2.length - 1];

            return lastName1.compareTo(lastName2);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.num_userSameNam:
                ArrayList<Contact> userCungTen = new ArrayList<>();
                String name = contactList.get(selectedid).getName().trim();
                for (Contact ite : contactList
                ) {
                    if (ite.getName().trim().equals(name) )
                        userCungTen.add(ite);
                }
                listContact.setAdapter(listAdapter);
                int num=userCungTen.size()-1;
                Toast.makeText(this, "có "+num+" người dùng trùng tên", Toast.LENGTH_SHORT).show();

                break;
            case R.id.menuEdit:
                Intent intent = new Intent(MainActivity.this,
                        MainActivity_Add.class);
                Contact c=contactList.get(selectedid);
                Bundle bundle = new Bundle();
                bundle.putInt("id",c.getId());
                bundle.putString("name",c.getName());
                bundle.putString("phone",c.getSdt());
                bundle.putInt("ischeck",c.isCheck());

                intent.putExtras(bundle);

                startActivityForResult(intent, 200);
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        if(data!=null) {
            Bundle bundle = data.getExtras();
            int id = bundle.getInt("id");
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            int ischeck = bundle.getInt("ischeck");

            if(requestCode==100 && resultCode==100)
            {
                //đặt vào listData
                myDB.addContact(new Contact(id,name,phone,ischeck));
                contactList=myDB.getAllContact();
                listAdapter= new adt(contactList,this);
                //  contactList.add(new Contact(id,name,tuoi,"" ));
                listContact.setAdapter(listAdapter);
//              mysqlitedb.addContact(new Contact(id, "", name,tuoi ));
                }

            else if(requestCode==200 && resultCode==150) {
            //contactList.set(selectedid, new Contact(id, name,tuoi,"" ));
                myDB.updateContact(contactList.get(selectedid).getId(),new Contact(id,name,phone,ischeck));
//            //cập nhật adapter
                contactList=myDB.getAllContact();
                listAdapter= new adt(contactList,this);

                // listAdapter.notifyDataSetChanged();
                listContact.setAdapter(listAdapter);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList=new ArrayList<>();
        myDB=new MyDB(this,"UserDB2",null,1);
//        contactList.add(new Contact(1,"dao manh quang","02384242",false));
//        contactList.add(new Contact(2,"dao manh quang","02384242",false));
//        contactList.add(new Contact(3,"dao manh nam","02384242",false));
//        contactList.add(new Contact(4,"dao manh hung","02384242",false));
//        myDB.addContact(new Contact(1,"dao manh quang","02384242",0));
//        myDB.addContact(new Contact(2,"dao manh nam","02384242",0));
//        myDB.addContact(new Contact(3,"dao manh quang","02384242",0));
//        myDB.addContact(new Contact(4,"dao manh Trung","02384242",0));
        contactList=myDB.getAllContact();
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
                // Create an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // Set the dialog title
                builder.setTitle("Thông báo");
                // Set the message
                builder.setMessage("Bạn có muốn xóa không ?");

                // Set the positive button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle positive button click
                        for (Contact item:contactList
                        ) {
                            if(item.isCheck()==1){
                                a.add(item);
                            }
                        }
                        for (Contact a1:a
                        ) {
                            myDB.deleteContact(a1.getId());
                        }
                        contactList=myDB.getAllContact();
                        listAdapter= new adt(contactList,MainActivity.this);
                        listContact.setAdapter(listAdapter);
                        Toast.makeText(MainActivity.this, "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });

                // Set the negative button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle negative button click

                    }
                });

                // Create the AlertDialog object and show it
                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });
        registerForContextMenu(listContact);
        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedid = i;

            }
        });
        listContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                selectedid = position;
                return false;
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
