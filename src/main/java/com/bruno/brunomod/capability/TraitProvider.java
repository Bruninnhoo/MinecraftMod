package com.bruno.brunomod.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TraitProvider implements ICapabilitySerializable<CompoundTag> {

    public static Capability<ITraitData> TRAIT_CAP = CapabilityManager.get(new CapabilityToken<>() {});

    private final TraitData backend = new TraitData();

    private final LazyOptional<ITraitData> optional = LazyOptional.of(() -> backend);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == TRAIT_CAP) {
            return optional.cast();
        }
        return  LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        backend.saveNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        backend.loadNBT(nbt);
    }
}
