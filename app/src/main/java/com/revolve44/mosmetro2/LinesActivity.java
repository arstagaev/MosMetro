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
import com.revolve44.mosmetro2.adapters.LineAdapter;
import com.revolve44.mosmetro2.storage.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


import static com.revolve44.mosmetro2.SplashActivity.offline;
import static com.revolve44.mosmetro2.network.Api.EXTRA_NAME;
import static com.revolve44.mosmetro2.network.Api.EXTRA_ST;

public class LinesActivity extends AppCompatActivity {

       /*
    Here we are may see list of lines of Moscow Subway

    Second Screen
     */

    private RecyclerView recyclerView;
    private LineAdapter adapter;
    private ArrayList<String> data = new ArrayList<>();
    TreeMap<String, String> secondhm = new TreeMap<>();
    TreeMap<String, Integer> IdOrder = new TreeMap<>();
    TreeMap<Integer, String> FinalOrder = new TreeMap<>();

    HashSet<String> set1 = new HashSet<>();
    Gson gson = new Gson();
    String json;
    String json2;

    String[] lines2 = new String[17];
    Boolean offline2 = false;
    ArrayList<String> xStations = new ArrayList<>(); // just selected stations
    int q = 0;

    ArrayList<Integer> allIDs = new ArrayList<>(); // ID
    ArrayList<String> allSt = new ArrayList<>(); // St

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_main);

        //api.startcall();
        //offline2 = getIntent().getBooleanExtra(SplashActivity.EXTRA_OFF,false);
        if (offline){
            Snackbar.make(findViewById(android.R.id.content),"Offline Mode ",10000)
                    .setTextColor(Color.RED)
                    .show();
        }
        try {
            json = SharedPref.getTreeMap1(getApplicationContext());
            //get from shared prefs

            java.lang.reflect.Type type = new TypeToken<TreeMap<String, String>>(){}.getType();
            secondhm = gson.fromJson(json, type);


            for (Map.Entry<String, String> entry : secondhm.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                set1.add(value);


            }
        }catch (Exception e){
            Intent goaway2 = new Intent(LinesActivity.this, SplashActivity.class);
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
                        buildcards();
                    }
                }, 1000);
            }
        }, 1000);
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

        //convert to linked list
        final ArrayList<String> usrAll = new ArrayList<String>(set1);
        LinkedList<String> linkList = new LinkedList<String>(usrAll);

        adapter = new LineAdapter(linkList);
        recyclerView.setAdapter(adapter);
        SharedPref.setCheckData(true, getApplicationContext());

        Log.d("zv",""+ Arrays.toString(lines2));
        Log.d("zv2",""+usrAll +"[ size = "+usrAll.size());
        //added mb wrong
        adapter.setOnItemClickListener(new LineAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("TAG", "onItemClick position: " + position);

                String selectedline = usrAll.get(position);

                //ArrayList<String> StationsInOrder = new ArrayList<>();

                //get from shared prefs
                json2 = SharedPref.getTreeMap2(getApplicationContext());
                java.lang.reflect.Type type2 = new TypeToken<TreeMap<String, Integer>>(){}.getType();
                IdOrder = gson.fromJson(json2, type2);


                int r = 0;
                for (Map.Entry<String, String> entry : secondhm.entrySet()) {
                    String key = entry.getKey();  // stations
                    String value = entry.getValue(); // lines

                    if (value.equals(selectedline)){

                        FinalOrder.put(IdOrder.get(key),key);

                        //xStations.get(r)
                       // r++;
                    }
                    // ...
                }
                Log.d("order1",""+FinalOrder);
                int q= 0;

                for (Map.Entry<Integer, String> entry : FinalOrder.entrySet()) {
                    Integer key = entry.getKey();  // ID
                    String value = entry.getValue();  // stations
                    xStations.add(value);
                }
                Log.d("order1.2",""+xStations.toString());

                if (selectedline.substring(0, 4).equals("Солн")){
                    xStations = CodeTrick.solcevscaya;
                }else if (selectedline.substring(0, 4).equals("Замо")){
                    xStations = CodeTrick.zamoscvorec;
                }else if (selectedline.substring(0, 4).equals("Некр")){
                    xStations = CodeTrick.nekrasovskaya;
                }else if (selectedline.substring(0, 4).equals("Любл")){
                    xStations = CodeTrick.lublinodmitrievskaya;
                }


                // Send Name of Line

                Intent goaway3 = new Intent(getApplicationContext(), StationsActivity.class);
                goaway3.putExtra(EXTRA_NAME, usrAll.get(position));
                goaway3.putStringArrayListExtra(EXTRA_ST, xStations);
                startActivity(goaway3);
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Log.d("TAG", "onItemLongClick pos = " + position);
            }
        });

    }




    @Override
    protected void onStop() {
        Log.d("cyc","Stop");
        FinalOrder.clear();
        xStations.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("cyc","Destroy");
        super.onDestroy();

    }
}
