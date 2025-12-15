package com.bruno.brunomod.client;

import com.bruno.brunomod.client.screen.TraitSelectionScreen;
import net.minecraft.client.Minecraft;

public class ClientHooks {
    public static void openTraitScreen() {
        Minecraft.getInstance().setScreen(new TraitSelectionScreen());
    }
}
