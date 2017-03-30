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
    private long cooldown_reduction;
    private long crit_reduction;
    private long damage_to_monsters;
    private long lifesteal;
    private long magic_penetration;
    private long physical_penetration;
    private long resilience;
    private long spell_vamp;
    private long cost;
    private String role;
    private String specialty;

    public Hero() {

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
        cooldown_reduction = in.readLong();
        crit_reduction = in.readLong();
        damage_to_monsters = in.readLong();
        lifesteal = in.readLong();
        magic_penetration = in.readLong();
        physical_penetration = in.readLong();
        resilience = in.readLong();
        spell_vamp = in.readLong();
        cost = in.readLong();
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
        dest.writeLong(cooldown_reduction);
        dest.writeLong(crit_reduction);
        dest.writeLong(damage_to_monsters);
        dest.writeLong(lifesteal);
        dest.writeLong(magic_penetration);
        dest.writeLong(physical_penetration);
        dest.writeLong(resilience);
        dest.writeLong(spell_vamp);
        dest.writeLong(cost);
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

    public void setAbility_crit_rate(long ability_crit_rate) {
        this.ability_crit_rate = ability_crit_rate;
    }

    public void setArmor(long armor) {
        this.armor = armor;
    }

    public void setAttack_speed(long attack_speed) {
        this.attack_speed = attack_speed;
    }

    public void setBasic_attack_crit_rate(long basic_attack_crit_rate) {
        this.basic_attack_crit_rate = basic_attack_crit_rate;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public void setHp_regen(long hp_regen) {
        this.hp_regen = hp_regen;
    }

    public void setMagic_power(long magic_power) {
        this.magic_power = magic_power;
    }

    public void setMagic_resistance(long magic_resistance) {
        this.magic_resistance = magic_resistance;
    }

    public void setMana(long mana) {
        this.mana = mana;
    }

    public void setMana_regen(long mana_regen) {
        this.mana_regen = mana_regen;
    }

    public void setMovement_speed(long movement_speed) {
        this.movement_speed = movement_speed;
    }

    public void setPhysical_attack(long physical_attack) {
        this.physical_attack = physical_attack;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public long getCooldown_reduction() {
        return cooldown_reduction;
    }

    public void setCooldown_reduction(long cooldown_reduction) {
        this.cooldown_reduction = cooldown_reduction;
    }

    public long getCrit_reduction() {
        return crit_reduction;
    }

    public void setCrit_reduction(long crit_reduction) {
        this.crit_reduction = crit_reduction;
    }

    public long getDamage_to_monsters() {
        return damage_to_monsters;
    }

    public void setDamage_to_monsters(long damage_to_monsters) {
        this.damage_to_monsters = damage_to_monsters;
    }

    public long getLifesteal() {
        return lifesteal;
    }

    public void setLifesteal(long lifesteal) {
        this.lifesteal = lifesteal;
    }

    public long getMagic_penetration() {
        return magic_penetration;
    }

    public void setMagic_penetration(long magic_penetration) {
        this.magic_penetration = magic_penetration;
    }

    public long getPhysical_penetration() {
        return physical_penetration;
    }

    public void setPhysical_penetration(long physical_penetration) {
        this.physical_penetration = physical_penetration;
    }

    public long getResilience() {
        return resilience;
    }

    public void setResilience(long resilience) {
        this.resilience = resilience;
    }

    public long getSpell_vamp() {
        return spell_vamp;
    }

    public void setSpell_vamp(long spell_vamp) {
        this.spell_vamp = spell_vamp;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public static Creator<Hero> getCREATOR() {
        return CREATOR;
    }
}

