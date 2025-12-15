package com.bruno.brunomod.client.screen;

import com.bruno.brunomod.network.PacketHandler;
import com.bruno.brunomod.network.packet.PacketSendTraits;
import com.bruno.brunomod.trait.Trait;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class TraitSelectionScreen extends Screen {

    private final List<Trait> tempSelectedTraits = new ArrayList<>();

    private int currentPoints = 0;

    public TraitSelectionScreen() {
        super(new TranslatableComponent("screen.brunomod.traits_title"));
    }

    @Override
    protected void init() {
        super.init();

        int startX = this.width / 2 -150;
        int startY = 50;
        int btnWidth = 140;
        int btnHeight = 20;

        int i = 0;
        for (Trait trait : Trait.values()) {
            if(trait.getType() == Trait.Type.DEBUFF) {
                createTraitButton(trait, startX, startY + (i * 25), btnWidth, btnHeight);
                i++;
            }
        }

        i = 0;
        for (Trait trait : Trait.values()) {
            if(trait.getType() == Trait.Type.BUFF) {
                createTraitButton(trait, startX + 160, startY + (i * 25), btnWidth, btnHeight);
                i++;
            }
        }

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 40, 100, 20,
                new TextComponent("Confirmar"), (button) -> {

            PacketSendTraits packet = new PacketSendTraits(this.tempSelectedTraits);

            PacketHandler.sendToServer(packet);

            this.onClose();
        }));
    }

    private void createTraitButton(Trait trait, int x, int y, int w, int h) {
        this.addRenderableWidget(new Button(x, y, w, h, getButtonText(trait), (btn) -> {
            if(tempSelectedTraits.contains(trait)) {
                tempSelectedTraits.remove(trait);
                currentPoints += trait.getCost();
            } else {
                tempSelectedTraits.add(trait);
                currentPoints -= trait.getCost();
            }

            btn.setMessage(getButtonText(trait));
        }, (btn, poseStack, mouseX, mouseY) -> {
            this.renderTooltip(poseStack, trait.getDescription(), mouseX, mouseY);
        }));
    }

    private TextComponent getButtonText(Trait trait) {
        String check = tempSelectedTraits.contains(trait) ? "[X]" : "[ ]";
        ChatFormatting color = trait.getType() == Trait.Type.BUFF ? ChatFormatting.GREEN : ChatFormatting.RED;

        return new TextComponent(check + trait.getDisplayName().getString() + " (" + trait.getCost() + ")");
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        int color = currentPoints >= 0 ? 0x00FF00 : 0xFF0000;
        drawCenteredString(poseStack, this.font, "Pontos Restantes: " + currentPoints, this.width / 2, 25, color);

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
