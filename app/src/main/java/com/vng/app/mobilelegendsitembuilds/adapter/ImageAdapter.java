package com.vng.app.mobilelegendsitembuilds.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private OnItemClickListener onItemClickListener;

    public interface ImageAdapterListener {
        void onHeroPick(Bundle data, View view);
    }

    public interface OnItemClickListener {
        void onItemPick(int position);
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(ImageAdapterListener listener) {
        this.listener = listener;
    }

    public void setItemListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

        if (heros != null && heros.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                binder.imageviewItem.setTransitionName("hero_image" + position);
            Picasso.with(mContext)
                    .load(new File(mContext.getFilesDir(), heros.get(position).getName().concat(".png")))
                    .fit()
                    .centerCrop()
                    .into(binder.imageviewItem);
        } else {
            Picasso.with(mContext)
                    .load(new File(mContext.getFilesDir(), items.get(position).getName().concat(".png")))
                    .into(binder.imageviewItem);
        }
    }

    @Override
    public int getItemCount() {
        return heros != null && heros.size() > 0 ? heros.size() : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemHeroGridLayoutBinding binder;

        public ViewHolder(View itemView) {
            super(itemView);
            binder = DataBindingUtil.getBinding(itemView);
            binder.imageviewItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (heros != null && heros.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putString("transitionName", "hero_image" + getAdapterPosition());
                bundle.putParcelable("HERO", heros.get(getAdapterPosition()));
                listener.onHeroPick(bundle, view);
            } else if (items != null && items.size() > 0) {
                Toast.makeText(mContext, items.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("ITEM", items.get(getAdapterPosition()));
                onItemClickListener.onItemPick(getAdapterPosition());
            }

        }
    }
}