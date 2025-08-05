package com.FabulasDeSapo.AdventureForge.enums;

import com.FabulasDeSapo.AdventureForge.domain.Character;
import com.FabulasDeSapo.AdventureForge.domain.Hero;
import com.FabulasDeSapo.AdventureForge.domain.Enemy;
import com.FabulasDeSapo.AdventureForge.actions.BattleResult;

public enum TurnAction {
    STANDARD_ATTACK {

        public BattleResult apply(Character character1, Character character2) {
            int damageBrutal = character1.damageBrutal-character2.armor;if(damageBrutal<0){damageBrutal=0;}
            int damageMistic = character1.damageMistic-character2.resistance;if(damageMistic<0){damageMistic=0;}
            int damageLetal1 = character1.damageLetal/2-character2.armor/2; if(damageLetal1<0){damageLetal1=0;}
            int damageLetal2 = character1.damageLetal/2-character2.resistance/2;if(damageLetal2<0){damageLetal2=0;}
            int damageTotal = damageBrutal + damageMistic + damageLetal1 + damageLetal2;

            boolean isCritic = checkCritic(character1.critic);
            boolean isEvade = false;
            if(character1.accuracy<=character2.evasion){isEvade = checkEvade();}
            if(isCritic){ damageTotal = (int) (damageTotal * 1.5);}
            if(isEvade){ damageTotal = 0;}

            character2.actualLife = character2.actualLife - damageTotal;
            return new BattleResult(character1, character2, null, null,0,isCritic,isEvade);
        }
    },
    HARD_STRIKE {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            int damageTotal = (int) (character1.damageBrutal*1.5)-character2.armor;if(damageTotal<0){damageTotal=0;}

            boolean isCritic = checkCritic(character1.critic);
            boolean isEvade = false;
            if(character1.accuracy<=character2.evasion){isEvade = checkEvade();}
            if(isCritic){ damageTotal = (int) (damageTotal * 1.5);}
            if(isEvade){ damageTotal = 0;}

            character2.actualLife = character2.actualLife - damageTotal;
            return new BattleResult(character1, character2, null, null,0,isCritic,isEvade);
        }
    },
    HARD_SHOT {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            int damageLetal1 = (int) (character1.damageLetal*1.5)/2-character2.armor/2; if(damageLetal1<0){damageLetal1=0;}
            int damageLetal2 = (int) (character1.damageLetal*1.5)/2-character2.resistance/2;if(damageLetal2<0){damageLetal2=0;}
            int damageTotal = damageLetal1 + damageLetal2;

            boolean isCritic = checkCritic(character1.critic);
            boolean isEvade = false;
            if(character1.accuracy<=character2.evasion){isEvade = checkEvade();}
            if(isCritic){ damageTotal = (int) (damageTotal * 1.5);}
            if(isEvade){ damageTotal = 0;}

            character2.actualLife = character2.actualLife - damageTotal;
            return new BattleResult(character1, character2, null, null,0,isCritic,isEvade);
        }
    },
    HARD_SPELL {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            int damageTotal = (int) (character1.damageMistic*1.5)-character2.resistance;if(damageTotal<0){damageTotal=0;}

            boolean isCritic = checkCritic(character1.critic);
            boolean isEvade = false;
            if(character1.accuracy<=character2.evasion){isEvade = checkEvade();}
            if(isCritic){ damageTotal = (int) (damageTotal * 1.5);}
            if(isEvade){ damageTotal = 0;}

            character2.actualLife = character2.actualLife - damageTotal;
            return new BattleResult(character1, character2, null, null,0,isCritic,isEvade);
        }
    },
    BUFF_ARMOR {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            // Se aplica el buff positivo al personaje que realiza la acción
            return new BattleResult(character1, character2, TurnAction.BUFF_ARMOR, null, 2,false,false);
        }
    },
    DEBUFF_ARMOR {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            // Se aplica el debuff negativo al personaje que recibe la acción
            return new BattleResult(character1, character2, null, TurnAction.DEBUFF_ARMOR, 2,false,false);
        }
    },
    NOACTION {
        @Override
        public BattleResult apply(Character character1, Character character2) {
            return new BattleResult(character1, character2, null, null,0,false,false);
        }
    };

    private static boolean checkEvade() {
        boolean isEvade = false;
        double random = Math.random();
        if (random <= 0.5){
            isEvade = true;
        }
        return isEvade;
    }

    private static boolean checkCritic(int characterCritic) {
        boolean isCritic = false;
        double random = Math.random()*100;
        if (random <= characterCritic){
            isCritic = true;
        }
        return isCritic;
    }

    public abstract BattleResult apply(Character character1, Character character2);
}