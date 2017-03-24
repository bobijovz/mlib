package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.util.ArrayList;


/**
 * Created by Nico on 3/24/2017.
 */

public class ItemBuilderFragment extends Fragment {

    private FragmentItemBuilderBinding binder;
    private ArrayList<Hero> heros = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();


    public ItemBuilderFragment newInstance(ArrayList<Hero> heros, ArrayList<Item> items) {
        ItemBuilderFragment fragment = new ItemBuilderFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("HERO_LIST", heros);
        b.putParcelableArrayList("ITEMS", items);
        fragment.setArguments(b);
        return fragment;
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
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_item_builder, container, false);

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            ImageAdapter adapter = new ImageAdapter(getContext());
            adapter.setItems(items);
            //adapter.setHeros(heros);

            binder.recyclerviewHeroList.setLayoutManager(new GridLayoutManager(getContext(),5));
            binder.recyclerviewHeroList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.d("SIZE", String.valueOf(adapter.getItemCount()));

    }
}
