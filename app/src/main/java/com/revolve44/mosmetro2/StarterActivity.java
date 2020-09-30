package com.revolve44.mosmetro2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revolve44.mosmetro2.adapters.DataAdapter;
import com.revolve44.mosmetro2.storage.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import static com.revolve44.mosmetro2.MainActivity.EXTRA_NAME;

public class StarterActivity extends AppCompatActivity {

       /*
    Here we are may see list of lines of Moscow Subway

    Second Screen
     */

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    HashMap<String, String> secondhm = new HashMap<>();
    HashSet<String> set1 = new HashSet<>();

    String json;

    String[] lines2 = new String[17];
    Boolean offline2 = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_main);

        //api.startcall();
        offline2 = getIntent().getBooleanExtra(MainActivity.EXTRA_OFF,false);
        if (offline2){
            Snackbar.make(findViewById(android.R.id.content),"Offline Mode ",10000)
                    .setTextColor(Color.RED)
                    .show();
        }
        try {
            json = SharedPref.getHash(getApplicationContext());
            //get from shared prefs
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            secondhm = gson.fromJson(json, type);

            for (Map.Entry<String, String> entry : secondhm.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                set1.add(value);

                // ...
            }
        }catch (Exception e){
            Intent goaway2 = new Intent(StarterActivity.this, MainActivity.class);
            startActivity(goaway2);
        }





        final Handler handler2 = new Handler(Looper.getMainLooper());
        handler2.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                //Do something after 100ms


                initViews();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        buildcards();
                    }
                }, 1000);
            }
        }, 3000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildcards() {

        //lines2 = new HashSet<String>(Arrays.asList(lines2)).toArray(new String[0]);
        Collections.addAll(data, lines2);

//        for (Map.Entry<String, String> entry : stn.entrySet()) {
//            String key = entry.getKey();
//            //String value = entry.getValue();
//            data.add(key);
//
//            // ...
//        }
//        Log.d("MapChecker", ""+stn);

        ;

        //convert to linked list
        final ArrayList<String> usrAll = new ArrayList<String>(set1);
        LinkedList<String> linkList = new LinkedList<String>(usrAll);

        adapter = new DataAdapter(linkList);
        recyclerView.setAdapter(adapter);

        Log.d("zv",""+ Arrays.toString(lines2));
        Log.d("zv2",""+usrAll +"[ size = "+usrAll.size());
        //added mb wrong
        adapter.setOnItemClickListener(new DataAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("TAG", "onItemClick position: " + position);
                //Toast.makeText(getApplicationContext(),"Hey "+ usrAll.get(position),Toast.LENGTH_LONG).show();
                usrAll.get(position);

                Intent goaway = new Intent(getApplicationContext(), ActivityLine.class);
                goaway.putExtra(EXTRA_NAME, usrAll.get(position));
                //goaway.putExtra(EXTRA_LINE, nominalpower);
                startActivity(goaway);

            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("TAG", "onItemLongClick pos = " + position);
            }
        });
    }
}
