package com.FabulasDeSapo.AdventureForge.actions;

import java.util.HashMap;
import java.util.Map;

public class BuffEffect {
    private final Map<String, Integer> modifiers = new HashMap<>();

    public BuffEffect addModifier(String statName, int value) {
        modifiers.put(statName, value);
        return this;
    }

    public int getModifier(String statName) {
        return modifiers.getOrDefault(statName, 0);
    }

    public Map<String, Integer> getAllModifiers() {
        return modifiers;
    }
}