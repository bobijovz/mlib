package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;

import java.io.File;

/**
 * Created by jovijovs on 3/27/2017.
 */

public class ItemBuilderFragment extends Fragment {

    private Hero hero;
    private FragmentItemBuilderBinding binder;
    private String transition_name = "";

    public ItemBuilderFragment() {
    }

    public ItemBuilderFragment newInstance(Bundle bundle) {
        ItemBuilderFragment fragment = new ItemBuilderFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hero = getArguments().getParcelable("HERO");
            transition_name = getArguments().getString("transitionName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_item_builder, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binder.imageviewItem.setTransitionName(transition_name);
        }
        binder.tvHeroName.setText(hero.getName());
        binder.tvRole.setText(hero.getRole());
        binder.tvSpecialty.setText(hero.getSpecialty());
        binder.tvHealth.setText(String.valueOf(hero.getHp()));
        binder.tvMana.setText(String.valueOf(hero.getMana()));
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), hero.getName().concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageviewItem);

        return binder.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
