package com.vng.app.mobilelegendsitembuilds.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.databinding.ItemStatsLayoutBinding;

import java.util.ArrayList;

/**
 * Created by admin on 4/28/2017.
 */

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> statLabel = new ArrayList<>();
    private ArrayList<String> statValue = new ArrayList<>();

    public StatsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setValues(ArrayList<String> statLabel, ArrayList<String> statValue){
        this.statLabel = statLabel;
        this.statValue = statValue;
    }

    public void addValue(String statLabel, String statValue){
        this.statLabel.add(statLabel);
        this.statValue.add(statValue);
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(mContext),
                        R.layout.item_stats_layout,
                        parent,
                        false).getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemStatsLayoutBinding binder = DataBindingUtil.getBinding(holder.itemView);
        binder.textviewStatsLabel.setText(statLabel.get(position));
        binder.textviewStatsValue.setText(statValue.get(position));
    }



    @Override
    public int getItemCount() {
        return statValue.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }




}
