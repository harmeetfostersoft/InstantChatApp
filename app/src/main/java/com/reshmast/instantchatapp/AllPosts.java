package com.reshmast.instantchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.firebase.client.Firebase;
import com.reshmast.instantchatapp.adapters.CustomAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by fostersoftsol03 on 5/12/17.
 */

public class AllPosts extends AppCompatActivity {
    private ListView postsList;
    private TextView noPostsText;
    private ArrayList<String> alUser = new ArrayList<>();
    private ArrayList<String> alTitle = new ArrayList<>();
    private ArrayList<String> alDesc = new ArrayList<>();
    private ArrayList<String> alOrderId = new ArrayList<>();
    private int totalUsers = 0;
    private ProgressDialog pd;
    private String strImagePath = "http://fostersoftsolutions.com/humbledonator/charity/charity_image/Superman%20In%20Fire_26.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Firebase.setAndroidContext(this);

        postsList = (ListView)findViewById(R.id.usersList);
        noPostsText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(AllPosts.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://instantchatapp-87f7c.firebaseio.com/orders.json";

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

        RequestQueue rQueue = Volley.newRequestQueue(AllPosts.this);
        rQueue.add(request);

        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                UserDetails.chatWith = alUser.get(position);
//                startActivity(new Intent(AllPosts.this, Chat.class));
            }
        });
    }

    public void doOnSuccess(String s){

        Log.e("Response:", s+"");

        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){

                key = i.next().toString();
                JSONObject jsonObject = obj.getJSONObject(key);

                if(!key.contains(UserDetails.username)) {
                String[] str = key.split("_");
                alUser.add(str[0]);
                alTitle.add(jsonObject.getString("title"));
                alOrderId.add(jsonObject.getString("order_id"));
                alDesc.add(jsonObject.getString("description"));
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(alUser.size() <1){
            noPostsText.setVisibility(View.VISIBLE);
            postsList.setVisibility(View.GONE);
        }
        else{
            noPostsText.setVisibility(View.GONE);
            postsList.setVisibility(View.VISIBLE);

            postsList.setAdapter(new CustomAdapter(getApplicationContext(), alTitle, alDesc, new CustomAdapter.ListAdapterListener() {
                @Override
                public void onClickAtMessageButton(final int position) {

                    final ProgressDialog pd = new ProgressDialog(AllPosts.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://instantchatapp-87f7c.firebaseio.com/chatlist.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {

                            Firebase reference = new Firebase("https://instantchatapp-87f7c.firebaseio.com/chatlist");

                            if(s.equals("null")) {

                                reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("title").setValue(alTitle.get(position));
                                reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("order_id").setValue(alOrderId.get(position));
                                reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("user").setValue(alUser.get(position));
                                reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("image").setValue(strImagePath);
                                reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("last_seen").setValue(getCurrentDateTime());

                                reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("title").setValue(alTitle.get(position));
                                reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("order_id").setValue(alOrderId.get(position));
                                reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("user").setValue(UserDetails.username);
                                reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("image").setValue(strImagePath);
                                reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("last_seen").setValue(getCurrentDateTime());

                                UserDetails.chatWith = alUser.get(position);
                                UserDetails.orderId = alOrderId.get(position);
                                startActivity(new Intent(AllPosts.this, Chat.class));
                            }
                            else {
                                try {

                                    reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("title").setValue(alTitle.get(position));
                                    reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("order_id").setValue(alOrderId.get(position));
                                    reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("user").setValue(alUser.get(position));
                                    reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("image").setValue(strImagePath);
                                    reference.child(UserDetails.username).child(alUser.get(position)+"_"+alOrderId.get(position)).child("last_seen").setValue(getCurrentDateTime());

                                    reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("title").setValue(alTitle.get(position));
                                    reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("order_id").setValue(alOrderId.get(position));
                                    reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("user").setValue(UserDetails.username);
                                    reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("image").setValue(strImagePath);
                                    reference.child(alUser.get(position)).child(UserDetails.username+"_"+alOrderId.get(position)).child("last_seen").setValue(getCurrentDateTime());

                                    UserDetails.chatWith = alUser.get(position);
                                    UserDetails.orderId = alOrderId.get(position);
                                    startActivity(new Intent(AllPosts.this, Chat.class));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(AllPosts.this);
                    rQueue.add(request);

                }
            }));
        }

        pd.dismiss();
    }

    public String getCurrentDateTime(){

        //get current date and time from system
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDateandTime = sdf.format(calendar.getTime());

        //return date and time as a string
        return strDateandTime;
    }
}
