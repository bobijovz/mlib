package com.vng.app.mobilelegendsitembuilds.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.ItemGridLayoutBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jovijovs on 3/24/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private ArrayList<Hero> heros = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setHeros( ArrayList<Hero> heros){
        this.heros = heros;
    }

    public void setItems(ArrayList<Item> items){
        this.items = items;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemGridLayoutBinding binder =
                DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_grid_layout,parent,false);


        return new ViewHolder(binder.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemGridLayoutBinding binder = DataBindingUtil.getBinding(holder.itemView);
        if (heros != null)
        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(),heros.get(position).getName().concat(".png")))

                .into(binder.imageviewItem);
        else if (items != null)
            Picasso.with(mContext)
                    .load(new File(mContext.getFilesDir(),items.get(position).getName().concat(".jpg")))
                    .fit()
                    .into(binder.imageviewItem);
    }

    @Override
    public int getItemCount() {
        return heros != null ? heros.size() : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}