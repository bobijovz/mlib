package com.vng.app.mobilelegendsitembuilds.fragment;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ImageView img;
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
        img = (ImageView) binder.getRoot().findViewById(R.id.imageview_item);
        Log.d("TRANSITION",transition_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setTransitionName(transition_name);
        }
        binder.tvHeroName.setText(hero.getName());
        binder.tvRole.setText(hero.getRole());
        binder.tvSpecialty.setText(hero.getSpecialty());
        binder.tvHealth.setText(String.valueOf(hero.getHp()));
        binder.tvMana.setText(String.valueOf(hero.getMana()));
        binder.tvAbilityCrit.setText(String.valueOf(hero.getAbility_crit_rate()));
        binder.tvArmor.setText(String.valueOf(hero.getArmor()));
        binder.tvAttackSpeed.setText(String.valueOf(hero.getAttack_speed()));
        binder.tvAttackCrit.setText(String.valueOf(hero.getBasic_attack_crit_rate()));
        binder.tvHpRegen.setText(String.valueOf(hero.getHp_regen()));
        binder.tvMagicPower.setText(String.valueOf(hero.getMagic_power()));
        binder.tvMagicResist.setText(String.valueOf(hero.getMagic_resistance()));
        binder.tvManaRegen.setText(String.valueOf(hero.getMana_regen()));
        binder.tvMoveSpeed.setText(String.valueOf(hero.getMovement_speed()));
        binder.tvPhysicalAttack.setText(String.valueOf(hero.getPhysical_attack()));

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
