package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentHeroListBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.util.ArrayList;


/**
 * Created by Nico on 3/24/2017.
 */

public class HeroListFragment extends Fragment implements ImageAdapter.HeroAdapterListener {

    private FragmentHeroListBinding binder;
    private HeroListFragment fragment;
    private ArrayList<Hero> heros = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ImageAdapter adapter;
    ImageAdapter.HeroAdapterListener heroListener;

    public HeroListFragment newInstance(ArrayList<Hero> heros, ArrayList<Item> items) {
        fragment = new HeroListFragment();
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
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_hero_list, container, false);

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        heroListener = this;
        binder.spinnerHeroType.setSelection(0);
        binder.spinnerHeroType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Hero> test = new ArrayList<>();
                adapter = new ImageAdapter(getContext());
                if (binder.spinnerHeroType.getSelectedItem().toString().equals("All")) {
                    adapter.setHeros(heros);
                } else {
                    for (int i = 0; i < heros.size(); i++) {
                        if (heros.get(i).getRole().contains(binder.spinnerHeroType.getSelectedItem().toString())) {
                            test.add(heros.get(i));
                        }
                    }
                    adapter.setHeros(test);
                }
                adapter.setListener(heroListener);
                binder.recyclerviewHeroList.setLayoutManager(new GridLayoutManager(getContext(), 5));
                binder.recyclerviewHeroList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onHeroPick(Bundle data, View view) {
        Bundle newData = data;
        newData.putParcelableArrayList("ITEMS", items);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition changeTransform = TransitionInflater.from(getContext()).
                    inflateTransition(R.transition.change_image_transform);
            Transition explodeTransform = TransitionInflater.from(getContext()).
                    inflateTransition(android.R.transition.explode);

            // Setup exit transition on first fragment
            this.setSharedElementReturnTransition(changeTransform);
            this.setExitTransition(explodeTransform);


            ItemBuilderFragment builderFragment = new ItemBuilderFragment().newInstance(newData);
            // Setup enter transition on second fragment
            builderFragment.setSharedElementEnterTransition(changeTransform);
            builderFragment.setEnterTransition(explodeTransform);

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, builderFragment)
                    .addToBackStack("item_builder")
                    .addSharedElement(view, "hero_image");
            ft.commit();
        } else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, new ItemBuilderFragment().newInstance(newData));
            transaction.commit();
        }
    }
}
