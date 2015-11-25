package net.ilexiconn.lifegauge.client.render;

import net.ilexiconn.lifegauge.api.LifeGaugeAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class NameTagRenderer extends Gui {
    public Minecraft mc = Minecraft.getMinecraft();

    public void renderNameTag(EntityLiving entity, float x, float y, float z) {
        FontRenderer fontRenderer = mc.fontRendererObj;
        float f = 1.6f;
        float f1 = 0.016666668f * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + entity.height + 0.7f, z);
        GL11.glNormal3f(0f, 1f, 0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0f, 1f, 0f);
        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1f, 0f, 0f);
        GlStateManager.scale(-f1, -f1, f1);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.disableTexture2D();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        int j = (int) Math.max(fontRenderer.getStringWidth(entity.getCommandSenderName()) / 2, entity.getMaxHealth() * 8 / 4);
        worldrenderer.func_181662_b(-j - 1d, -1d, 0d).func_181666_a(0f, 0f, 0.0F, 0.25f).func_181675_d();
        worldrenderer.func_181662_b(-j - 1d, 8d + 11f, 0d).func_181666_a(0f, 0f, 0f, 0.25f).func_181675_d();
        worldrenderer.func_181662_b(j + 2d, 8d + 11f, 0d).func_181666_a(0f, 0f, 0f, 0.25f).func_181675_d();
        worldrenderer.func_181662_b(j + 1d, -1d, 0d).func_181666_a(0f, 0f, 0f, 0.25f).func_181675_d();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        String name = entity.getCommandSenderName();
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, 553648127);
        mc.renderEngine.bindTexture(icons);
        renderHealth(entity, fontRenderer);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRenderer.drawString(name, -fontRenderer.getStringWidth(name) / 2, 0, -1);
        renderHealth(entity, fontRenderer);

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GlStateManager.popMatrix();
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
