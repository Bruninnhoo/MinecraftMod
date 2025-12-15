package com.bruno.brunomod.capability;

import com.bruno.brunomod.trait.Trait;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class TraitData implements ITraitData{
    private boolean selected = false;
    private List<Trait> activeTraits = new ArrayList<>();

    public void saveNBT(CompoundTag nbt) {
        nbt.putBoolean("isTraitsSelected", this.selected);

        ListTag traitListTag = new ListTag();

        for (Trait trait : this.activeTraits) {
            traitListTag.add(StringTag.valueOf(trait.name()));
        }

        nbt.put("MyActiveTraits", traitListTag);
    }

    public void loadNBT(CompoundTag nbt) {
        this.selected = nbt.getBoolean("isTraitsSelected");
        this.activeTraits.clear();
        ListTag traitListTag = nbt.getList("MyActiveTraits", Tag.TAG_STRING);

        for(int i=0; i < traitListTag.size(); i++) {
            String traitName = traitListTag.getString(i);

            try {
                Trait t = Trait.valueOf(traitName);
                this.activeTraits.add(t);
            } catch (IllegalArgumentException e) {
                System.out.println("Trait desconhecida e removida: " + traitName);
            }
        }
    }

    @Override
    public boolean hasSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean value) {
        this.selected = value;
    }

    @Override
    public void copyFrom(ITraitData other) {
        this.selected = other.hasSelected();
    }
}
