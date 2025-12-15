package com.bruno.brunomod.network.packet;

import com.bruno.brunomod.capability.TraitProvider;
import com.bruno.brunomod.trait.Trait;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketSendTraits {

    private final List<Trait> traits;

    public PacketSendTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public PacketSendTraits(FriendlyByteBuf buf) {
        this.traits = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            try {
                String name = buf.readUtf();
                this.traits.add(Trait.valueOf(name));
            } catch (Exception ignored) {

            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(traits.size());
        for (Trait t : traits) {
            buf.writeUtf(t.name());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
           ServerPlayer player = context.getSender();

           if(player != null) {
               player.getCapability(TraitProvider.TRAIT_CAP).ifPresent(data -> {
                   data.setTraits(this.traits);

                   System.out.println("Traits salvos para o player: " + player.getName().getString());
               });
           }
        });

        return true;
    }

}
