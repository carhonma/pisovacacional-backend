package com.FabulasDeSapo.AdventureForge.actions;

import com.FabulasDeSapo.AdventureForge.domain.Character;
import com.FabulasDeSapo.AdventureForge.enums.TurnAction;

import java.util.ArrayList;
import java.util.List;

public class BattleResult {
    private final Character character1;
    private final Character character2;
    private final String log;
    private final boolean finalResult;
    private final String[] rewardItems;
    private final Integer rewardExp;

    private final Integer heroBuffTurns;
    private final Integer enemyBuffTurns;

    // NUEVO: Buffs reales para aplicación de lógica
    private final Character actor;
    private final Character target;
    private final Integer buffTurns;
    private final TurnAction buffCharacter1;
    private final TurnAction buffCharacter2;

    // Lista de buffs activos del héroe (si quieres guardar históricos)
    private List<ActiveBuff> heroActiveBuffs = new ArrayList<>();

    // Constructor usado al aplicar una acción de combate
    public BattleResult(Character actor, Character target, TurnAction buffCharacter1, TurnAction buffCharacter2, int buffTurns, Boolean isCritic,Boolean isEvade) {
        this.actor = actor;
        this.target = target;
        this.buffTurns = buffTurns;
        this.buffCharacter1 = buffCharacter1;
        this.buffCharacter2 = buffCharacter2;

        this.character1 = null;
        this.character2 = null;
        this.log = String.valueOf(isCritic)+String.valueOf(isEvade);
        this.finalResult = false;
        this.rewardItems = null;
        this.rewardExp = 0;

        this.heroBuffTurns = null;
        this.enemyBuffTurns = null;
    }

    // Constructor final de batalla
    public BattleResult(Character hero, Character enemy, String log, Boolean finalResult, String[] rewardItems, Integer rewardExp) {
        this.character1 = hero;
        this.character2 = enemy;
        this.finalResult = finalResult;
        this.log = log;
        this.rewardItems = rewardItems;
        this.rewardExp = rewardExp;
        this.buffTurns = null;
        this.buffCharacter1 = null;
        this.buffCharacter2 = null;

        this.heroBuffTurns = null;
        this.enemyBuffTurns = null;

        this.actor = null;
        this.target = null;
    }

    // Constructor antiguo por si lo necesitas para algún caso visual
    public BattleResult(Character character1, Character character2, String heroBuff, String enemyBuff, Integer heroBuffTurns, Integer enemyBuffTurns, TurnAction buffCharacter1, TurnAction buffCharacter2) {
        this.character1 = character1;
        this.character2 = character2;
        this.heroBuffTurns = heroBuffTurns;
        this.enemyBuffTurns = enemyBuffTurns;
        this.buffCharacter1 = buffCharacter1;
        this.buffCharacter2 = buffCharacter2;

        this.rewardExp = 0;
        this.log = null;
        this.finalResult = false;
        this.rewardItems = null;

        this.actor = null;
        this.target = null;
        this.buffTurns = null;

    }

    public Character getCharacter1 () {
        return character1;
    }

    public Character getCharacter2 () {
        return character2;
    }

    public String getLog() {
        return log;
    }

    public Boolean getFinalResult() {
        return finalResult;
    }

    public String[] getRewardItems() {
        return rewardItems;
    }

    public Integer getRewardExp() {
        return rewardExp;
    }

    public Integer getHeroBuffTurns() {
        return heroBuffTurns;
    }

    public Integer getEnemyBuffTurns() {
        return enemyBuffTurns;
    }

    public List<ActiveBuff> getHeroActiveBuffs() {
        return heroActiveBuffs;
    }

    public void addHeroBuff(ActiveBuff buff) {
        this.heroActiveBuffs.add(buff);
    }

    // NUEVO: Aplicar el buff si corresponde
    public ActiveBuff getCharacter1BuffObject() {
        if (actor == null || target == null) return null;

        if (buffCharacter1 != null && actor != null && target != null) {
            return new ActiveBuff(buffCharacter1, buffTurns);
        }

        return null;
    }

    public ActiveBuff getCharacter2BuffObject() {
        if (actor == null || target == null) return null;

        if (buffCharacter2 != null && target != null) {
            return new ActiveBuff(buffCharacter2, buffTurns);
        }

        return null;
    }

    // Extra (opcional): getters por si necesitas lógica avanzada
    public Character getActor() {
        return actor;
    }

    public Character getTarget() {
        return target;
    }

    public TurnAction getBuffCharacter1() {
        return buffCharacter1;
    }

    public TurnAction getBuffCharacter2() {
        return buffCharacter2;
    }

    public Integer getBuffTurns() {
        return buffTurns;
    }
}
