package com.FabulasDeSapo.AdventureForge.actions;

import java.io.Serializable;

import com.FabulasDeSapo.AdventureForge.domain.Character;
import com.FabulasDeSapo.AdventureForge.enums.TurnAction;

public class ActiveBuff {

    public TurnAction buffType;
    public int remainingTurns;

    public ActiveBuff(TurnAction buffType, int remainingTurns) {
        this.buffType = buffType;
        this.remainingTurns = remainingTurns;
    }

    public void applyEffect(Character target) {
        if (target == null) {
            System.err.println("¡ERROR! Se intentó aplicar un buff a un objetivo nulo.");
            return;
        }

        switch (buffType) {
            case BUFF_ARMOR:
                target.armor += 5;
                break;

            case DEBUFF_ARMOR:
                target.armor -= 5;
                break;

            // Añade más buffs y debuffs aquí si necesitas
            default:
                break;
        }
    }

    public void removeEffect(Character target) {
        switch (buffType) {
            case BUFF_ARMOR:
                target.armor -= 5;
                break;

            case DEBUFF_ARMOR:
                target.armor += 5;
                break;


            // Asegúrate de revertir el efecto correctamente
            default:
                break;
        }
    }
}
