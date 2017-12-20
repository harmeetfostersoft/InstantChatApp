package com.reshmast.instantchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.reshmast.instantchatapp.adapters.CustomMsgAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ChatHistory extends AppCompatActivity {

    private ListView chatsList;
    private TextView nochatText;
    private ArrayList<String> alUser = new ArrayList<>();
    private ArrayList<String> alOrderId = new ArrayList<>();
    private ArrayList<String> alImage = new ArrayList<>();
    private ArrayList<String> alDateTime = new ArrayList<>();
    private ArrayList<String> alTitle = new ArrayList<>();
    private int totalUsers = 0;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        chatsList = (ListView)findViewById(R.id.chatList);
        nochatText = (TextView)findViewById(R.id.noChatText);

        pd = new ProgressDialog(ChatHistory.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://instantchatapp-87f7c.firebaseio.com/chatlist.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(ChatHistory.this);
        rQueue.add(request);

        chatsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = alUser.get(position);
                UserDetails.orderId = alOrderId.get(position);
                startActivity(new Intent(ChatHistory.this, Chat.class));
            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            JSONObject jsonObject = obj.getJSONObject(UserDetails.username);


            Iterator i = jsonObject.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.contains(UserDetails.username)) {

                    JSONObject jsonObject1 = jsonObject.getJSONObject(key);

                    alTitle.add(jsonObject1.getString("title"));
                    alOrderId.add(jsonObject1.getString("order_id"));
                    alUser.add(jsonObject1.getString("user"));
                    alImage.add(jsonObject1.getString("image"));
                    alDateTime.add(jsonObject1.getString("last_seen"));

                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <1){
            nochatText.setVisibility(View.VISIBLE);
            chatsList.setVisibility(View.GONE);
        }
        else{
            nochatText.setVisibility(View.GONE);
            chatsList.setVisibility(View.VISIBLE);
            chatsList.setAdapter(new CustomMsgAdapter(ChatHistory.this, alUser, alTitle, alDateTime, alImage));
        }

        pd.dismiss();
    }
}
