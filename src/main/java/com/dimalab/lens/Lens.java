package com.dimalab.lens;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(Lens.MOD_ID)
public class Lens {
    public static final String MOD_ID = "lens";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Lens() {
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT,
                LensConfig.SPEC
        );

        LOGGER.info("Zoomify initialized.");
    }
}