package com.revolve44.mosmetro2;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revolve44.mosmetro2.adapters.StationAdapterX;
import com.revolve44.mosmetro2.storage.SharedPref;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityLine extends AppCompatActivity {

    /*
    Here we are may see list of stations of Moscow Subway

    Third Screen
     */

    private RecyclerView recyclerView;
    private StationAdapterX adapter;
    private ArrayList<String> data2 = new ArrayList<>();

    //Map<String, String> alldata = new HashMap<>();
    String finalJson;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_stations);

        preparestart();
        initViews();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                //Do something after 100ms

                buildcards();

            }
        }, 1000);
    }

    private void preparestart() {
        String name = getIntent().getStringExtra(MainActivity.EXTRA_NAME);
        //Toast.makeText(getApplicationContext(), ""+ name, Toast.LENGTH_LONG).show();
        assert name != null;
        if (name.equals("Московское центральное кольцо")){
            Snackbar.make(findViewById(android.R.id.content),"Выбрано "+name,Snackbar.LENGTH_SHORT).show();
        }else {
            Snackbar.make(findViewById(android.R.id.content),"Выбрана "+name,Snackbar.LENGTH_SHORT).show();
        }


        finalJson = SharedPref.getHash(getApplicationContext());
        //get from shared prefs
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        HashMap<String, String> alldata = gson.fromJson(finalJson, type);

        for (Map.Entry<String, String> entry : alldata.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value.equals(name)){
                data2.add(key);
            }

            // ...
        }
        Log.d("777", ""+data2);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_st);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildcards() {
//        ArrayList<String> list1 = new ArrayList<String>();
//        Collections.addAll(list1, copy);
        //List<String> list1 = new ArrayList<String>();
        //Collections.addAll(data, lines2);


        //data = list1;
        adapter = new StationAdapterX(data2);
        recyclerView.setAdapter(adapter);


       // Log.d("zv",""+ Arrays.toString(names2));
        Log.d("zv2",""+data2 +"[ size = "+data2.size());
    }
}
