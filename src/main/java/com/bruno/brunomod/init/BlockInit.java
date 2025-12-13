package com.bruno.brunomod.init;

import com.bruno.brunomod.BrunoMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            BrunoMod.MOD_ID);

    public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

    private static <T extends Block> RegistryObject<T> registerBlock(final String name, final Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
            Function<RegistryObject<T>, Supplier<? extends Item>> Item) {
        return BLOCKS.register(name, block);
    }

}
