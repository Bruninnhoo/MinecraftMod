package com.bruno.brunomod.capability;

public interface ITraitData {
    boolean hasSelected();
    void setSelected(boolean value);
    void copyFrom(ITraitData other);
}
