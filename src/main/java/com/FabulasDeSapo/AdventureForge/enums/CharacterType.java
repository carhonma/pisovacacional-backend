package com.FabulasDeSapo.AdventureForge.enums;



public enum CharacterType {
        GUERRERO(100, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,5,0,0,10,0,5,5,0,100,new StatsPerLevel(1,0,0,2,0,0,0,0,10,2),new Category[]{Category.SWORD, Category.HEAVYARMOR},new Category[]{Category.DAGE, Category.LIGHTARMOR}),
        PICARO(100, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,7,0,3,5,5,7,7,50,new StatsPerLevel(0,1,0,1,1,0,0,0,5,2),new Category[]{Category.DAGE, Category.MEDIUMARMOR},new Category[]{Category.SWORD, Category.HEAVYARMOR}),
        MAGO(100, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,10,0,2,5,3,5,10,new StatsPerLevel(0,0,2,0,1,0,0,0,2,2),new Category[]{Category.CROSIER, Category.LIGHTARMOR},new Category[]{Category.WAND, Category.MEDIUMARMOR}),
        PALADIN(1000, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,2,0,5,0,10,5,3,0,100,new StatsPerLevel(1,0,1,0,2,0,0,0,10,2),new Category[]{Category.SWORD, Category.HEAVYARMOR},new Category[]{Category.AXE, Category.LIGHTARMOR}),
        CAZADOR(1000, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,7,0,5,1,10,0,5,50,new StatsPerLevel(0,1,0,1,1,1,0,0,5,2),new Category[]{Category.BOW, Category.MEDIUMARMOR},new Category[]{Category.DAGE, Category.HEAVYARMOR}),
        CLERIGO(1000, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,6,0,5,5,0,0,10,new StatsPerLevel(0,0,1,0,1,1,1,0,2,2),new Category[]{Category.WAND, Category.LIGHTARMOR},new Category[]{Category.CROSIER, Category.MEDIUMARMOR}),

        FOREST1(1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,5,0,0,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        FOREST1_UP1(1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,5,0,0,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        FOREST1_UP2(1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,5,0,0,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        GOLEM1 (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,5,0,0,3,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        GOLEM1_UP1 (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,5,0,0,5,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        GOLEM1_UP2 (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,5,0,0,5,0,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        ENT1   (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,5,0,5,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        ENT1_UP1   (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,5,0,5,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        ENT1_UP2   (1, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,5,0,5,10,3,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI1(2, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,0,5,5,10,3,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI1_UP1(2, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,0,5,5,10,3,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI1_UP2(2, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,0,5,5,10,3,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI2(2, TurnAction.STANDARD_ATTACK, TurnAction.BUFF_ARMOR, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,15,0,0,10,0,10,4,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI2_UP1(2, TurnAction.STANDARD_ATTACK, TurnAction.BUFF_ARMOR, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,15,0,0,10,0,10,4,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI2_UP2(2, TurnAction.STANDARD_ATTACK, TurnAction.BUFF_ARMOR, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,15,0,0,10,0,10,4,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI3(3, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,20,0,2,2,10,5,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI3_UP1(3, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,20,0,2,2,10,5,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI3_UP2(3, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,20,0,2,2,10,5,10,20,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI4(4, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,25,10,5,10,6,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI4_UP1(4, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,25,10,5,10,6,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI4_UP2(4, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,0,25,10,5,10,6,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI5(6, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,30,10,10,10,7,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI5_UP1(6, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,30,10,10,10,7,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI5_UP2(6, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,10,0,30,10,10,10,7,10,30,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI6(8, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,40,0,20,5,10,8,10,40,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI6_UP1(8, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,40,0,20,5,10,8,10,40,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI6_UP2(8, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,40,0,20,5,10,8,10,40,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),

        JABALI7(10, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,30,30,0,20,20,10,10,10,50,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI7_UP1(10, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,30,30,0,20,20,10,10,10,50,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        JABALI7_UP2(10, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,30,30,0,20,20,10,10,10,50,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),


        PEZ2(12, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,5,0,0,0,10,10,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        PEZ2_UP1(12, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK, TurnAction.STANDARD_ATTACK,TurnAction.STANDARD_ATTACK,0,5,0,0,0,10,10,10,10,new StatsPerLevel(0,0,0,0,0,0,0,0,0,0),new Category[]{Category.NOSUBTYPE},new Category[]{Category.NOSUBTYPE}),
        ;

        public final int price;
        public final TurnAction skill1;
        public final TurnAction skill2;
        public final TurnAction skill3;
        public final TurnAction skill4;
        public final TurnAction skill5;

        public final int damageBrutal;
        public final int damageLetal;
        public final int damageMistic;
        public final int armor;
        public final int resistance;
        public final int accuracy;
        public final int evasion;
        public final int critic;
        public final int maxHealth;
        public final StatsPerLevel statsPerLevel;
        public final Category[] bonus;
        public final Category[] collateral;

        CharacterType(int price, TurnAction skill1, TurnAction skill2, TurnAction skill3, TurnAction skill4, TurnAction skill5,
                      int damageBrutal, int damageLetal, int damageMistic, int armor, int resistance, int accuracy, int evasion, int critic, int  maxHealth, StatsPerLevel statsPerLevel, Category[] bonus, Category[] collateral) {

                this.price = price;
                this.skill1 = skill1;
                this.skill2 = skill2;
                this.skill3 = skill3;
                this.skill4 = skill4;
                this.skill5 = skill5;
                this.damageBrutal = damageBrutal;
                this.damageLetal = damageLetal;
                this.damageMistic = damageMistic;
                this.armor = armor;
                this.resistance = resistance;
                this.accuracy = accuracy;
                this.evasion = evasion;
                this.critic = critic;
                this.maxHealth = maxHealth;
                this.statsPerLevel = statsPerLevel;
                this.bonus = bonus;
                this.collateral = collateral;

        }
        public static class StatsPerLevel {

                public final int Dbrutal;
                public final int Dletal;
                public final int Dmistic;
                public final int armor;
                public final int resistance;
                public final int accuracy;
                public final int evasion;
                public final int critic;
                public final int maxHealth;
                public final int maxExp;

                public StatsPerLevel(int Dbrutal,int Dletal,int Dmistic,int armor,int resistance,int accuracy,int evasion,int critic,int maxHealth,int maxExp) {

                        this.Dbrutal = Dbrutal;
                        this.Dletal = Dletal;
                        this.Dmistic = Dmistic;
                        this.armor = armor;
                        this.resistance = resistance;
                        this.accuracy = accuracy;
                        this.evasion = evasion;
                        this.critic = critic;
                        this.maxHealth = maxHealth;
                        this.maxExp = maxExp;
                }
        }
}
