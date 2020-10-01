package com.revolve44.mosmetro2.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.revolve44.mosmetro2.CodeTrick;
import com.revolve44.mosmetro2.R;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private ArrayList<String> celly;
    private  String nameLn;
    private final String l1 = "Калу";
    private final String l2 = "Арба";
    private final String l3 = "Соко";


    public StationAdapter(ArrayList<String> android) {
        this.celly = android;
    }

    @Override
    public StationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linecard_row,parent,false);
        return new StationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StationAdapter.ViewHolder holder, int position) {
        Log.d("777", ""+celly);
        holder.tv_name.setText(celly.get(position));
//        holder.tv_version.setText(cellx.get(position).getVer());
//        holder.tv_api_level.setText(cellx.get(position).getApi());

        nameLn= celly.get(position).substring(0,4);
        //int idnow = Integer.parseInt(cellx.get(position));

        Log.d("Nek", nameLn);

//        if (nameLn.equals(l1)){
//            //holder.tv_name.setTextColor(Color.RED);
//            holder.tv_name.setText(celly.get(position));}
    }

    @Override
    public int getItemCount() {
        return celly.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_name;
        private TextView tv_line;
        private TextView tv_api_level;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView)itemView.findViewById(R.id.namestation);
//            tv_line = (TextView)itemView.findViewById(R.id.tv_version);
//            tv_api_level = (TextView)itemView.findViewById(R.id.tv_api_level);

        }



        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        StationAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
