package net.ilexiconn.lifegauge.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.lifegauge.api.LifeGaugeAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class LifeGaugeRenderer extends Gui {
    public ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");

    public void renderLifeGauge(Minecraft mc, FontRenderer fontRenderer, EntityLiving entity, int x, int y) {
        GL11.glPushMatrix();

        Potion[] potionArray = LifeGaugeAPI.getPotionsForEntity(entity);

        int nameWidth = fontRenderer.getStringWidth(entity.getCommandSenderName());
        int healthWidth = fontRenderer.getStringWidth("x" + entity.getMaxHealth() / 2) + 12;
        int width = Math.max(nameWidth, healthWidth) + potionArray.length * 18 + 14;
        int height = 30;

        drawRect(x, y, x + width, y + height, 0xff000000);

        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(icons);
        drawTexturedModalRect(x + 5, y + 17, 52, 0, 9, 9);

        mc.getTextureManager().bindTexture(inventoryBackground);
        for (int i = 0; i < potionArray.length; i++) {
            Potion potion = potionArray[i];
            int i1 = potion.getStatusIconIndex();
            drawTexturedModalRect(x + Math.max(nameWidth, healthWidth) + 9 + i * 18, 5, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
        }

        GL11.glRotated(90f, 0f, 0f, 1f);
        GL11.glTranslated(0, y - 50 - width - x, 0);
        drawGradientRect(0, 0, height, 50, 0x00000000, 0xff000000);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public void renderLifeGaugeText(EntityLiving entity, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRenderer;
        fontRenderer.drawString(entity.getCommandSenderName(), x + 5, y + 5, 0xffffff);
        fontRenderer.drawString("x" + entity.getMaxHealth() / 2, x + 16, y + 17, 0xffffff);
    }
}
