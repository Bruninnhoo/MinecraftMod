package com.bruno.brunomod.network.packet;

import com.bruno.brunomod.client.ClientHooks;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketOpenTraitScreen {

    public PacketOpenTraitScreen() {}

    public PacketOpenTraitScreen(FriendlyByteBuf buf){}
    public void toBytes(FriendlyByteBuf buf){}

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientHooks.openTraitScreen();
        });

        return true;
    }

}
