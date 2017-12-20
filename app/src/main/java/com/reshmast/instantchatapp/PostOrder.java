package com.reshmast.instantchatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

public class PostOrder extends AppCompatActivity {
    private EditText et_order_title, et_order_id, et_order_desc;
    private Button btn_post;
    private String title, order_id, order_desc;
    private TextView tv_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);

        et_order_title = (EditText)findViewById(R.id.et_title);
        et_order_id = (EditText)findViewById(R.id.et_orderid);
        et_order_desc = (EditText)findViewById(R.id.et_desc);
        btn_post = (Button)findViewById(R.id.btn_post);
        tv_home = (TextView)findViewById(R.id.tv_goto_home);

        Firebase.setAndroidContext(this);

        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostOrder.this, MainActivity.class));
                finish();
            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_order_title.getText().toString();
                order_id = et_order_id.getText().toString();
                order_desc = et_order_desc.getText().toString();

                if(title.equals("")){
                    et_order_title.setError("can't be blank");
                }
                else if(order_id.equals("")){
                    et_order_id.setError("can't be blank");
                }
                else if(order_desc.equals("")){
                    et_order_desc.setError("can't be blank");
                }
                else if(title.length()<5){
                    et_order_title.setError("at least 5 characters long");
                }
                else if(order_id.length()<3){
                    et_order_id.setError("at least 3 characters long");
                }
                else if(order_desc.length()<20){
                    et_order_desc.setError("at least 20 characters long");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(PostOrder.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://instantchatapp-87f7c.firebaseio.com/orders.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {

                            Firebase reference = new Firebase("https://instantchatapp-87f7c.firebaseio.com/orders");

                            if(s.equals("null")) {

                                reference.child(UserDetails.username+"_"+order_id).child("order_id").setValue(order_id);
                                reference.child(UserDetails.username+"_"+order_id).child("title").setValue(title);
                                reference.child(UserDetails.username+"_"+order_id).child("description").setValue(order_desc);



                                Toast.makeText(PostOrder.this, "Post successful", Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(UserDetails.username+"_"+order_id)) {
                                        reference.child(UserDetails.username+"_"+order_id).child("order_id").setValue(order_id);
                                        reference.child(UserDetails.username+"_"+order_id).child("title").setValue(title);
                                        reference.child(UserDetails.username+"_"+order_id).child("description").setValue(order_desc);
                                        Toast.makeText(PostOrder.this, "Post successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(PostOrder.this, "already posted", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
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

                    RequestQueue rQueue = Volley.newRequestQueue(PostOrder.this);
                    rQueue.add(request);
                }
            }
        });
    }
}
