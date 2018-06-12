package com.bharat.lcoapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> questions;
    private HashMap<String,String> pairs;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private LinearLayout adLayout;
    private Boolean adPref;
    private TextView adText;
    private TextView headingText;
    private ObjectAnimator imageViewObjectAnimator2;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        if(expandableListAdapter!=null) {
            //expandableListAdapter.notifyDataSetChanged(); //was not working
            expandableListAdapter = new ExpandableListAdapter(MainActivity.this,questions,pairs);
            expandableListView.setAdapter(expandableListAdapter);
        }
        adPref = prefs.getBoolean("ad",true);
        if(adPref){
            adLayout.setVisibility(View.VISIBLE);
        }else {
            adLayout.setVisibility(View.INVISIBLE);
        }
        imageViewObjectAnimator2.start();
    }


    //Action Bar Button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.setting_btn){
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //Inflating Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headingText = findViewById(R.id.heading_text);
        questions = new ArrayList<>();
        pairs = new HashMap<>();
        expandableListView = findViewById(R.id.expListView);
        adLayout = findViewById(R.id.ad);
        adText = findViewById(R.id.clickHere);

        //Animations
        imageViewObjectAnimator2 = ObjectAnimator.ofFloat(headingText ,
                "rotation", 0f, 360f);
        imageViewObjectAnimator2.setRepeatCount(2);
        imageViewObjectAnimator2.setInterpolator(new AccelerateInterpolator());


        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        adText.startAnimation(anim);

        adLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlLCO = "https://courses.learncodeonline.in";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urlLCO));
                startActivity(intent);

            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                imageViewObjectAnimator2.start();

            }
        });
        final String jsonLink = "https://learncodeonline.in/api/android/datastructure.json";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonLink, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray questionsArray = response.getJSONArray("questions");

                            for(int i=0;i<questionsArray.length();i++){
                                JSONObject jsonObject = questionsArray.getJSONObject(i);

                                String ques = jsonObject.getString("question");
                                String ans = jsonObject.getString("Answer");

                                Log.d("Pair",ques+ans);

                                questions.add(ques);
                                pairs.put(ques,ans);
                            }
                            expandableListAdapter = new ExpandableListAdapter(MainActivity.this,questions,pairs);
                            expandableListView.setAdapter(expandableListAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Unable to Fetch Data",Toast.LENGTH_SHORT).show();

            }
        });

        MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);




    }
}
