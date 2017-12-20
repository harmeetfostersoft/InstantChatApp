package com.reshmast.instantchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.reshmast.instantchatapp.R;

import java.util.ArrayList;

/**
 * Created by fostersoftsol03 on 5/12/17.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> alTitle;
    ArrayList<String> alDesc;

    Context context;
    private LayoutInflater inflater;


    ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void onClickAtMessageButton(int position); // create callback function
    }

    public CustomAdapter(Context contex, ArrayList<String> alTitle, ArrayList<String> alDesc, ListAdapterListener listener){
        this.alTitle=alTitle;
        this.alDesc=alDesc;
        this.context=context;
        mListener=listener;

        inflater = LayoutInflater.from(contex);

    }
    @Override
    public int getCount() {
        return alTitle.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Holder
    {
        TextView tv_title;
        TextView tv_desc;
        Button btn_message;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;

        if(convertView ==null){

            holder = new Holder();
            convertView = inflater.inflate(R.layout.custom_user_list, null);
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_desc=(TextView) convertView.findViewById(R.id.tv_desc);
            holder.btn_message=(Button) convertView.findViewById(R.id.btn_message);

            convertView.setTag(holder);
        }else {

            holder = (Holder) convertView.getTag();
        }



        holder.tv_title.setText(alTitle.get(position));
        holder.tv_desc.setText(alDesc.get(position));
        holder.btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClickAtMessageButton(position);
            }
        });


        return convertView;
    }
}
