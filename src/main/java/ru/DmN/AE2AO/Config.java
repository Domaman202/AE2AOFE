package ru.DmN.AE2AO;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Config SERVER;

    public final ForgeConfigSpec.BooleanValue DisableChannels;
    public final ForgeConfigSpec.BooleanValue ControllerLimits;
    public final ForgeConfigSpec.IntValue[] ControllerSizeLimits = {null, null, null};

    public final ForgeConfigSpec.BooleanValue CellFireResistance;
    public final ForgeConfigSpec.BooleanValue PortableCellFireResistance;

    Config(final ForgeConfigSpec.Builder builder) {
        DisableChannels = builder.define("DisableChannels", false);
        ControllerLimits = builder.define("ControllerLimits", true);
        builder.push("ControllerSizeLimits");
        ControllerSizeLimits[0] = builder.defineInRange("Max_X", 7, 0, 255);
        ControllerSizeLimits[1] = builder.defineInRange("Max_Y", 7, 0, 255);
        ControllerSizeLimits[2] = builder.defineInRange("Max_Z", 7, 0, 255);
        builder.pop();
        CellFireResistance = builder.define("CellFireResistance", false);
        PortableCellFireResistance = builder.define("PortableCellFireResistance", false);
    }

    static {
        Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        SERVER = specPair.getLeft();
        SERVER_SPEC = specPair.getRight();
    }
}
