package com.vng.app.mobilelegendsitembuilds.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;

import com.vng.app.mobilelegendsitembuilds.databinding.ItemHeroGridLayoutBinding;
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
    private ImageAdapterListener listener;

    public interface ImageAdapterListener {
        void onHeroPick(Bundle data, View view);
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(ImageAdapterListener listener) {
        this.listener = listener;
    }

    public void setHeros(ArrayList<Hero> heros) {
        this.heros = heros;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHeroGridLayoutBinding binder =
                DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_hero_grid_layout, parent, false);


        return new ViewHolder(binder.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemHeroGridLayoutBinding binder = DataBindingUtil.getBinding(holder.itemView);
        String path = heros != null && heros.size() > 0 ? heros.get(position).getName().concat(".png") : items.get(position).getName().concat(".png");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.img.setTransitionName("hero_image" + position);

        Picasso.with(mContext)
                .load(new File(mContext.getFilesDir(), path))
                .fit()
                .centerCrop()
                .into(binder.imageviewItem);
    }

    @Override
    public int getItemCount() {
        return heros != null && heros.size() > 0 ? heros.size() : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemHeroGridLayoutBinding binder;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            binder = DataBindingUtil.getBinding(itemView);
            binder.imageviewItem.setOnClickListener(this);
            img = (ImageView) itemView.findViewById(R.id.imageview_item);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("transitionName", "hero_image" + getAdapterPosition());
            bundle.putParcelable("HERO", heros.get(getAdapterPosition()));
            listener.onHeroPick(bundle, img);
        }
    }
}