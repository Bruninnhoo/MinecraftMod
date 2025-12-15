package com.bruno.brunomod.event;

import com.bruno.brunomod.BrunoMod;
import com.bruno.brunomod.capability.ITraitData;
import com.bruno.brunomod.capability.TraitProvider;
import com.bruno.brunomod.network.PacketHandler;
import com.bruno.brunomod.network.packet.PacketOpenTraitScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(TraitProvider.TRAIT_CAP).isPresent()){
                event.addCapability(new ResourceLocation(BrunoMod.MOD_ID, "trait_data"), new TraitProvider());
            }
        }
    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ITraitData.class);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (!event.getPlayer().level.isClientSide){
            ServerPlayer player = (ServerPlayer) event.getPlayer();

            player.getCapability(TraitProvider.TRAIT_CAP).ifPresent(data -> {
                if (!data.hasSelected()){
                    PacketHandler.sendToPlayer(new PacketOpenTraitScreen(), player);
                }
            });
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event){
        if(event.isWasDeath()){
            event.getOriginal().getCapability(TraitProvider.TRAIT_CAP).ifPresent(oldData -> {
                event.getPlayer().getCapability(TraitProvider.TRAIT_CAP).ifPresent(newData -> {
                    newData.setSelected(false);
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(!event.getPlayer().level.isClientSide) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();

            player.getCapability(TraitProvider.TRAIT_CAP).ifPresent(data -> {
                if(!data.hasSelected()) {
                    PacketHandler.sendToPlayer(new PacketOpenTraitScreen(), player);
                }
            });
        }
    }

}
