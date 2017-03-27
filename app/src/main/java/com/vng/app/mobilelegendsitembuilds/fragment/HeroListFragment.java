package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentHeroListBinding;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.util.ArrayList;


/**
 * Created by Nico on 3/24/2017.
 */

public class HeroListFragment extends Fragment {

    private FragmentHeroListBinding binder;
    private ArrayList<Hero> heros = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ImageAdapter adapter;
    private ImageAdapter.ImageAdapterListener listener;



    public HeroListFragment newInstance(ArrayList<Hero> heros, ArrayList<Item> items) {
        HeroListFragment fragment = new HeroListFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("HERO_LIST", heros);
        b.putParcelableArrayList("ITEMS", items);
        fragment.setArguments(b);
        return fragment;
    }

    public void setAdapterListener(ImageAdapter.ImageAdapterListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            heros = getArguments().getParcelableArrayList("HERO_LIST");
            items = getArguments().getParcelableArrayList("ITEMS");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_hero_list, container, false);

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            adapter = new ImageAdapter(getContext());
            //adapter.setItems(items);
            adapter.setHeros(heros);

            binder.recyclerviewHeroList.setLayoutManager(new GridLayoutManager(getContext(),5));
            binder.recyclerviewHeroList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setListener(listener);


    }


}
