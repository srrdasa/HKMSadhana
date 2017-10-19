package com.hkm.hkmsadhna.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkm.hkmsadhna.Login;
import com.hkm.hkmsadhna.R;
import com.hkm.hkmsadhna.model.ListItem;

import java.util.List;

/**
 * Created by abhi on 03/09/17.
 */

public class DerpAdapter extends RecyclerView.Adapter<DerpAdapter.DerpHolder> {
    Typeface t;
    public DerpAdapter(List<ListItem> listData, Context c,Typeface tf) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
        this.t = tf;
    }

    private List<ListItem> listData;
    private LayoutInflater inflater;

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        ListItem item = listData.get(position);
        holder.id.setTextColor(Color.BLUE);
        holder.id.setText(item.getId()+"");
        String s1,s2,s3,s4;

        int a,b,c,d;
        a = Integer.parseInt(item.getMaPoints());
        b = Integer.parseInt(item.getDaPoints());
        c = Integer.parseInt(item.getJpPoints());
        d = Integer.parseInt(item.getSbPoints());
        Log.v("MNMN",item.getDaPoints());
        returnValue(Integer.parseInt(item.getMaPoints()));
        returnValue(Integer.parseInt(item.getDaPoints()));
        returnValue(Integer.parseInt(item.getJpPoints()));
        returnValue(Integer.parseInt(item.getSbPoints()));

        holder.maPoints.setText(returnValue(Integer.parseInt(item.getMaPoints())));
        holder.daPoints.setText(returnValue(Integer.parseInt(item.getDaPoints())));
        holder.jpPoints.setText(returnValue(Integer.parseInt(item.getJpPoints())));
        holder.sbPoints.setText(returnValue(Integer.parseInt(item.getSbPoints())));
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }




    class DerpHolder extends RecyclerView.ViewHolder{

        private TextView id;
        private TextView maPoints;
        private TextView jpPoints;
        private TextView daPoints;
        private TextView sbPoints;

        public DerpHolder(View itemView) {
            super(itemView);

            id = (TextView)itemView.findViewById(R.id.id);
            id.setTypeface(t);
            maPoints = (TextView) itemView.findViewById(R.id.ma);
            maPoints.setTypeface(t);

            daPoints = (TextView) itemView.findViewById(R.id.da);
            daPoints.setTypeface(t);

            sbPoints = (TextView) itemView.findViewById(R.id.sb);
            sbPoints.setTypeface(t);

            jpPoints = (TextView) itemView.findViewById(R.id.jp);
            jpPoints.setTypeface(t);

        }
    }

    String returnValue(int a){
        String s1 = null;
        if (a == -1){
            s1 = "-";
        }else if (a == 0){
            s1 = "NO";
        }else if (a == 25){
            s1 = "Half";
        }else if (a == 50){
            s1 = "HALF";
        }else if (a == 75){
            s1 = "Half";
        }else if (a == 100){
            s1 = "FULL";
        }else if (a == 8){
            s1 = "SICK";
        }else if (a == 9){
            s1 = "OS";
        }else if (a == 10){
            s1 = "Othors";
        }
        return s1;
    }
}
