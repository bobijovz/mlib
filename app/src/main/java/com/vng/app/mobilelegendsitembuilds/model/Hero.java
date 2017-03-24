package com.vng.app.mobilelegendsitembuilds.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jovijovs on 3/23/2017.
 */

public class Hero implements Serializable, Parcelable {
    private String name;
    private long ability_crit_rate;
    private long armor;
    private long attack_speed;
    private long basic_attack_crit_rate;
    private long hp;
    private long hp_regen;
    private long magic_power;
    private long magic_resistance;
    private long mana;
    private long mana_regen;
    private long movement_speed;
    private long physical_attack;
    private String role;
    private String specialty;

    public Hero(){

    }

    protected Hero(Parcel in) {
        name = in.readString();
        ability_crit_rate = in.readLong();
        armor = in.readLong();
        attack_speed = in.readLong();
        basic_attack_crit_rate = in.readLong();
        hp = in.readLong();
        hp_regen = in.readLong();
        magic_power = in.readLong();
        magic_resistance = in.readLong();
        mana = in.readLong();
        mana_regen = in.readLong();
        movement_speed = in.readLong();
        physical_attack = in.readLong();
        role = in.readString();
        specialty = in.readString();
    }

    public Hero(String name, long ability_crit_rate, long armor, long attack_speed, long basic_attack_crit_rate, long hp, long hp_regen, long magic_power, long magic_resistance, long mana, long mana_regen, long movement_speed, long physical_attack, String role, String specialty) {
        this.name = name;
        this.ability_crit_rate = ability_crit_rate;
        this.armor = armor;
        this.attack_speed = attack_speed;
        this.basic_attack_crit_rate = basic_attack_crit_rate;
        this.hp = hp;
        this.hp_regen = hp_regen;
        this.magic_power = magic_power;
        this.magic_resistance = magic_resistance;
        this.mana = mana;
        this.mana_regen = mana_regen;
        this.movement_speed = movement_speed;
        this.physical_attack = physical_attack;
        this.role = role;
        this.specialty = specialty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(ability_crit_rate);
        dest.writeLong(armor);
        dest.writeLong(attack_speed);
        dest.writeLong(basic_attack_crit_rate);
        dest.writeLong(hp);
        dest.writeLong(hp_regen);
        dest.writeLong(magic_power);
        dest.writeLong(magic_resistance);
        dest.writeLong(mana);
        dest.writeLong(mana_regen);
        dest.writeLong(movement_speed);
        dest.writeLong(physical_attack);
        dest.writeString(role);
        dest.writeString(specialty);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel in) {
            return new Hero(in);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };

    public long getAbility_crit_rate() {
        return ability_crit_rate;
    }

    public long getArmor() {
        return armor;
    }

    public long getAttack_speed() {
        return attack_speed;
    }

    public long getBasic_attack_crit_rate() {
        return basic_attack_crit_rate;
    }

    public long getHp() {
        return hp;
    }

    public long getHp_regen() {
        return hp_regen;
    }

    public long getMagic_power() {
        return magic_power;
    }

    public long getMagic_resistance() {
        return magic_resistance;
    }

    public long getMana() {
        return mana;
    }

    public long getMana_regen() {
        return mana_regen;
    }

    public long getMovement_speed() {
        return movement_speed;
    }

    public long getPhysical_attack() {
        return physical_attack;
    }

    public String getRole() {
        return role;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

