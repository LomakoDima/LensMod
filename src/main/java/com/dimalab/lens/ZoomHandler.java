package com.dimalab.lens;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(
        modid = Lens.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public final class ZoomHandler {

    private static final float DEFAULT_ZOOM = 1.0F;

    private static float desiredZoom = 0.25f;

    private static boolean zooming;
    private static boolean sensitivityModified;

    private static boolean toggleZoom;

    private static float zoomFactor = DEFAULT_ZOOM;
    private static float targetZoom = DEFAULT_ZOOM;

    private static double originalSensitivity;

    private static long lastFrameTime = System.nanoTime();

    private ZoomHandler() {}

    private static void reloadConfig() {
        desiredZoom = LensConfig.DEFAULT_ZOOM.get().floatValue();
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            toggleZoom = false;
            zooming = false;
            return;
        }

        if (mc.player.isScoping()) {
            zooming = false;
            toggleZoom = false;
            targetZoom = DEFAULT_ZOOM;
            return;
        }

        zooming = KeyBindings.ZOOM.isDown();

        targetZoom = zooming ? desiredZoom : DEFAULT_ZOOM;

        if (zooming) {

            if (!sensitivityModified) {
                originalSensitivity = mc.options.sensitivity().get();
                sensitivityModified = true;
            }

            if (LensConfig.ENABLE_SENSITIVITY_REDUCTION.get()) {

                double multiplier = LensConfig.AUTO_SENSITIVITY.get()
                        ? desiredZoom
                        : LensConfig.SENSITIVITY_MULTIPLIER.get();

                mc.options.sensitivity().set(
                        originalSensitivity * multiplier
                );
            }

        } else if (sensitivityModified) {

            mc.options.sensitivity().set(originalSensitivity);
            sensitivityModified = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(ClientPlayerNetworkEvent.LoggingIn event) {

        toggleZoom = false;
        zooming = false;

        while (KeyBindings.ZOOM.consumeClick()) {
            // очищаем очередь нажатий
        }
    }

    @SubscribeEvent
    public static void onPlayerLeave(ClientPlayerNetworkEvent.LoggingOut event) {

        toggleZoom = false;
        zooming = false;

        while (KeyBindings.ZOOM.consumeClick()) {
        }
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {

        long now = System.nanoTime();
        float delta = (now - lastFrameTime) / 1_000_000_000.0F;
        lastFrameTime = now;

        if (LensConfig.ENABLE_SMOOTH_ZOOM.get()) {

            float alpha = 1.0F - (float) Math.exp(
                    -LensConfig.SMOOTHING.get().floatValue() * delta
            );

            zoomFactor += (targetZoom - zoomFactor) * alpha;

        } else {

            zoomFactor = targetZoom;
        }

        event.setFOV(event.getFOV() * zoomFactor);
    }

    public static boolean isZooming() {
        return zooming;
    }

    public static float getZoomFactor() {
        return zoomFactor;
    }


    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {

        if (!zooming)
            return;

        if (event.getScrollDelta() > 0) {

            desiredZoom -= LensConfig.ZOOM_STEP.get().floatValue();

        } else {

            desiredZoom += LensConfig.ZOOM_STEP.get().floatValue();
        }

        desiredZoom = Math.max(
                LensConfig.MIN_ZOOM.get().floatValue(),
                Math.min(
                        LensConfig.MAX_ZOOM.get().floatValue(),
                        desiredZoom
                )
        );

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {

        if (event.getConfig().getSpec() != LensConfig.SPEC)
            return;

        reloadConfig();
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {

        if (event.getConfig().getSpec() != LensConfig.SPEC)
            return;

        reloadConfig();
    }
}