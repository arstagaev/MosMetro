package com.revolve44.mosmetro2.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;




public interface MosService {
    @GET()
    Call<List<CellsResult>> getCellsValue(@Url String url);



}