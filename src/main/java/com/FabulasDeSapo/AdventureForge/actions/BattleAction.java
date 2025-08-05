package com.FabulasDeSapo.AdventureForge.actions;

import com.FabulasDeSapo.AdventureForge.domain.Character;
import com.FabulasDeSapo.AdventureForge.enums.MissionReward;
import com.FabulasDeSapo.AdventureForge.enums.TurnAction;

public class BattleAction {

    private Character hero;
    private Character enemy;

    public BattleAction(Character hero, Character enemy) {
        this.hero = hero;
        this.enemy = enemy;
    }

    public BattleResult startBattle() {

        if (hero == null) {
            System.err.println("ERROR: Hero es null en startBattle");
            // Aquí puedes lanzar una excepción o devolver un resultado que indique fallo
            return new BattleResult(null, null, "Error: Hero es null", false, null, 0);
        }

        if (enemy == null) {
            System.err.println("ERROR: Enemy es null en startBattle");
            return new BattleResult(null, null, "Error: Enemy es null", false, null, 0);
        }

        String[] rewardItems = {null, null, null};
        Integer rewardExp = 0;

        TurnAction[] heroSkills = {
                hero.skill1, hero.skill2, hero.skill3,
                hero.skill4, hero.skill5
        };
        TurnAction[] enemySkills = {
                enemy.skill1, enemy.skill2, enemy.skill3,
                enemy.skill4, enemy.skill5
        };

        ActiveBuff[] heroBuffs = new ActiveBuff[5];
        ActiveBuff[] enemyBuffs = new ActiveBuff[5];
        StringBuilder log = new StringBuilder();

        log.append("La batalla comienza entre ").append(hero.name)
                .append(" y ").append(enemy.name).append(".\n")
                .append("vida del heroe: ").append(hero.actualLife).append(".\n")
                .append("vida del enemigo: ").append(enemy.actualLife).append(".\n");

        boolean result = false;
        int turn = 1;

        while (turn <= 5) {
            log.append("Turno ").append(turn).append(":\n");

            // Reducir duración de buffs
            for (int i = 0; i < 5; i++) {
                if (heroBuffs[i] != null && --heroBuffs[i].remainingTurns <= 0) {
                    heroBuffs[i].removeEffect(hero);
                    log.append("El héroe pierde el efecto: ")
                            .append(heroBuffs[i].buffType).append("\n");
                    heroBuffs[i] = null;
                }
                if (enemyBuffs[i] != null && --enemyBuffs[i].remainingTurns <= 0) {
                    enemyBuffs[i].removeEffect(enemy);
                    log.append("El enemigo pierde el efecto: ")
                            .append(enemyBuffs[i].buffType).append("\n");
                    enemyBuffs[i] = null;
                }
            }

            // Héroe ataca
            log.append("Hero do: ")
                    .append(heroSkills[turn - 1].name());
            BattleResult r1 = heroSkills[turn - 1].apply(hero, enemy);
            if (r1.getLog().equals("truetrue")) log.append(" Critico!! Evade!!").append("\n");
            else if (r1.getLog().equals("truefalse")) log.append(" Critico!!").append("\n");
            else if (r1.getLog().equals("falsetrue")) log.append(" Evade!!").append("\n");
            else{log.append("\n");}
            if (r1.getCharacter1() != null) hero = r1.getCharacter1();
            if (r1.getCharacter2() != null) enemy = r1.getCharacter2();

            // Aplicar buffs tras ataque héroe
            applyPendingBuff(r1.getCharacter1BuffObject(), heroBuffs, hero, log);
            applyPendingBuff(r1.getCharacter2BuffObject(), enemyBuffs, enemy, log);


            if (enemy.actualLife <= 0) {
                log.append(enemy.name).append(" ha sido derrotado.\n");
                result = true;
                break;
            }

            // Enemigo ataca
            log.append("Enemy do: ")
                    .append(enemySkills[turn - 1].name());
            BattleResult r2 = enemySkills[turn - 1].apply(enemy, hero);
            if (r2.getLog().equals("truetrue")) log.append(" Critico!! Evade!!").append("\n");
            else if (r2.getLog().equals("truefalse")) log.append(" Critico!!").append("\n");
            else if (r2.getLog().equals("falsetrue")) log.append(" Evade!!").append("\n");
            else{log.append("\n");}
            if (r2.getCharacter1() != null) enemy = r2.getCharacter1();
            if (r2.getCharacter2() != null) hero = r2.getCharacter2();

            // Aplicar buffs tras ataque enemigo
            applyPendingBuff(r2.getCharacter1BuffObject(),enemyBuffs, enemy, log);
            applyPendingBuff(r2.getCharacter2BuffObject(), heroBuffs, hero, log);

            if (hero.actualLife <= 0) {
                hero.actualLife = 0;
                log.append(hero.name).append(" ha sido derrotado.\n");
                result = false;
                break;
            }

            log.append("vida del heroe: ").append(hero.actualLife)
                    .append(".\nvida del enemigo: ").append(enemy.actualLife)
                    .append(".\n");

            if (turn == 5) {
                log.append("Se han acabado los turnos.\n");
                result = false;
            }

            turn++;
        }

        // Recompensas
        if (result) {
            MissionReward reward = MissionReward.valueOf(String.valueOf(enemy.type));
            rewardItems[0] = MissionReward.reprobItem(reward.Item1);
            rewardItems[1] = MissionReward.reprobItem(reward.Item2);
            rewardItems[2] = MissionReward.reprobItem(reward.Item3);

            rewardItems[0] = MissionReward.reprobItemGrade(rewardItems[0],hero.level,enemy.level);
            rewardItems[1] = MissionReward.reprobItemGrade(rewardItems[1],hero.level,enemy.level);
            rewardItems[2] = MissionReward.reprobItemGrade(rewardItems[2],hero.level,enemy.level);
            rewardExp = reward.Exp;
        }

        return new BattleResult(hero, enemy, log.toString(), result, rewardItems, rewardExp);
    }

    private void applyPendingBuff(ActiveBuff buff, ActiveBuff[] slots,
                                  Character target, StringBuilder log) {
        if (buff != null) {
            for (int i = 0; i < slots.length; i++) {
                if (slots[i] == null) {
                    buff.applyEffect(target);
                    slots[i] = buff;
                    log.append("El ").append(target.name).append(" obtiene el buff: ")
                            .append(buff.buffType).append(" durante ")
                            .append(buff.remainingTurns).append(" turnos.\n");
                    break;
                }
            }
        }
    }
}
