package com.bruno.brunomod;

import com.bruno.brunomod.event.ModEvents;
import com.bruno.brunomod.init.ItemInit;
import com.bruno.brunomod.network.PacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod("brunomod")
public class BrunoMod
{
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "brunomod";

    public static final CreativeModeTab BRUNOMOD_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.EXAMPLE_ITEM.get());
        }
    };

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }

    public BrunoMod()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(ModEvents::onRegisterCapabilities);

        MinecraftForge.EVENT_BUS.register(new ModEvents());

    }

}
