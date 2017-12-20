package com.reshmast.instantchatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.reshmast.instantchatapp.R;

import java.util.ArrayList;

/**
 * Created by fostersoftsol03 on 6/12/17.
 */

public class CustomMsgAdapter extends BaseAdapter {

    ArrayList<String> alTitle;
    ArrayList<String> alUser;
    ArrayList<String> alDateTime;
    ArrayList<String> alImage;

    Context context;
    private LayoutInflater inflater;

    public CustomMsgAdapter(Context contex, ArrayList<String> alUser, ArrayList<String> alTitle, ArrayList<String> alDateTime, ArrayList<String> alImage){
        this.alTitle=alTitle;
        this.alUser =alUser;
        this.alDateTime =alDateTime;
        this.alImage =alImage;
        this.context=contex;

        inflater = LayoutInflater.from(context);

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
        TextView tv_user;
        TextView tv_datetime;
        ImageView iv_image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder;

        if(convertView ==null){

            holder = new Holder();
            convertView = inflater.inflate(R.layout.custom_msg_list, null);
            holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_user=(TextView) convertView.findViewById(R.id.tv_user);
            holder.tv_datetime=(TextView) convertView.findViewById(R.id.tv_datetime);
            holder.iv_image=(ImageView) convertView.findViewById(R.id.iv_image);

            convertView.setTag(holder);
        }else {

            holder = (Holder) convertView.getTag();
        }



        holder.tv_title.setText(alTitle.get(position));
        holder.tv_user.setText(alUser.get(position));
        holder.tv_datetime.setText(alDateTime.get(position));
        Glide.with(this.context).load(alImage.get(position))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_image);



        return convertView;
    }
}
