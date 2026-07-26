package com.dimalab.lens;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = Lens.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public final class ZoomHandler {

    private static final float DEFAULT_ZOOM = 1.0F;
    private static final float TARGET_ZOOM = 0.30F;

    /**
     * Скорость приближения.
     * Больше = быстрее.
     */
    private static final float SMOOTHING = 8.0F;

    private static final double SENSITIVITY_MULTIPLIER = 0.25D;

    private static boolean zooming;
    private static boolean sensitivityModified;

    private static float zoomFactor = DEFAULT_ZOOM;
    private static float targetZoom = DEFAULT_ZOOM;

    private static double originalSensitivity;

    private static long lastFrameTime = System.nanoTime();

    private ZoomHandler() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END)
            return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        zooming = KeyBindings.ZOOM.isDown();

        targetZoom = zooming ? TARGET_ZOOM : DEFAULT_ZOOM;

        if (zooming) {

            if (!sensitivityModified) {

                originalSensitivity = mc.options.sensitivity().get();

                mc.options.sensitivity().set(
                        originalSensitivity * SENSITIVITY_MULTIPLIER
                );

                sensitivityModified = true;
            }

        } else if (sensitivityModified) {

            mc.options.sensitivity().set(originalSensitivity);
            sensitivityModified = false;
        }
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {

        long now = System.nanoTime();
        float delta = (now - lastFrameTime) / 1_000_000_000.0F;
        lastFrameTime = now;

        float alpha = 1.0F - (float) Math.exp(-SMOOTHING * delta);

        zoomFactor += (targetZoom - zoomFactor) * alpha;

        event.setFOV(event.getFOV() * zoomFactor);
    }

    public static boolean isZooming() {
        return zooming;
    }

    public static float getZoomFactor() {
        return zoomFactor;
    }
}