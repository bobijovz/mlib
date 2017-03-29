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
        
        Hero stats = hero;
        
        for (Item temp:itemBuildSet){
            if (temp.getAbility_crit_rate() != null){
                long t = stats.getAbility_crit_rate() + Long.parseLong(temp.getAbility_crit_rate()) ;
                stats.setAbility_crit_rate(t);
                binder.tvAbilityCrit.setText(String.valueOf(t));
            }
            if (temp.getArmor() != null){
                long t = stats.getArmor() + Long.parseLong(temp.getArmor()) ;
                stats.setArmor(t);
                binder.tvArmor.setText(String.valueOf(t));
            }
            if (temp.getAttack_speed() != null){
                long t = stats.getAttack_speed() + Long.parseLong(temp.getAttack_speed()) ;
                stats.setAttack_speed(t);
                binder.tvAttackSpeed.setText(String.valueOf(t));

            }
            if (temp.getBasic_attack_crit_rate() != null){
                long t = stats.getBasic_attack_crit_rate() + Long.parseLong(temp.getBasic_attack_crit_rate()) ;
                stats.setBasic_attack_crit_rate(t);

                binder.tvAttackCrit.setText(String.valueOf(t));

            }
            if (temp.getHp() != null){
                long t = stats.getHp() + Long.parseLong(temp.getHp()) ;
                stats.setHp(t);
               
                binder.tvHealth.setText(String.valueOf(t));

            }
            if (temp.getHp_regen() != null){
                long t = stats.getHp_regen() + Long.parseLong(temp.getHp_regen()) ;
                stats.setHp_regen(t);
                
                binder.tvHpRegen.setText(String.valueOf(t));

            }

            if (temp.getMagic_power() != null){
                long t = stats.getMagic_power() + Long.parseLong(temp.getMagic_power()) ;
                stats.setMagic_power(t);
                
                binder.tvMagicPower.setText(String.valueOf(t));

            }
            if (temp.getMagic_resistance() != null){
                long t = stats.getMagic_resistance() + Long.parseLong(temp.getMagic_resistance()) ;
                stats.setMagic_resistance(t);
                
                binder.tvMagicResist.setText(String.valueOf(t));

            }
            if (temp.getMana() != null){
                long t = stats.getMana() + Long.parseLong(temp.getMana()) ;
                stats.setMana(t);
                
                binder.tvMana.setText(String.valueOf(t));

            }
            if (temp.getMana_regen() != null){
                long t = stats.getMana_regen() + Long.parseLong(temp.getMana_regen()) ;
                stats.setMana_regen(t);
                
                binder.tvManaRegen.setText(String.valueOf(t));

            }
            if (temp.getMovement_speed() != null){
                long t = stats.getMovement_speed() + Long.parseLong(temp.getMovement_speed()) ;
                stats.setMovement_speed(t);
                
                binder.tvMoveSpeed.setText(String.valueOf(t));

            }
            if (temp.getPhysical_attack() != null){
                long t = stats.getPhysical_attack() + Long.parseLong(temp.getPhysical_attack()) ;
                stats.setPhysical_attack(t);
                
                binder.tvPhysicalAttack.setText(String.valueOf(t));

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
        stats = null;
    }
}
