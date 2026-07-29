package com.dimalab.lens;

import net.minecraftforge.common.ForgeConfigSpec;

public class LensConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.DoubleValue DEFAULT_ZOOM;
    public static final ForgeConfigSpec.DoubleValue MIN_ZOOM;
    public static final ForgeConfigSpec.DoubleValue MAX_ZOOM;

    public static final ForgeConfigSpec.DoubleValue ZOOM_STEP;

    public static final ForgeConfigSpec.DoubleValue SMOOTHING;

    public static final ForgeConfigSpec.DoubleValue SENSITIVITY_MULTIPLIER;

    public static final ForgeConfigSpec.BooleanValue ENABLE_SMOOTH_ZOOM;
    public static final ForgeConfigSpec.BooleanValue ENABLE_SENSITIVITY_REDUCTION;

    public static final ForgeConfigSpec.BooleanValue AUTO_SENSITIVITY;

    public static final ForgeConfigSpec.BooleanValue SHOW_HUD;

    static {

        BUILDER.push("zoom");

        DEFAULT_ZOOM = BUILDER
                .comment("Default zoom factor")
                .translation("config.lens.defaultZoom")
                .defineInRange("defaultZoom", 0.30D, 0.05D, 1.0D);

        MIN_ZOOM = BUILDER
                .comment("Minimum zoom")
                .translation("config.lens.minZoom")
                .defineInRange("minZoom", 0.05D, 0.01D, 1.0D);

        MAX_ZOOM = BUILDER
                .comment("Maximum zoom")
                .translation("config.lens.maxZoom")
                .defineInRange("maxZoom", 1.0D, 0.05D, 1.0D);

        ZOOM_STEP = BUILDER
                .comment("Zoom step when using mouse wheel")
                .translation("config.lens.zoomStep")
                .defineInRange("zoomStep", 0.05D, 0.01D, 0.50D);

        SMOOTHING = BUILDER
                .comment("Zoom animation speed")
                .translation("config.lens.smoothing")
                .defineInRange("smoothing", 8.0D, 0.1D, 50.0D);

        SENSITIVITY_MULTIPLIER = BUILDER
                .comment("Mouse sensitivity multiplier while zooming")
                .translation("config.lens.sensitivityMultiplier")
                .defineInRange("sensitivityMultiplier", 0.25D, 0.01D, 1.0D);

        ENABLE_SMOOTH_ZOOM = BUILDER
                .comment("Enable smooth zoom animation")
                .translation("config.lens.smoothZoom")
                .define("smoothZoom", true);

        ENABLE_SENSITIVITY_REDUCTION = BUILDER
                .comment("Reduce mouse sensitivity while zooming")
                .translation("config.lens.reduceSensitivity")
                .define("reduceSensitivity", true);

        AUTO_SENSITIVITY = BUILDER
                .comment("Automatically scale mouse sensitivity based on zoom")
                .translation("config.lens.autoSensitivity")
                .define("autoSensitivity", true);

        SHOW_HUD = BUILDER
                .comment("Show zoom HUD")
                .translation("config.lens.showHud")
                .define("showHud", true);

        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}