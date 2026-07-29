package com.dimalab.lens;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Lens.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public final class HudRenderer {
    @SubscribeEvent
    public static void onRender(RenderGuiOverlayEvent.Post event) {

        if (!ZoomHandler.isZooming())
            return;

        if (!LensConfig.SHOW_HUD.get())
            return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.screen != null)
            return;

        float zoom = 1.0F / ZoomHandler.getZoomFactor();

        String text = String.format("%.1fx", zoom);

        int x = event.getWindow().getGuiScaledWidth() / 2;
        int y = event.getWindow().getGuiScaledHeight() - 50;

        event.getGuiGraphics().drawCenteredString(
                mc.font,
                text,
                x,
                y,
                0xFFFFFF
        );
    }
}
