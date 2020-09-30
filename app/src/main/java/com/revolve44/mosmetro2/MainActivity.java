package com.revolve44.mosmetro2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.revolve44.mosmetro2.network.CellsResult;
import com.revolve44.mosmetro2.network.MosService;
import com.revolve44.mosmetro2.storage.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    /*
    Here we make request to server, and save answer from him in HashMap

    First Screen
     */


    public static String BaseUrl = "https://apidata.mos.ru/v1/";
    public static String otherbody = "datasets/1488/rows/?api_key=79bba0ab1c9f025ca4136af28229c43a";
    String[] names2 = new String[305];
    String[] lines2 = new String[17];

    boolean found = false;
    Map<String, String> stn = new HashMap<>();

    private ArrayList<String> mcr = new ArrayList<>();
    public static final String LISTX =
            "LISTX";
    public static final String EXTRA_NAME =
            "EXTRA_NAME";
    public static final String EXTRA_LINE =
            "EXTRA_DESCRIPTION";//?
    public static final String EXTRA_OFF =
            "EXTRA_OFF";

    ArrayList<String> Stations = new ArrayList<>();
    ArrayList<String> Lines = new ArrayList<>();

    private CoordinatorLayout coordinatorLayout;
    Boolean offline = false;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_load);
        textView = findViewById(R.id.loadingtv);

        //api.startcall();
        getCurrentData();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something
                Engine();

            }
        }, 3000);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void getCurrentData() {
        Log.d("Lifecycle -> method ", " getCurrentdata ");

        //Log.d("Lifecycle -> method ", " latitude " + lat + lon);
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();//for create a LOGs
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(); //for create a LOGs
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //for create a LOGs
        okhttpClientBuilder.addInterceptor(logging); //for create a LOGs

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build()) //for create a LOGs
                .build();
        MosService service = retrofit.create(MosService.class);
        //Call<WeatherResponse> call = service.getCurrentWeatherData(CITY, metric, AppId);
        Call<List<CellsResult>> call = service.getCellsValue(otherbody);

        call.enqueue(new Callback<List<CellsResult>>() {
            @Override
            public void onResponse(@NotNull Call<List<CellsResult>> call, @NotNull Response<List<CellsResult>> response) {

                List<CellsResult> rs = response.body();
                assert rs != null;

                Log.d("***",""+rs.size());
                String[] heroes = new String[rs.size()];
                String[] lines = new String[rs.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < rs.size(); i++) {


                    Lines.add(i, rs.get(i).getCells().getLine());
                    Stations.add(i, rs.get(i).getCells().getStation());
                }

                lines2 = new HashSet<String>(Arrays.asList(lines)).toArray(new String[0]);

                names2 = heroes;

                int q = 0;
                while (q < Stations.size() && q< Lines.size()) { //for safety i will check on both sizes
                    stn.put(Stations.get(q),Lines.get(q));
                    q++;
                }
                Log.d("Map is ", "iter "+stn);
                Gson gson = new Gson();
                String json = gson.toJson(stn);

                SharedPref.setHash(json,getApplicationContext());
            }


            @Override
            public void onFailure(@NotNull Call<List<CellsResult>> call, @NotNull Throwable t) {
                Log.e("ERROR", " On Retrofit "+t.getMessage());
                Snackbar.make(findViewById(android.R.id.content),"Offline Mode ",10000)
                        .setTextColor(Color.RED)
                        .show();
                offline = true;
            }
        });
    }

    public void Engine(){
        if (SharedPref.getCount(getApplicationContext())<5){
            Intent goaway = new Intent(MainActivity.this, StarterActivity.class);
            goaway.putExtra(EXTRA_OFF, offline);
            //goaway.putExtra(EXTRA_LINE, nominalpower);
            startActivity(goaway);
            int a = SharedPref.getCount(getApplicationContext());
            a= a+1;
            SharedPref.setCount(a,getApplicationContext());
        }else{
            textView.setText("Check Internet \nConnection");
        }
    }

    @Override
    protected void onDestroy() {
        SharedPref.setCount(0,getApplicationContext());
        super.onDestroy();
    }
}