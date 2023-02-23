package com.example.myapplication1;


import android.net.Uri;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import static androidx.core.content.ContextCompat.startActivity;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;

import android.content.Intent;

public class adt extends BaseAdapter implements Filterable {
    private ArrayList<Contact> contacts;
    private Activity activity;
    private LayoutInflater inflater;
    //khai báo đối tương LayoutInflater để phân tích giao diện cho một phần tử
    private ArrayList<Contact> databackup;
    static  Intent intent;

    public adt(ArrayList<Contact> contacts, Activity activity ) {
        this.contacts = contacts;
        this.activity = activity;
        this.inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contacts.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null)
            v=inflater.inflate(R.layout.activity_adduser,null);
        TextView tvID=v.findViewById(R.id.textView);
        tvID.setText(contacts.get(i).getName());
        TextView tvName=v.findViewById(R.id.textView2);
        tvName.setText(contacts.get(i).getSdt());
        CheckBox isdelete=v.findViewById(R.id.checkBox);
        isdelete.setChecked(contacts.get(i).isCheck());
        isdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isdelete.isChecked()==true){
                    contacts.get(i).setCheck(true);
                }
                else
                    contacts.get(i).setCheck(false);
            }
        });

        ImageView call= v.findViewById(R.id.imageView2);
        ImageView messager= v.findViewById(R.id.imageView3);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("tel:0395533345"));
                view.getContext().startActivity(intent1);
            }
        });
        messager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(Intent.ACTION_VIEW,
                        Uri.parse("sms:0395533345"));
                view.getContext().startActivity(intent1);
            }
        });
        return  v;
    }
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(databackup==null)
                    databackup = new ArrayList<>(contacts);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else{
                    ArrayList<Contact> newdata = new ArrayList<>();
                    for(Contact u:databackup)
                        if(u.getName().toLowerCase().contains(
                                charSequence.toString().toLowerCase()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;
                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                contacts = new ArrayList<Contact>();
                ArrayList<Contact> tmp =(ArrayList<Contact>)filterResults.values;
                for(Contact u: tmp)
                    contacts.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
