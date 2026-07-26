package com.dimalab.lens;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Lens.MOD_ID)
public class Lens {

    public static final String MOD_ID = "lens";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Lens() {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Zoomify initialized.");
    }
}