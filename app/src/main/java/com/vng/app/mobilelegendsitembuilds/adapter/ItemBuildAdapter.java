package com.vng.app.mobilelegendsitembuilds.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.ItemBuildLayoutBinding;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 4/28/2017.
 */

public class ItemBuildAdapter extends RecyclerView.Adapter<ItemBuildAdapter.ViewHolder>  {
    private Context mContext;
    private ArrayList<String> buildTitles = new ArrayList<>();
    private ArrayList<String> buildItems = new ArrayList<>();

    public ItemBuildAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(ArrayList<String> buildTitles, ArrayList<String> buildItems){
        this.buildTitles = buildTitles;
        this.buildItems = buildItems;
        notifyDataSetChanged();
    }

    public void addData(String buildTitle, String buildItem){
        this.buildTitles.add(buildTitle);
        this.buildItems.add(buildItem);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemBuildAdapter.ViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(mContext),
                        R.layout.item_build_layout,
                        parent,
                        false).getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemBuildLayoutBinding binder = DataBindingUtil.getBinding(holder.itemView);
        String[] items = buildItems.get(position).split(",");
        binder.textBuildTitle.setText(buildTitles.get(position));
        //TODO check if build was created by user, hide editbutton if not
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[0].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem1);
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[1].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem2);
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[2].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem3);
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[3].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem4);
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[4].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem5);
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), items[5].concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageCurrentItem6);

    }

    @Override
    public int getItemCount() {
        return buildTitles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ViewHolder(View itemView) {
            super(itemView);
            ((ItemBuildLayoutBinding)DataBindingUtil.getBinding(itemView))
                    .imagebuttonEditBuild.setOnClickListener(this);
            ((ItemBuildLayoutBinding)DataBindingUtil.getBinding(itemView))
                    .imagebuttonShareBuild.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imagebutton_edit_build:
                    //TODO edit button
                    break;
                case R.id.imagebutton_share_build:
                    //TODO share button
                    break;
            }
        }
    }
}
