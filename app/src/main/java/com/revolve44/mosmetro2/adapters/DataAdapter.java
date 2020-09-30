package com.revolve44.mosmetro2.adapters;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.revolve44.mosmetro2.R;

import java.util.LinkedList;

/**
 * Adapter for Lines
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private static DataAdapter.ClickListener clickListener;
    private LinkedList<String> cellx;
    private  String nameLn;
    private final String l1 = "Калу";
    private final String l2 = "Арба";
    private final String l3 = "Соко";
    private final String l4 = "Кахо";
    private final String l5 = "Мос1";
    private final String l6 = "Коль";

    private final String l7 = "Боль";
    private final String l8 = "Серп";
    private final String l9 = "Любл";
    private final String l10 = "Кали";
    private final String l11 = "Тага";
    private final String l12 = "Мос2";

    private final String l13 = "Солн";
    private final String l14 = "Филё";
    private final String l15 = "Буто";
    private final String l16 = "Некр";
    private final String l17 = "Замо";


    public DataAdapter(LinkedList<String> android) {
        this.cellx = android;
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, final int position) {
        holder.tv_name.setText(cellx.get(position));

         nameLn= cellx.get(position).substring(0,4);
         //int idnow = Integer.parseInt(cellx.get(position));

        Log.d("Nek", nameLn);

        if (nameLn.equals(l1)){
            //holder.tv_name.setTextColor(Color.RED);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f5b400"));
        }else if(nameLn.equals(l2)){
           // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#4e00f5"));
        }else if(nameLn.equals(l3)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.RED);
        }else if(nameLn.equals(l4)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#00f5c4"));
        }else if(nameLn.equals(l5)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#9faba8"));
        }else if(nameLn.equals(l6)){
            // holder.tv_name.setTextColor(Color.BLACK);/////////////
            holder.cardView.setCardBackgroundColor(Color.parseColor("#782a02"));
        }else if(nameLn.equals(l7)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#521d02"));
        }else if(nameLn.equals(l8)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#7d7d7d"));
        }else if(nameLn.equals(l9)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#1dcc71"));
        }else if(nameLn.equals(l10)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#f2ff00"));
        }else if(nameLn.equals(l11)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ff00fb"));
        }else if(nameLn.equals(l12)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#2600ff"));
        }else if(nameLn.equals(l13)){
            // holder.tv_name.setTextColor(Color.BLACK);

            holder.cardView.setCardBackgroundColor(Color.parseColor("#f2ff00"));
        }else if(nameLn.equals(l14)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#3700ff"));
        }else if(nameLn.equals(l15)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#1e0c61"));
        }else if(nameLn.equals(l16)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ff0099"));
        }else if(nameLn.equals(l17)){
            // holder.tv_name.setTextColor(Color.BLACK);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#0b7000"));
        }

//        switch (nameLn){
//            case "Некрасовская линия":
//
//                holder.cardView.setCardBackgroundColor(Color.RED);
//            case "Серпуховско-Тимирязевская линия":
//                holder.cardView.setCardBackgroundColor(Color.BLUE);
//
//        }
////        switch (position){
//            case 0:
//                holder.cardView.setCardBackgroundColor(Color.RED);
//            case 1:
//                holder.cardView.setCardBackgroundColor(Color.BLUE);
//
//        }

//        holder.tv_version.setText(cellx.get(position).getVer());
//        holder.tv_api_level.setText(cellx.get(position).getApi());

    }

    @Override
    public int getItemCount() {
        return cellx.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_name;
        private TextView tv_line;
        private TextView tv_api_level;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.linescard);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_line = (TextView)itemView.findViewById(R.id.tv_version);
            tv_api_level = (TextView)itemView.findViewById(R.id.tv_api_level);
            itemView.setOnClickListener(this);

            //cardView.setCardBackgroundColor(Color.RED);


        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);

            Log.d(" --- ", "444 onBindViewHolder: 4 == "+getAdapterPosition());
        }
    }

    public void setOnItemClickListener(DataAdapter.ClickListener clickListener) {
        DataAdapter.clickListener = clickListener;
        notifyDataSetChanged();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
