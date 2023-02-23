package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_Add extends AppCompatActivity {
    private ArrayList<Contact> contacts;

    private TextView etFullName;
    private TextView etID;
    private TextView etPhone;
    Button btnOk;
    Button btnCancel;
    private  boolean validate(){
        String name=etFullName.getText().toString();
        String phone=etPhone.getText().toString();
        try {
            int id = Integer.parseInt(etID.getText().toString());
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.trim().length()==0){
            Toast.makeText(getApplicationContext(), "Khong de trong ten", Toast.LENGTH_SHORT).show();

            return false;
        }
        if(phone.length()!=10){
            Toast.makeText(getApplicationContext(), "sdt phai du 10 chu so", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_add);
        etFullName=findViewById(R.id.txtTen);
        etID=findViewById(R.id.txtCode);
        etPhone=findViewById(R.id.txtSdt);
        btnOk=findViewById(R.id.btnAdd1);
        btnCancel=findViewById(R.id.btnCancel);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            int id=bundle.getInt("id");
            String img=bundle.getString("image");
            String name=bundle.getString("name");
            String phone=bundle.getString("phone");
            etID.setText(String.valueOf(id));
            etFullName.setText(name);
            etPhone.setText(phone);
            btnOk.setText("Edit");
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=Integer.parseInt(etID.getText().toString());
                    String name=etFullName.getText().toString();
                    String phone=etPhone.getText().toString();
                    Intent intent= new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putInt("id", id);
                    bundle.putString("name",name);
                    bundle.putString("phone",phone);
                    intent.putExtras(bundle);
                    setResult(150, intent);
                    finish();
                }
            });
        }
        else{
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(validate()){
                        int id=Integer.parseInt(etID.getText().toString());
                        String name=etFullName.getText().toString();
                        String phone=etPhone.getText().toString();
                        Intent intent= new Intent();
                        Bundle bundle=new Bundle();
                        bundle.putInt("id", id);
                        bundle.putString("name",name);
                        bundle.putString("phone",phone);
                        intent.putExtras(bundle);
                        setResult(100, intent);
                        finish();
                    }
                }
            });
        }

    }
}