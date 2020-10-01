package com.revolve44.mosmetro2.network;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.revolve44.mosmetro2.SplashActivity;
import com.revolve44.mosmetro2.storage.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static String BaseUrl = "https://apidata.mos.ru/v1/";
    public static String otherbody = "datasets/1488/rows/?api_key=79bba0ab1c9f025ca4136af28229c43a";

    Map<String, String> stn = new TreeMap<>();
    Map<String, Integer> orderSt = new TreeMap<>();


    public static final String EXTRA_NAME =
            "EXTRA_NAME";
    public static final String EXTRA_ST =
            "EXTRA_DESCRIPTION";//?
    public static final String EXTRA_OFF =
            "EXTRA_OFF";

    ArrayList<String> Stations = new ArrayList<>();
    ArrayList<String> Lines = new ArrayList<>();

    Context ctx;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getCurrentData() {

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

                Log.d("response size ",""+rs.size());
//                String[] heroes = new String[rs.size()];
//                String[] lines = new String[rs.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < rs.size(); i++) {
                    Lines.add(i, rs.get(i).getCells().getLine());
                    Stations.add(i, rs.get(i).getCells().getStation());

                    orderSt.put(rs.get(i).getCells().getStation(),rs.get(i).getCells().getID());
                }


                int q = 0;
                while (q < Stations.size() && q< Lines.size()) { //for safety i will check on both sizes
                    stn.put(Stations.get(q),Lines.get(q));
                    q++;
                }
                Log.d("Map is ", "iter "+stn);

                // Convert ThreeMap to Json and save this in SharedPref
                Gson gson = new Gson();
                String json = gson.toJson(stn);
                String json2 = gson.toJson(orderSt);

                SharedPref.setTreeMap1(json,ctx);
                SharedPref.setTreeMap2(json2,ctx);

                SplashActivity.offline = false;
            }


            @Override
            public void onFailure(@NotNull Call<List<CellsResult>> call, @NotNull Throwable t) {
                Log.e("ERROR", " On Retrofit "+t.getMessage());

                SplashActivity.offline = true;
            }
        });
    }
}
