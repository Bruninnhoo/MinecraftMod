package com.bruno.brunomod.trait;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Trait {
    //DEBUFFS
    FUMANTE("fumante", -4, Type.DEBUFF),
    ASMATICO("asmatico", -5, Type.DEBUFF),
    SEDENTARIO("sedentario", -6, Type.DEBUFF),

    //BUFFS
    ROBUSTO("robusto", 6, Type.BUFF),
    CORREDOR("corredor", 4, Type.BUFF),
    SORTUDO("sortudo", 4, Type.BUFF);

    private final String id;
    private final int cost;
    private final Type type;

    public enum Type {
        BUFF,
        DEBUFF
    }

    Trait(String id, int cost, Type type) {
        this.id = id;
        this.cost = cost;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public Type getType(){
        return type;
    }

    public Component getDisplayName(){
        return new TranslatableComponent("trait.brunomod." + id);
    }

    public Component getDescription() {
        return new TranslatableComponent("trait.brunomod." + id + ".desc");
    }
}
