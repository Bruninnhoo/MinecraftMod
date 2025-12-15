package com.bruno.brunomod.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class TraitSelectionScreen extends Screen {

    public TraitSelectionScreen() {
        super(new TranslatableComponent("screen.brunomod.traits_title"));
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 40, 100, 20,
                new TextComponent("Aceitar"), (button) -> {

            this.onClose();
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        drawString(poseStack, this.font, "Escolha seu defeitos...", this.width / 2 - 100, 50, 0xAAAAAA);

        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
