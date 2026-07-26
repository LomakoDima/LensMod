package com.dimalab.lens;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(
        modid = Lens.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public final class KeyBindings {

    public static final String CATEGORY = "key.categories.zoomify";

    public static final KeyMapping ZOOM = new KeyMapping(
            "key.zoomify.zoom",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            CATEGORY
    );

    private KeyBindings() {
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(ZOOM);
        Lens.LOGGER.info("Zoom key registered.");
    }
}