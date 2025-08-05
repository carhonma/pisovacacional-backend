package com.FabulasDeSapo.AdventureForge.domain;

import com.FabulasDeSapo.AdventureForge.enums.CharacterType;

import java.util.Map;

public class Hero extends Character {


    public Hero(CharacterType type, String name) {
        super(type, name);
    }

    public Hero(Map<String, Object> data) {
        super();
        CharacterForBattle(data);
    }

    public void delete() {

    }

}
