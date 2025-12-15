package com.bruno.brunomod.network;

import com.bruno.brunomod.network.packet.PacketOpenTraitScreen;
import com.bruno.brunomod.network.packet.PacketSendTraits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("brunomod", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register() {
        INSTANCE.messageBuilder(PacketOpenTraitScreen.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketOpenTraitScreen::new)
                .encoder(PacketOpenTraitScreen::toBytes)
                .consumer((packet, contextSupplier) -> {
                    packet.handle(contextSupplier);
                })
                .add();

        INSTANCE.messageBuilder(PacketSendTraits.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketSendTraits::new)
                .encoder(PacketSendTraits::toBytes)
                .consumer((packet, context) -> {
                    packet.handle(context);
                })
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }

}
