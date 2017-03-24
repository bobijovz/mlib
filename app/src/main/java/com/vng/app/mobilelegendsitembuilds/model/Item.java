package com.vng.app.mobilelegendsitembuilds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jovijovs on 3/24/2017.
 */

public class Item implements Serializable, Parcelable {

    private String name;
    private String ability_crit_rate;
    private String armor;
    private String attack_speed;
    private String basic_attack_crit_rate;
    private String cooldown_reduction;
    private String cost;
    private String crit_reduction;
    private String damage_to_monsters;
    private String hp;
    private String hp_regen;
    private String lifesteal;
    private String magic_penetration;
    private String magic_power;
    private String magic_resistance;
    private String mana;
    private String mana_regen;
    private String movement_speed;
    private String passive;
    private String physical_attack;
    private String physical_penetration;
    private String resilience;
    private String spell_vamp;
    private String type;

    public Item() {
    }

    protected Item(Parcel in) {
        name = in.readString();
        ability_crit_rate = in.readString();
        armor = in.readString();
        attack_speed = in.readString();
        basic_attack_crit_rate = in.readString();
        cooldown_reduction = in.readString();
        cost = in.readString();
        crit_reduction = in.readString();
        damage_to_monsters = in.readString();
        hp = in.readString();
        hp_regen = in.readString();
        lifesteal = in.readString();
        magic_penetration = in.readString();
        magic_power = in.readString();
        magic_resistance = in.readString();
        mana = in.readString();
        mana_regen = in.readString();
        movement_speed = in.readString();
        passive = in.readString();
        physical_attack = in.readString();
        physical_penetration = in.readString();
        resilience = in.readString();
        spell_vamp = in.readString();
        type = in.readString();
    }

    public Item(String name, String ability_crit_rate, String armor, String attack_speed, String basic_attack_crit_rate, String cooldown_reduction, String cost, String crit_reduction, String damage_to_monsters, String hp, String hp_regen, String lifesteal, String magic_penetration, String magic_power, String magic_resistance, String mana, String mana_regen, String movement_speed, String passive, String physical_attack, String physical_penetration, String resilience, String spell_vamp, String type) {
        this.name = name;
        this.ability_crit_rate = ability_crit_rate;
        this.armor = armor;
        this.attack_speed = attack_speed;
        this.basic_attack_crit_rate = basic_attack_crit_rate;
        this.cooldown_reduction = cooldown_reduction;
        this.cost = cost;
        this.crit_reduction = crit_reduction;
        this.damage_to_monsters = damage_to_monsters;
        this.hp = hp;
        this.hp_regen = hp_regen;
        this.lifesteal = lifesteal;
        this.magic_penetration = magic_penetration;
        this.magic_power = magic_power;
        this.magic_resistance = magic_resistance;
        this.mana = mana;
        this.mana_regen = mana_regen;
        this.movement_speed = movement_speed;
        this.passive = passive;
        this.physical_attack = physical_attack;
        this.physical_penetration = physical_penetration;
        this.resilience = resilience;
        this.spell_vamp = spell_vamp;
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ability_crit_rate);
        dest.writeString(armor);
        dest.writeString(attack_speed);
        dest.writeString(basic_attack_crit_rate);
        dest.writeString(cooldown_reduction);
        dest.writeString(cost);
        dest.writeString(crit_reduction);
        dest.writeString(damage_to_monsters);
        dest.writeString(hp);
        dest.writeString(hp_regen);
        dest.writeString(lifesteal);
        dest.writeString(magic_penetration);
        dest.writeString(magic_power);
        dest.writeString(magic_resistance);
        dest.writeString(mana);
        dest.writeString(mana_regen);
        dest.writeString(movement_speed);
        dest.writeString(passive);
        dest.writeString(physical_attack);
        dest.writeString(physical_penetration);
        dest.writeString(resilience);
        dest.writeString(spell_vamp);
        dest.writeString(type);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbility_crit_rate() {
        return ability_crit_rate;
    }

    public String getArmor() {
        return armor;
    }

    public String getAttack_speed() {
        return attack_speed;
    }

    public String getBasic_attack_crit_rate() {
        return basic_attack_crit_rate;
    }

    public String getCooldown_reduction() {
        return cooldown_reduction;
    }

    public String getCost() {
        return cost;
    }

    public String getCrit_reduction() {
        return crit_reduction;
    }

    public String getDamage_to_monsters() {
        return damage_to_monsters;
    }

    public String getHp() {
        return hp;
    }

    public String getHp_regen() {
        return hp_regen;
    }

    public String getLifesteal() {
        return lifesteal;
    }

    public String getMagic_penetration() {
        return magic_penetration;
    }

    public String getMagic_power() {
        return magic_power;
    }

    public String getMagic_resistance() {
        return magic_resistance;
    }

    public String getMana() {
        return mana;
    }

    public String getMana_regen() {
        return mana_regen;
    }

    public String getMovement_speed() {
        return movement_speed;
    }

    public String getPassive() {
        return passive;
    }

    public String getPhysical_attack() {
        return physical_attack;
    }

    public String getPhysical_penetration() {
        return physical_penetration;
    }

    public String getResilience() {
        return resilience;
    }

    public String getSpell_vamp() {
        return spell_vamp;
    }

    public String getType() {
        return type;
    }
}
