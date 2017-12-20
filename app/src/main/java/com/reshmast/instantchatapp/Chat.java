package com.reshmast.instantchatapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fostersoftsol03 on 5/12/17.
 */

public class Chat extends AppCompatActivity {
    private LinearLayout layout;
    private ImageView sendButton;
    private EditText messageArea;
    private ScrollView scrollView;
    private Firebase reference1, reference2;
    private ListView lv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://instantchatapp-87f7c.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith +"_"+ UserDetails.orderId);
        reference2 = new Firebase("https://instantchatapp-87f7c.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username +"_"+ UserDetails.orderId);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("timestamp", getCurrentDateTime());
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String timestamp = map.get("timestamp").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox(message, timestamp, 1);
                }
                else{
                    addMessageBox(message, timestamp, 2);
                }
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, String timestamp, int type){
        TextView textView = new TextView(Chat.this);
        TextView textView1 = new TextView(Chat.this);
        textView.setText(message);
        textView1.setText(timestamp);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setMinWidth(70);
        textView.setMaxWidth(300);
        lp1.setMargins(0, 0, 0, 0);
        textView.setLayoutParams(lp);
        textView1.setLayoutParams(lp1);

        if(type == 1) {

            lp.gravity = Gravity.RIGHT;
            lp.setMargins(20, 0, 0, 10);
            textView.setLayoutParams(lp);
            textView1.setLayoutParams(lp);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.button_background);
            textView.setGravity(Gravity.RIGHT);
            textView1.setGravity(Gravity.RIGHT);
            textView1.setTextSize(10);
        }
        else if(type == 2){

            lp.gravity = Gravity.LEFT;
            lp.setMargins(0, 0, 20, 10);
            textView.setLayoutParams(lp);
            textView1.setLayoutParams(lp);
            textView.setTextColor(getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.button_background);
            textView.setGravity(Gravity.LEFT);
            textView1.setGravity(Gravity.LEFT);
            textView1.setTextSize(10);
        }

        layout.addView(textView);
        layout.addView(textView1);
        scrollView.fullScroll(View.FOCUS_DOWN);
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
