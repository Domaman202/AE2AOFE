package ru.DmN.AE2AO;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

public class Config {
    public static final ForgeConfigSpec Instance_Spec;
    public static final Config Instance;

    public final ForgeConfigSpec.BooleanValue DisableChannels;
    public final ForgeConfigSpec.BooleanValue ControllerLimits;
    public final ForgeConfigSpec.BooleanValue ControllerCross;
    public final ForgeConfigSpec.BooleanValue MultipleControllers;
    public final MaxBoxSize ControllerSizeLimits;
    public final ForgeConfigSpec.BooleanValue CellFireResistance;
    public final ForgeConfigSpec.BooleanValue CellExplosionResistance;
    public final ForgeConfigSpec.BooleanValue PortableCellFireResistance;
    public final ForgeConfigSpec.BooleanValue PortableCellExplosionResistance;
    public final ForgeConfigSpec.ConfigValue<Integer> ItemsPerByte;
    public final ForgeConfigSpec.ConfigValue<Integer> MbPerByte;

    Config(final ForgeConfigSpec.Builder builder) {
        DisableChannels = builder.comment("""
                Disable all channels logic. If true amount of used channels is always 0."""
        ).define("DisableChannels", false);


        builder.push("Cells");

        CellFireResistance = builder.define("CellFireResistance", false);
        CellExplosionResistance = builder.define("CellExplosionResistance", false);
        PortableCellFireResistance = builder.define("PortableCellFireResistance", false);
        PortableCellExplosionResistance = builder.define("PortableCellExplosionResistance", false);

        ItemsPerByte = builder.define("ItemsPerByte", 8, new MoreOrEqual<>(Integer.class, 1));
        MbPerByte = builder.define("MbPerByte", 8000, new MoreOrEqual<>(Integer.class, 1));

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


        ControllerSizeLimits = new MaxBoxSize(builder, "ControllerSizeLimits");

        builder.pop();
    }

    private static class MoreOrEqual<V extends Comparable<? super V>> implements Predicate<Object> {
        private final Class<? extends V> clazz;
        private final V min;

        private MoreOrEqual(Class<V> clazz, V min) {
            this.clazz = clazz;
            this.min = min;
        }

        @Override
        public boolean test(Object t) {
            if (!clazz.isInstance(t)) return false;
            V c = clazz.cast(t);

            boolean result = c.compareTo(min) >= 0;
            if (!result) {
                AE2AOMain.LOGGER.debug("Value {} is less than minimal = {}", c, min);
            }
            return result;
        }

        @Override
        public String toString() {
            return ">= " + min;
        }
    }

    public static class MaxBoxSize {
        public final ForgeConfigSpec.ConfigValue<Integer> Max_X;
        public final ForgeConfigSpec.ConfigValue<Integer> Max_Y;
        public final ForgeConfigSpec.ConfigValue<Integer> Max_Z;

        MaxBoxSize(final ForgeConfigSpec.Builder builder, String name) {

            builder.push(name);

            Max_X = builder.define("Max_X", 7, new MoreOrEqual<>(Integer.class, 1));
            Max_Y = builder.define("Max_Y", 7, new MoreOrEqual<>(Integer.class, 1));
            Max_Z = builder.define("Max_Z", 7, new MoreOrEqual<>(Integer.class, 1));

            builder.pop();
        }
    }

    static {
        Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        Instance = specPair.getLeft();
        Instance_Spec = specPair.getRight();
    }
}
