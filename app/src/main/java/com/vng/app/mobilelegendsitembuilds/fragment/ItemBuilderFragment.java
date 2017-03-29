package com.vng.app.mobilelegendsitembuilds.fragment;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jovijovs on 3/27/2017.
 */

public class ItemBuilderFragment extends Fragment implements View.OnClickListener, ImageAdapter.ItemAdapterListener {

    private Hero hero;
    private ArrayList<Item> items = new ArrayList<>();
    private FragmentItemBuilderBinding binder;
    private String transition_name = "";
    private CircleImageView selectedItemSlot;
    private ImageAdapter adapter;


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
            items = getArguments().getParcelableArrayList("ITEMS");
            transition_name = getArguments().getString("transitionName");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binder.imageviewItem.setTransitionName(transition_name);
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
        binder.imageCurrentItem1.setOnClickListener(this);
        binder.imageCurrentItem2.setOnClickListener(this);
        binder.imageCurrentItem3.setOnClickListener(this);
        binder.imageCurrentItem4.setOnClickListener(this);
        binder.imageCurrentItem5.setOnClickListener(this);
        binder.imageCurrentItem6.setOnClickListener(this);

        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), hero.getName().concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageviewItem);

        onItemSelected(0);

        adapter = new ImageAdapter(getContext());
        adapter.setItems(items);
        adapter.setListener(this);
        binder.recyclerviewItemList.setLayoutManager(new GridLayoutManager(getContext(),6));
        binder.recyclerviewItemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void onItemSelected(int i){

        for (int j = 0 ; j < binder.linearlayoutItemBuildContainer.getChildCount(); j++){
            CircleImageView img = (CircleImageView) binder.linearlayoutItemBuildContainer.getChildAt(j);
            if (j == i){
                selectedItemSlot = img;
                img.setBorderColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            } else {
                img.setBorderColor(ContextCompat.getColor(getContext(),android.R.color.white));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_current_item1:
                onItemSelected(0);
                break;
            case R.id.image_current_item2:
                onItemSelected(1);
                break;
            case R.id.image_current_item3:
                onItemSelected(2);
                break;
            case R.id.image_current_item4:
                onItemSelected(3);
                break;
            case R.id.image_current_item5:
                onItemSelected(4);
                break;
            case R.id.image_current_item6:
                onItemSelected(5);
                break;
        }
    }

    @Override
    public void onItemPick(Item item) {
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), item.getName().concat(".png")))
                .fit()
                .centerCrop()
                .into(selectedItemSlot);
    }
}
