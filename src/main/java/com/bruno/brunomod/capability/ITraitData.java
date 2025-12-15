package com.bruno.brunomod.capability;

import com.bruno.brunomod.trait.Trait;

import java.util.List;

public interface ITraitData {
    boolean hasSelected();
    void setSelected(boolean value);
    void copyFrom(ITraitData other);

    void setTraits(List<Trait> traits);
}
