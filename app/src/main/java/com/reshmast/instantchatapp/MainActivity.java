package com.reshmast.instantchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_all_posts;
    private Button btn_post_order;
    private Button btn_chat_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listeners();


    }

    private void init(){

        btn_all_posts = findViewById(R.id.btn_all_posts);
        btn_post_order = findViewById(R.id.btn_post_order);
        btn_chat_history = findViewById(R.id.btn_chat_history);

    }

    private void listeners(){

        btn_all_posts.setOnClickListener(this);
        btn_post_order.setOnClickListener(this);
        btn_chat_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == btn_all_posts){

            startActivity(new Intent(MainActivity.this, AllPosts.class));
        }

        if(view == btn_post_order){


            startActivity(new Intent(MainActivity.this, PostOrder.class));

        }

        if(view == btn_chat_history){


            startActivity(new Intent(MainActivity.this, ChatHistory.class));

        }
    }
}
