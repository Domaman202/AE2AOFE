package ru.DmN.AE2AO;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final ForgeConfigSpec Instance_Spec;
    public static final Config Instance;

    public final ForgeConfigSpec.BooleanValue DisableChannels;
    public final ForgeConfigSpec.BooleanValue ControllerLimits;
    public final ForgeConfigSpec.BooleanValue ControllerCross;
    public final ForgeConfigSpec.BooleanValue MultipleControllers;
    public final ForgeConfigSpec.IntValue[] ControllerSizeLimits = {null, null, null};
    public final ForgeConfigSpec.BooleanValue CellFireResistance;
    public final ForgeConfigSpec.BooleanValue CellBlastResistance;
    public final ForgeConfigSpec.BooleanValue PortableCellFireResistance;
    public final ForgeConfigSpec.BooleanValue PortableCellBlastResistance;

    Config(final ForgeConfigSpec.Builder builder) {
        DisableChannels = builder.comment("""
        Disable all channels logic. If true amount of used channels is always 0."""
        ).define("DisableChannels", false);



        builder.push("Cells");

        CellFireResistance = builder.define("CellFireResistance", false);
        CellBlastResistance = builder.define("CellBlastResistance", false);
        PortableCellFireResistance = builder.define("PortableCellFireResistance", false);
        PortableCellBlastResistance = builder.define("PortableCellBlastResistance", false);

        builder.pop();



        builder.push("Controllers");

        ControllerLimits = builder.comment("""
         Control all controller limits. If is false, any variant of controller structure is correct."""
        ).define("ControllerLimits", true);
        ControllerCross = builder.comment("""
         If is false, possible structure with cross pattern, i.e. two neighbors on two or three axes."""
        ).define("ControllerCross", false);
        MultipleControllers = builder.comment("""
         If is true, possible connect to one me net multiple controller structure."""
        ).define("MultipleControllers", false);

        builder.push("ControllerSizeLimits");
        ControllerSizeLimits[0] = builder.defineInRange("Max_X", 7, 0, 255);
        ControllerSizeLimits[1] = builder.defineInRange("Max_Y", 7, 0, 255);
        ControllerSizeLimits[2] = builder.defineInRange("Max_Z", 7, 0, 255);
        builder.pop();

        builder.pop();
    }

    static {
        Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        Instance = specPair.getLeft();
        Instance_Spec = specPair.getRight();
    }
}
