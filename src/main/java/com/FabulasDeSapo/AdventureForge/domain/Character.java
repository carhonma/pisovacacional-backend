package com.FabulasDeSapo.AdventureForge.domain;

import com.FabulasDeSapo.AdventureForge.actions.ActiveBuff;
import com.FabulasDeSapo.AdventureForge.enums.CharacterType;
import com.FabulasDeSapo.AdventureForge.enums.TurnAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Character {
    public String name;
    public CharacterType type;
    public List<Object> state = new ArrayList<>();
    public TurnAction skill1;
    public TurnAction skill2;
    public TurnAction skill3;
    public TurnAction skill4;
    public TurnAction skill5;

    // Stats base
    public int damageBrutal;
    public int damageLetal;
    public int damageMistic;
    public int armor;
    public int resistance;
    public int accuracy;
    public int evasion;
    public int critic;
    public int maxHealth;
    public int maxExp;
    public int level;
    public int actualLife;
    public int actualExp;
    public String Item1;
    public String Item2;
    public String Item3;
    public String Item4;
    public String Item5;
    public String Item6;

    // Buffs activos
    private List<ActiveBuff> activeBuffs = new ArrayList<>();

    public Character() {}

    public Character(CharacterType type, String name) {
        this.name = name;
        this.type = type;
        this.state.add("");
        this.state.add("");
        this.skill1 = type.skill1;
        this.skill2 = type.skill2;
        this.skill3 = type.skill3;
        this.skill4 = type.skill4;
        this.skill5 = type.skill5;
        this.damageBrutal = type.damageBrutal;
        this.damageLetal = type.damageLetal;
        this.damageMistic = type.damageMistic;
        this.armor = type.armor;
        this.resistance = type.resistance;
        this.accuracy = type.accuracy;
        this.evasion = type.evasion;
        this.critic = type.critic;
        this.maxHealth = type.maxHealth;
        this.maxExp = 1000;
        this.level = type.price;
        this.actualLife = type.maxHealth;
        this.actualExp = 0;
    }

    public void CharacterForBattle(Map<String, Object> data) {
        this.name = (String) data.get("name");
        this.type = CharacterType.valueOf((String) data.get("type"));

        this.skill1 = TurnAction.valueOf((String) data.get("skill1"));
        this.skill2 = TurnAction.valueOf((String) data.get("skill2"));
        this.skill3 = TurnAction.valueOf((String) data.get("skill3"));
        this.skill4 = TurnAction.valueOf((String) data.get("skill4"));
        this.skill5 = TurnAction.valueOf((String) data.get("skill5"));

        this.damageBrutal = (int) data.get("Dbrutal");
        this.damageLetal = (int) data.get("Dletal");
        this.damageMistic = (int) data.get("Dmistic");
        this.armor = (int) data.get("armor");
        this.resistance = (int) data.get("resistance");
        this.accuracy = (int) data.get("accuracy");
        this.evasion = (int) data.get("evasion");
        this.critic = (int) data.get("critic");
        this.maxHealth = (int) data.get("maxHealth");
        this.maxExp = (int) data.getOrDefault("maxExp", 1000);
        this.level = (int) data.getOrDefault("level", 1);
        this.actualLife = (int) data.getOrDefault("actualLife", 0);
        this.actualExp = (int) data.getOrDefault("actualExp", 0);

        this.Item1 = (String) data.get("Item1");
        this.Item2 = (String) data.get("Item2");
        this.Item3 = (String) data.get("Item3");
        this.Item4 = (String) data.get("Item4");
        this.Item5 = (String) data.get("Item5");
        this.Item6 = (String) data.get("Item6");
    }

    // =========================
    // Gestión de Buffs
    // =========================

    public void addBuff(ActiveBuff buff) {
        this.activeBuffs.add(buff);
    }

    public void decrementBuffDurations() {
        activeBuffs.removeIf(buff -> {
            buff.remainingTurns--;
            return buff.remainingTurns <= 0;
        });
    }

    public int getStatWithBuff(String statName, int baseValue) {
        int total = baseValue;
        for (ActiveBuff buff : activeBuffs) {
            switch (statName) {
                case "armor":
                    if (buff.buffType == TurnAction.BUFF_ARMOR) total += 5;
                    if (buff.buffType == TurnAction.DEBUFF_ARMOR) total -= 5;
                    break;
                // Puedes añadir más stats aquí si agregas nuevos buffs/debuffs
                default:
                    break;
            }
        }
        return Math.max(total, 0); // evita stats negativas como armadura < 0
    }

    public int getDamageBrutal() {
        return getStatWithBuff("damageBrutal", this.damageBrutal);
    }

    public int getDamageLetal() {
        return getStatWithBuff("damageLetal", this.damageLetal);
    }

    public int getDamageMistic() {
        return getStatWithBuff("damageMistic", this.damageMistic);
    }

    public int getArmor() {
        return getStatWithBuff("armor", this.armor);
    }

    public int getResistance() {
        return getStatWithBuff("resistance", this.resistance);
    }

    public int getAccuracy() {
        return getStatWithBuff("accuracy", this.accuracy);
    }

    public int getEvasion() {
        return getStatWithBuff("evasion", this.evasion);
    }

    public int getCritic() {
        return getStatWithBuff("critic", this.critic);
    }

    public int getMaxHealth() {
        return getStatWithBuff("maxHealth", this.maxHealth);
    }

    public List<ActiveBuff> getActiveBuffs() {
        return activeBuffs;
    }
}
