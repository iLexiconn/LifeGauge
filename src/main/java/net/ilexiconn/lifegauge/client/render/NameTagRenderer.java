package net.ilexiconn.lifegauge.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.lifegauge.api.LifeGaugeAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NameTagRenderer extends Gui {
    public Minecraft mc = Minecraft.getMinecraft();

    public void renderNameTag(EntityLiving entity, float x, float y, float z) {
        FontRenderer fontRenderer = mc.fontRenderer;
        float f = 1.6f;
        float f1 = 0.016666668f * f;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + entity.height + 0.7f, z);
        GL11.glNormal3f(0f, 1f, 0f);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0f, 1f, 0f);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1f, 0f, 0f);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.instance;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        int j = (int) Math.max(fontRenderer.getStringWidth(entity.getCommandSenderName()) / 2, entity.getMaxHealth() * 8 / 4);
        tessellator.setColorRGBA_F(0f, 0f, 0f, 0.25f);
        tessellator.addVertex(-j - 1d, -1d, 0d);
        tessellator.addVertex(-j - 1d, 8d + 11f, 0d);
        tessellator.addVertex(j + 2d, 8d + 11f, 0d);
        tessellator.addVertex(j + 1d, -1d, 0d);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        String name = entity.getCommandSenderName();
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, 553648127);
        mc.renderEngine.bindTexture(icons);
        renderHealth(entity, fontRenderer);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, -1);
        renderHealth(entity, fontRenderer);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void renderHealth(EntityLiving entity, FontRenderer fontRenderer) {
        boolean poison = false;
        boolean wither = false;
        Potion[] potionArray = LifeGaugeAPI.getPotionsForEntity(entity);
        for (Potion potion : potionArray) {
            if (potion.getId() == Potion.poison.getId()) {
                poison = true;
            } else if (potion.getId() == Potion.wither.getId()) {
                wither = true;
            }
        }
        int i;
        int width = (int) entity.getMaxHealth() * 4 / 2;
        boolean offset = entity.getMaxHealth() == 1f;
        mc.renderEngine.bindTexture(icons);
        for (i = 0; i < Math.round(entity.getMaxHealth() / 2f); i++) {
            drawTexturedModalRect(-width + i * 8 - (offset ? 2 : 0), fontRenderer.FONT_HEIGHT, 16, 0, 9, 9);
        }
        for (i = 0; i < (int) entity.getHealth() / 2; i++) {
            drawTexturedModalRect(-width + i * 8 - (offset ? 2 : 0), fontRenderer.FONT_HEIGHT, poison ? 88 : wither ? 124 : 52, 0, 9, 9);
        }
        if ((int) entity.getHealth() % 2 == 1) {
            drawTexturedModalRect(-width + i * 8 - (offset ? 2 : 0), fontRenderer.FONT_HEIGHT, poison ? 97 : wither ? 133 : 61, 0, 9, 9);
        }
    }
}
