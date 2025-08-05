package com.FabulasDeSapo.AdventureForge.enums;



public enum Category {
    NOSUBTYPE(new CategoryBenefits(0,0,0,0,0,0,0,0,0),new CategoryCollateral(0,0,0,0,0,0,0,0,0)),
    RAW(new CategoryBenefits(0,0,0,0,0,0,0,0,0),new CategoryCollateral(0,0,0,0,0,0,0,0,0)),
    POTION(new CategoryBenefits(0,0,0,0,0,0,0,0,0),new CategoryCollateral(0,0,0,0,0,0,0,0,0)),

    HEAVYARMOR(new CategoryBenefits(0,0,0,1,0,0,0,0,0),new CategoryCollateral(0,0,0,-1,0,0,0,0,0)),
    MEDIUMARMOR(new CategoryBenefits(0,0,0,1,0,0,0,0,0),new CategoryCollateral(0,0,0,-1,0,0,0,0,0)),
    LIGHTARMOR(new CategoryBenefits(0,0,0,1,0,0,0,0,0),new CategoryCollateral(0,0,0,0,-1,0,0,0,0)),
    SWORD(new CategoryBenefits(2,0,0,0,0,0,0,0,0),new CategoryCollateral(-2,0,0,0,0,0,0,0,0)),
    AXE(new CategoryBenefits(2,0,0,0,0,0,0,0,0),new CategoryCollateral(-2,0,0,0,0,0,0,0,0)),
    HAMMER(new CategoryBenefits(0,0,0,0,2,0,0,0,0),new CategoryCollateral(0,0,0,0,-2,0,0,0,0)),

    DAGE(new CategoryBenefits(0,2,0,0,0,0,0,0,0),new CategoryCollateral(0,-2,0,0,0,0,0,0,0)),
    BOW(new CategoryBenefits(0,2,0,0,0,0,0,0,0),new CategoryCollateral(0,-2,0,0,0,0,0,0,0)),
    CROSIER(new CategoryBenefits(0,0,2,0,0,0,0,0,0),new CategoryCollateral(0,0,-2,0,0,0,0,0,0)),
    WAND(new CategoryBenefits(0,0,2,0,0,0,0,0,0),new CategoryCollateral(0,0,-2,0,0,0,0,0,0)),
    ;



    public final CategoryBenefits benefits;
    public final CategoryCollateral collateral;

  Category(CategoryBenefits benefits,CategoryCollateral collateral) {
      this.benefits = benefits;
      this.collateral = collateral;
  }

    public static class CategoryBenefits {

        public final int Dbrutal;
        public final int Dletal;
        public final int Dmistic;
        public final int armor;
        public final int resistance;
        public final int accuracy;
        public final int evasion;
        public final int critic;
        public final int maxHealth;

        public CategoryBenefits(int Dbrutal,int Dletal,int Dmistic,int armor,int resistance,int accuracy,int evasion,int critic,int maxHealth) {

            this.Dbrutal = Dbrutal;
            this.Dletal = Dletal;
            this.Dmistic = Dmistic;
            this.armor = armor;
            this.resistance = resistance;
            this.accuracy = accuracy;
            this.evasion = evasion;
            this.critic = critic;
            this.maxHealth = maxHealth;
        }
    }
    public static class CategoryCollateral {

        public final int Dbrutal;
        public final int Dletal;
        public final int Dmistic;
        public final int armor;
        public final int resistance;
        public final int accuracy;
        public final int evasion;
        public final int critic;
        public final int maxHealth;

        public CategoryCollateral(int Dbrutal,int Dletal,int Dmistic,int armor,int resistance,int accuracy,int evasion,int critic,int maxHealth) {

            this.Dbrutal = Dbrutal;
            this.Dletal = Dletal;
            this.Dmistic = Dmistic;
            this.armor = armor;
            this.resistance = resistance;
            this.accuracy = accuracy;
            this.evasion = evasion;
            this.critic = critic;
            this.maxHealth = maxHealth;
        }
    }
}
