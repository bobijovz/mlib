package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentShareBuildBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;

import java.util.ArrayList;

/**
 * Created by Nico on 3/24/2017.
 */

public class ShareBuildFragment extends Fragment {

    private FragmentShareBuildBinding binder;
    private ShareBuildFragment fragment;
    private ArrayList<Hero> heros = new ArrayList<>();

    public ShareBuildFragment newInstance(ArrayList<Hero> heros) {
        fragment = new ShareBuildFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("HERO_LIST", heros);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_share_build, container, false);
        if (getArguments() != null) {
            heros = getArguments().getParcelableArrayList("HERO_LIST");
            ArrayAdapter<Hero> hero = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, heros);
            hero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binder.spinnerHeroes.setAdapter(hero);
            binder.spinnerHeroes.setSelection(0);
        }
        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
