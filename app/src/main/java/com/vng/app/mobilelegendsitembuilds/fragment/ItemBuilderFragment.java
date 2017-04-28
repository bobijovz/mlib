package com.vng.app.mobilelegendsitembuilds.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vng.app.mobilelegendsitembuilds.Constant;
import com.vng.app.mobilelegendsitembuilds.R;
import com.vng.app.mobilelegendsitembuilds.adapter.ImageAdapter;
import com.vng.app.mobilelegendsitembuilds.adapter.StatsAdapter;
import com.vng.app.mobilelegendsitembuilds.databinding.FragmentItemBuilderBinding;
import com.vng.app.mobilelegendsitembuilds.model.Hero;
import com.vng.app.mobilelegendsitembuilds.model.Item;
import com.vng.app.mobilelegendsitembuilds.service.FloatingViewService;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

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
    private ArrayList<Item> itemBuildSet = new ArrayList<>();
    private StatsAdapter statsAdapter;
    private ImageAdapter.ItemAdapterListener itemListener;
    private SharedPreferences sharedPreferences;

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
        for (Item item : items) {
            if (!itemTypes.contains(item.getType())) {
                itemTypes.add(item.getType());
            }
        }
        for (int i = 0; i < 6; i++) {
            itemBuildSet.add(new Item());
        }
        binder.tvHeroName.setText(hero.getName());
        binder.tvRole.setText(hero.getRole());
        binder.tvSpecialty.setText(hero.getSpecialty());

        setHeroStats(hero, new Hero());
        binder.imageCurrentItem1.setOnClickListener(this);
        binder.imageCurrentItem2.setOnClickListener(this);
        binder.imageCurrentItem3.setOnClickListener(this);
        binder.imageCurrentItem4.setOnClickListener(this);
        binder.imageCurrentItem5.setOnClickListener(this);
        binder.imageCurrentItem6.setOnClickListener(this);
        binder.buttonUseBuild.setOnClickListener(this);
        binder.buttonOpenBuild.setOnClickListener(this);
        sharedPreferences = getContext().getSharedPreferences(Constant.MyPREFERENCES, Context.MODE_PRIVATE);

        /*binder.textLabelAbilityCrit.setOnClickListener(this);
        binder.textLabelArmor.setOnClickListener(this);
        binder.textLabelAttackSpeed.setOnClickListener(this);
        binder.textLabelAttackCrit.setOnClickListener(this);
        binder.textLabelHpRegen.setOnClickListener(this);
        binder.textLabelMagicPower.setOnClickListener(this);
        binder.textLabelMagicResistance.setOnClickListener(this);
        binder.textLabelManaRegen.setOnClickListener(this);
        binder.textLabelMovementSpeed.setOnClickListener(this);
        binder.textLabelPhysicalAttack.setOnClickListener(this);
        binder.textLabelCooldownReduction.setOnClickListener(this);
        binder.textLabelCritReduction.setOnClickListener(this);
        binder.textLabelDamageToMonsters.setOnClickListener(this);
        binder.textLabelLifesteal.setOnClickListener(this);
        binder.textLabelMagicPenetration.setOnClickListener(this);
        binder.textLabelPhysicalPenetration.setOnClickListener(this);
        binder.textLabelResilience.setOnClickListener(this);
        binder.textLabelSpellVamp.setOnClickListener(this);
        binder.textLabelCost.setOnClickListener(this);*/

        Picasso.with(getContext())
                .load(new File(getContext().getFilesDir(), hero.getName().concat(".png")))
                .fit()
                .centerCrop()
                .into(binder.imageviewItem);

        onItemSelected(0);
        itemListener = this;

        binder.spinnerItemType.setSelection(0);
        binder.spinnerItemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Item> test = new ArrayList<>();
                adapter = new ImageAdapter(getContext());
                if (binder.spinnerItemType.getSelectedItem().toString().equals("All")) {
                    adapter.setItems(items);
                } else {
                    for (int i = 0; i < items.size(); i++) {
                        if (items.get(i).getType().equals(binder.spinnerItemType.getSelectedItem().toString())) {
                            test.add(items.get(i));
                        }
                    }
                    adapter.setItems(test);
                }
                adapter.setListener(itemListener);
                binder.recyclerviewItemList.setLayoutManager(new GridLayoutManager(getContext(), 4));
                binder.recyclerviewItemList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setHeroStats(Hero hero, Hero item) {
        binder.tvHealth.setText(String.valueOf(hero.getHp() + item.getHp()));
        binder.tvMana.setText(String.valueOf(hero.getMana() + item.getMana()));
        statsAdapter = new StatsAdapter(getContext());

        if (hero.getAbility_crit_rate() + item.getAbility_crit_rate() != 0)
            statsAdapter.addValue("Ability Crit :", String.valueOf(hero.getAbility_crit_rate() + item.getAbility_crit_rate()));
        if (hero.getArmor() + item.getArmor() != 0)
            statsAdapter.addValue("Armor :", String.valueOf(hero.getArmor() + item.getArmor()));
        if (hero.getAttack_speed() + item.getAttack_speed() != 0)
            statsAdapter.addValue("Attack Speed :", String.valueOf(hero.getAttack_speed() + item.getAttack_speed()));
        if (hero.getBasic_attack_crit_rate() + item.getBasic_attack_crit_rate() != 0)
            statsAdapter.addValue("Attack Crit :", String.valueOf(hero.getBasic_attack_crit_rate() + item.getBasic_attack_crit_rate()));
        if (hero.getHp_regen() + item.getHp_regen() != 0)
            statsAdapter.addValue("Hp Regen :", String.valueOf(hero.getHp_regen() + item.getHp_regen()));
        if (hero.getMagic_power() + item.getMagic_power() != 0)
            statsAdapter.addValue("Magic Power :", String.valueOf(hero.getMagic_power() + item.getMagic_power()));
        if (hero.getMagic_resistance() + item.getMagic_resistance() != 0)
            statsAdapter.addValue("Magic Resist :", String.valueOf(hero.getMagic_resistance() + item.getMagic_resistance()));
        if (hero.getMana_regen() + item.getMana_regen() != 0)
            statsAdapter.addValue("Mana Regen :", String.valueOf(hero.getMana_regen() + item.getMana_regen()));
        if (hero.getMovement_speed() + item.getMovement_speed() != 0)
            statsAdapter.addValue("Move Speed :", String.valueOf(hero.getMovement_speed() + item.getMovement_speed()));
        if (hero.getPhysical_attack() + item.getPhysical_attack() != 0)
            statsAdapter.addValue("Physical Atk :", String.valueOf(hero.getPhysical_attack() + item.getPhysical_attack()));
        if (hero.getCooldown_reduction() + item.getCooldown_reduction() != 0)
            statsAdapter.addValue("Cooldown Rdc :", String.valueOf(hero.getCooldown_reduction() + item.getCooldown_reduction()));
        if (hero.getCrit_reduction() + item.getCrit_reduction() != 0)
            statsAdapter.addValue("Crit Reduct :", String.valueOf(hero.getCrit_reduction() + item.getCrit_reduction()));
        if (hero.getDamage_to_monsters() + item.getDamage_to_monsters() != 0)
            statsAdapter.addValue("Dmg Monster :", String.valueOf(hero.getDamage_to_monsters() + item.getDamage_to_monsters()));
        if (hero.getLifesteal() + item.getLifesteal() != 0)
            statsAdapter.addValue("Lifesteal :", String.valueOf(hero.getLifesteal() + item.getLifesteal()));
        if (hero.getMagic_penetration() + item.getMagic_penetration() != 0)
            statsAdapter.addValue("M Penetarte :", String.valueOf(hero.getMagic_penetration() + item.getMagic_penetration()));
        if (hero.getPhysical_penetration() + item.getPhysical_penetration() != 0)
            statsAdapter.addValue("P Penetrate :", String.valueOf(hero.getPhysical_penetration() + item.getPhysical_penetration()));
        if (hero.getResilience() + item.getResilience() != 0)
            statsAdapter.addValue("Resilience :", String.valueOf(hero.getResilience() + item.getResilience()));
        if (hero.getSpell_vamp() + item.getSpell_vamp() != 0)
            statsAdapter.addValue("Spell Vamp :", String.valueOf(hero.getSpell_vamp() + item.getSpell_vamp()));
        if (hero.getCost() + item.getCost() != 0)
            statsAdapter.addValue("Total Cost :", String.valueOf(hero.getCost() + item.getCost()));

        binder.gridlayoutStatsContainer.setAdapter(statsAdapter);
        binder.gridlayoutStatsContainer.setLayoutManager(new GridLayoutManager(getContext(), 3));
        statsAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(int i) {
        selectedItemPosition = i;
        for (int j = 0; j < binder.linearlayoutItemBuildContainer.getChildCount(); j++) {
            CircleImageView img = (CircleImageView) binder.linearlayoutItemBuildContainer.getChildAt(j);
            if (j == i) {
                selectedItemSlot = img;
                img.setBorderColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            } else {
                img.setBorderColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

            case R.id.button_open_build:
                if (itemBuildSet.size() == 6) {
                    Intent i = new Intent(getActivity(), FloatingViewService.class);
                    i.putParcelableArrayListExtra("BUILD_SET", itemBuildSet);
                    getActivity().startService(i);
                } else {
                    Toast.makeText(getContext(), "Error, Item build incomplete!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_use_build:
                if (itemBuildSet.size() == 6) {

                    //TODO save item build in firebase
                    //1. Ask build unique title
                    //2. Save in firebase use this json format as ref :
                    //builds {
                    //      userid123 : {
                    //          build_name123: {
                    //              name : buildename123,
                    //              items : {item array here},
                    //              public : true/false(Optional nalang kung gusto mo lagyan neto)
                    //          },
                    //          build_name1234: {
                    //              name: buildname1234,
                    //              items : {item array here},
                    //              public : false(Optional nalang kung gusto mo lagyan neto)
                    //          }
                    //      },
                    //      userid321: {.........}
                    //}

                    //TEMPORARY SHARED PREF SAVING
                    String buildSet = "";
                    for (Item item : itemBuildSet) {
                        buildSet += item.getName().concat(",");
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constant.BuildOneValues, buildSet);
                    editor.apply();
                } else {
                    Toast.makeText(getContext(), "Error, Item build incomplete!", Toast.LENGTH_SHORT).show();
                }


                break;

            /*case R.id.text_label_ability_crit:
                Toast.makeText(getContext(), "Ability Crit", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_armor:
                Toast.makeText(getContext(), "Armor", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_attack_speed:
                Toast.makeText(getContext(), "Attack Speed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_attack_crit:
                Toast.makeText(getContext(), "Attack Crit", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_hp_regen:
                Toast.makeText(getContext(), "HP REGEN", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_magic_power:
                Toast.makeText(getContext(), "MAGIC POWER", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_magic_resistance:
                Toast.makeText(getContext(), "MAGIC RESISTANCE", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_mana_regen:
                Toast.makeText(getContext(), "MANA REGEN", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_movement_speed:
                Toast.makeText(getContext(), "MOVEMENT SPEED", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_physical_attack:
                Toast.makeText(getContext(), "PHYSICAL ATTACK", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_cooldown_reduction:
                Toast.makeText(getContext(), "COOLDOWN REDUCTION", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_crit_reduction:
                Toast.makeText(getContext(), "CRIT REDUCTION", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_damage_to_monsters:
                Toast.makeText(getContext(), "DAMAGE TO MONSTERS", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_lifesteal:
                Toast.makeText(getContext(), "LIFESTEAL", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_magic_penetration:
                Toast.makeText(getContext(), "MAGIC PENETRATION", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_physical_penetration:
                Toast.makeText(getContext(), "PHYSICAL PENETRATION", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_resilience:
                Toast.makeText(getContext(), "RESILIENCE", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_spell_vamp:
                Toast.makeText(getContext(), "SPELL VAMP", Toast.LENGTH_SHORT).show();
                break;

            case R.id.text_label_cost:
                Toast.makeText(getContext(), "TOTAL COST", Toast.LENGTH_SHORT).show();
                break;*/

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

        if (!item.getAbility_crit_rate().contentEquals("0")) {
            addDetails("CRIT RATE :".concat(item.getAbility_crit_rate()));
        }
        if (!item.getArmor().contentEquals("0")) {
            addDetails("ARMOR :".concat(item.getArmor()));
        }
        if (!item.getAttack_speed().contentEquals("0")) {
            addDetails("ATK SPEED :".concat(item.getAttack_speed()));
        }
        if (!item.getBasic_attack_crit_rate().contentEquals("0")) {
            addDetails("ATK CRIT:".concat(item.getBasic_attack_crit_rate()));
        }
        if (!item.getCooldown_reduction().contentEquals("0")) {
            addDetails("COOLDOWN RDCT :".concat(item.getCooldown_reduction()));
        }
        if (!item.getCrit_reduction().contentEquals("0")) {
            addDetails("CRIT RDCT :".concat(item.getCrit_reduction()));
        }
        if (!item.getDamage_to_monsters().contentEquals("0")) {
            addDetails("DMG TO MONSTER :".concat(item.getDamage_to_monsters()));
        }
        if (!item.getHp().contentEquals("0")) {
            addDetails("HP :".concat(item.getHp()));
        }
        if (!item.getHp_regen().contentEquals("0")) {
            addDetails("HP REGEN :".concat(item.getHp_regen()));
        }
        if (!item.getLifesteal().contentEquals("0")) {
            addDetails("LIFESTEAL :".concat(item.getLifesteal()));
        }
        if (!item.getMagic_penetration().contentEquals("0")) {
            addDetails("MAGIC PNTRTN :".concat(item.getMagic_penetration()));
        }
        if (!item.getMagic_power().contentEquals("0")) {
            addDetails("MAGIC POW :".concat(item.getMagic_power()));
        }
        if (!item.getMagic_resistance().contentEquals("0")) {
            addDetails("MAGIC RESIST :".concat(item.getMagic_resistance()));
        }
        if (!item.getMana().contentEquals("0")) {
            addDetails("MANA :".concat(item.getMana()));
        }
        if (!item.getMana_regen().contentEquals("0")) {
            addDetails("MANA REGEN :".concat(item.getMana_regen()));
        }
        if (!item.getMovement_speed().contentEquals("0")) {
            addDetails("MOVE SPEED :".concat(item.getMovement_speed()));
        }
        if (!item.getPhysical_attack().contentEquals("0")) {
            addDetails("PHY ATK :".concat(item.getPhysical_attack()));
        }
        if (!item.getPhysical_penetration().contentEquals("0")) {
            addDetails("PHY PNTRTN :".concat(item.getPhysical_penetration()));
        }
        if (!item.getResilience().contentEquals("0")) {
            addDetails("RESILIENCE :".concat(item.getResilience()));
        }
        if (!item.getSpell_vamp().contentEquals("0")) {
            addDetails("SPELL VAMP :".concat(item.getSpell_vamp()));
        }
        if (!item.getType().contentEquals("")) {
            addDetails("TYPE :".concat(item.getType()));
        }
        if (!item.getPassive().contentEquals("n/a")) {
            addDetails("PASSIVE :".concat(item.getPassive()));
        }
        if (!item.getCost().contentEquals("0")) {
            addDetails("COST :".concat(item.getCost()));
        }
    }

    public void addDetails(String detail) {
        TextView temp = new TextView(getContext());
        temp.setText(detail);
        temp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        binder.linearlayoutItemDetails.addView(temp);
    }

    public void setBuildCollection(int position, Item item) {
        itemBuildSet.set(position, item);
        Hero stats = new Hero();
        for (Item temp : itemBuildSet) {
            if (temp.getAbility_crit_rate() != null) {
                long t = stats.getAbility_crit_rate() + Long.parseLong(temp.getAbility_crit_rate());
                stats.setAbility_crit_rate(t);
            }
            if (temp.getArmor() != null) {
                long t = stats.getArmor() + Long.parseLong(temp.getArmor());
                stats.setArmor(t);
            }
            if (temp.getAttack_speed() != null) {
                long t = stats.getAttack_speed() + Long.parseLong(temp.getAttack_speed());
                stats.setAttack_speed(t);
            }
            if (temp.getBasic_attack_crit_rate() != null) {
                long t = stats.getBasic_attack_crit_rate() + Long.parseLong(temp.getBasic_attack_crit_rate());
                stats.setBasic_attack_crit_rate(t);
            }
            if (temp.getHp() != null) {
                long t = stats.getHp() + Long.parseLong(temp.getHp());
                stats.setHp(t);
            }
            if (temp.getHp_regen() != null) {
                long t = stats.getHp_regen() + Long.parseLong(temp.getHp_regen());
                stats.setHp_regen(t);
            }
            if (temp.getMagic_power() != null) {
                long t = stats.getMagic_power() + Long.parseLong(temp.getMagic_power());
                stats.setMagic_power(t);
            }
            if (temp.getMagic_resistance() != null) {
                long t = stats.getMagic_resistance() + Long.parseLong(temp.getMagic_resistance());
                stats.setMagic_resistance(t);
            }
            if (temp.getMana() != null) {
                long t = stats.getMana() + Long.parseLong(temp.getMana());
                stats.setMana(t);
            }
            if (temp.getMana_regen() != null) {
                long t = stats.getMana_regen() + Long.parseLong(temp.getMana_regen());
                stats.setMana_regen(t);
            }
            if (temp.getMovement_speed() != null) {
                long t = stats.getMovement_speed() + Long.parseLong(temp.getMovement_speed());
                stats.setMovement_speed(t);
            }
            if (temp.getPhysical_attack() != null) {
                long t = stats.getPhysical_attack() + Long.parseLong(temp.getPhysical_attack());
                stats.setPhysical_attack(t);
            }

            if (temp.getCooldown_reduction() != null) {
                long t = stats.getCooldown_reduction() + Long.parseLong(temp.getCooldown_reduction());
                stats.setCooldown_reduction(t);
            }
            if (temp.getCrit_reduction() != null) {
                long t = stats.getCrit_reduction() + Long.parseLong(temp.getCrit_reduction());
                stats.setCrit_reduction(t);
            }
            if (temp.getDamage_to_monsters() != null) {
                long t = stats.getDamage_to_monsters() + Long.parseLong(temp.getDamage_to_monsters());
                stats.setDamage_to_monsters(t);
            }
            if (temp.getLifesteal() != null) {
                long t = stats.getLifesteal() + Long.parseLong(temp.getLifesteal());
                stats.setLifesteal(t);
            }
            if (temp.getMagic_penetration() != null) {
                long t = stats.getMagic_penetration() + Long.parseLong(temp.getMagic_penetration());
                stats.setMagic_penetration(t);
            }
            if (temp.getPhysical_penetration() != null) {
                long t = stats.getPhysical_penetration() + Long.parseLong(temp.getPhysical_penetration());
                stats.setPhysical_penetration(t);
            }
            if (temp.getResilience() != null) {
                long t = stats.getResilience() + Long.parseLong(temp.getResilience());
                stats.setResilience(t);
            }
            if (temp.getSpell_vamp() != null) {
                long t = stats.getSpell_vamp() + Long.parseLong(temp.getSpell_vamp());
                stats.setSpell_vamp(t);
            }
            if (temp.getCost() != null) {
                long t = stats.getCost() + Long.parseLong(temp.getCost());
                stats.setCost(t);
            }


        }

        setHeroStats(hero, stats);
    }
}
