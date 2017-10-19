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

import com.hkm.hkmsadhna.R;
import com.hkm.hkmsadhna.model.ListItem;

import java.util.List;

/**
 * Created by abhi on 03/09/17.
 */

public class NewDerpAdapter extends RecyclerView.Adapter<NewDerpAdapter.DerpHolder> {
    Typeface t;
    public NewDerpAdapter(List<ListItem> listData, Context c, Typeface tf) {
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

        String s = item.getId();
        String month;
        if (s.length() == 5) {
             month = s.charAt(4) + "";
        }
        else{
             month = s.charAt(4) + s.charAt(5) + "";
        }

        if (month.equals("1")){
            holder.id.setText("Jan - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("2")){
            holder.id.setText("Feb - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("3")){
            holder.id.setText("Mar - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("4")){
            holder.id.setText("Apr - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("5")){
            holder.id.setText("May - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("6")){
            holder.id.setText("Jun - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("7")){
            holder.id.setText("Jul - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("8")){
            holder.id.setText("Aug - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("9")){
            holder.id.setText("Sup - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("10")){
            holder.id.setText("Oct - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("11")){
            holder.id.setText("Nov - " + s.charAt(2)+"" + s.charAt(3));
        }else if (month.equals("12")){
            holder.id.setText("Dec - " + s.charAt(2)+"" + s.charAt(3));
        }
        else
            holder.id.setText(item.getId()+"");

        holder.maPoints.setText(item.getMaPoints());
        holder.daPoints.setText(item.getDaPoints());
        holder.jpPoints.setText(item.getJpPoints());
        holder.sbPoints.setText(item.getSbPoints());
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

}
