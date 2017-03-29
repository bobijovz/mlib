package com.vng.app.mobilelegendsitembuilds.fragment;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;

import java.io.File;
import java.sql.Array;
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
    private int selectedItemPosition = 0;
    private ImageAdapter adapter;
    private ArrayList<String> itemTypes = new ArrayList<>();
    private ArrayList<Item> itemBuildSet = new ArrayList<>(5);


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
        for (Item item: items){
            if (!itemTypes.contains(item.getType())){
                itemTypes.add(item.getType());
            }
        }
        for (int i = 0; i < 5; i++) {
            itemBuildSet.add(new Item());
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
        binder.recyclerviewItemList.setLayoutManager(new GridLayoutManager(getContext(),4));
        binder.recyclerviewItemList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //binder.spinnerItemType.setAdapter();



    }

    public void onItemSelected(int i){
        selectedItemPosition = i;
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
        setBuildCollection(selectedItemPosition, item);
        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), item.getName().concat(".png")))
                .fit()
                .centerCrop()
                .into(selectedItemSlot);
        binder.linearlayoutItemDetails.removeAllViews();
        TextView tv = new TextView(getContext());
        tv.setText(item.getName());
        tv.setGravity(Gravity.CENTER);

        tv.setTypeface(Typeface.DEFAULT_BOLD);
        binder.linearlayoutItemDetails.addView(tv);

        if (!item.getAbility_crit_rate().contentEquals("0")){
            addDetails("CRIT RATE :".concat(item.getAbility_crit_rate()));
        }
        if (!item.getArmor().contentEquals("0")){
            addDetails("ARMOR :".concat(item.getArmor()));
        }
        if (!item.getAttack_speed().contentEquals("0")){
            addDetails("ATK SPEED :".concat(item.getAttack_speed()));
        }
        if (!item.getBasic_attack_crit_rate().contentEquals("0")){
            addDetails("ATK CRIT:".concat(item.getBasic_attack_crit_rate()));
        }
        if (!item.getCooldown_reduction().contentEquals("0")){
            addDetails("COOLDOWN RDCT :".concat(item.getCooldown_reduction()));
        }
        if (!item.getCrit_reduction().contentEquals("0")){
            addDetails("CRIT RDCT :".concat(item.getCrit_reduction()));
        }
        if (!item.getDamage_to_monsters().contentEquals("0")){
            addDetails("DMG TO MONSTER :".concat(item.getDamage_to_monsters()));
        }
        if (!item.getHp().contentEquals("0")){
            addDetails("HP :".concat(item.getHp()));
        }
        if (!item.getHp_regen().contentEquals("0")){
            addDetails("HP REGEN :".concat(item.getHp_regen()));
        }
        if (!item.getLifesteal().contentEquals("0")){
            addDetails("LIFESTEAL :".concat(item.getLifesteal()));
        }
        if (!item.getMagic_penetration().contentEquals("0")){
            addDetails("MAGIC PNTRTN :".concat(item.getMagic_penetration()));
        }
        if (!item.getMagic_power().contentEquals("0")){
            addDetails("MAGIC POW :".concat(item.getMagic_power()));
        }
        if (!item.getMagic_resistance().contentEquals("0")){
            addDetails("MAGIC RESIST :".concat(item.getMagic_resistance()));
        }
        if (!item.getMana().contentEquals("0")){
            addDetails("MANA :".concat(item.getMana()));
        }
        if (!item.getMana_regen().contentEquals("0")){
            addDetails("MANA REGEN :".concat(item.getMana_regen()));
        }
        if (!item.getMovement_speed().contentEquals("0")){
            addDetails("MOVE SPEED :".concat(item.getMovement_speed()));
        }
        if (!item.getPhysical_attack().contentEquals("0")){
            addDetails("PHY ATK :".concat(item.getPhysical_attack()));
        }
        if (!item.getPhysical_penetration().contentEquals("0")){
            addDetails("PHY PNTRTN :".concat(item.getPhysical_penetration()));
        }
        if (!item.getResilience().contentEquals("0")){
            addDetails("RESILIENCE :".concat(item.getResilience()));
        }
        if (!item.getSpell_vamp().contentEquals("0")){
            addDetails("SPELL VAMP :".concat(item.getSpell_vamp()));
        }
        if (!item.getType().contentEquals("")){
            addDetails("TYPE :".concat(item.getType()));
        }
        if (!item.getPassive().contentEquals("n/a")){
            addDetails("PASSIVE :".concat(item.getPassive()));
        }
        if (!item.getCost().contentEquals("0")){
            addDetails("COST :".concat(item.getCost()));
        }
    }

    public void addDetails(String detail){
        TextView temp = new TextView(getContext());
        temp.setText(detail);
        temp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        binder.linearlayoutItemDetails.addView(temp);
    }
    
    public void setBuildCollection(int position, Item item){
        itemBuildSet.set(position, item);
        
        for (Item temp:itemBuildSet){
            if (temp.getAbility_crit_rate() != null){
                setComputation(temp.getAbility_crit_rate(),binder.tvAbilityCrit);
            }
            if (temp.getArmor() != null){
                setComputation(temp.getArmor(),binder.tvArmor);
            }
            if (temp.getAttack_speed() != null){
                setComputation(temp.getAttack_speed(),binder.tvAttackSpeed);

            }
            if (temp.getBasic_attack_crit_rate() != null){
                setComputation(temp.getBasic_attack_crit_rate(),binder.tvAttackCrit);

            }
            if (temp.getHp() != null){
                setComputation(temp.getHp(),binder.tvHealth);

            }
            if (temp.getHp_regen() != null){
                setComputation(temp.getHp_regen(),binder.tvHpRegen);

            }

            if (temp.getMagic_power() != null){
                setComputation(temp.getMagic_power(),binder.tvMagicPower);

            }
            if (temp.getMagic_resistance() != null){
                setComputation(temp.getMagic_resistance(),binder.tvMagicResist);

            }
            if (temp.getMana() != null){
                setComputation(temp.getMana(),binder.tvMana);

            }
            if (temp.getMana_regen() != null){
                setComputation(temp.getMana_regen(),binder.tvManaRegen);

            }
            if (temp.getMovement_speed() != null){
                setComputation(temp.getMovement_speed(),binder.tvMoveSpeed);

            }
            if (temp.getPhysical_attack() != null){
                setComputation(temp.getPhysical_attack(),binder.tvPhysicalAttack);

            }

            /*if (temp.getCooldown_reduction() != null){
                setComputation(temp.getCooldown_reduction(),binder.tv);

            }
            if (temp.getCrit_reduction() != null){
                setComputation(temp.getCrit_reduction(),binder.tvAttackCrit);

            }
            if (temp.getDamage_to_monsters() != null){
                setComputation(temp.getDamage_to_monsters(),binder.tvAttackCrit);

            }*/
            /*if (temp.getLifesteal() != null){
                setComputation(temp.getLifesteal(),binder.li);

            }*/
            /*if (temp.getMagic_penetration() != null){
                setComputation(temp.getMagic_penetration(),binder.tvAttackCrit);

            }*/
           /* if (temp.getPhysical_penetration() != null){
                setComputation(temp.getPhysical_penetration(),binder.tvAttackCrit);

            }*/
            /*if (temp.getResilience() != null){
                setComputation(temp.getResilience(),binder.tvAttackCrit);

            }*/
            /*if (temp.getSpell_vamp() != null){
                setComputation(temp.getSpell_vamp(),binder.tvAttackCrit);

            }*/
           /* if (!item.getCost() != null){
                 setComputation(temp.getCost(),binder.tvAttackCrit);
            }*/
        }
    }

    public void setComputation(String d, TextView tv){
        double i = Double.parseDouble(tv.getText().toString());
        tv.setText(String.valueOf(i + Double.parseDouble(d)));
    }
}
