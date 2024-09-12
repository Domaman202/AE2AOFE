package ru.DmN.AE2AO;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Predicate;

public class Config {
    public static final ModConfigSpec Instance_Spec;
    public static final Config Instance;

    public final ModConfigSpec.BooleanValue DisableChannels;
    public final ModConfigSpec.BooleanValue ControllerLimits;
    public final ModConfigSpec.BooleanValue ControllerCross;
    public final ModConfigSpec.BooleanValue MultipleControllers;
    public final MaxBoxSize ControllerSizeLimits;
    public final ModConfigSpec.BooleanValue CellFireResistance;
    public final ModConfigSpec.BooleanValue CellExplosionResistance;
    public final ModConfigSpec.BooleanValue PortableCellFireResistance;
    public final ModConfigSpec.BooleanValue PortableCellExplosionResistance;
    public final ModConfigSpec.ConfigValue<Integer> ItemsPerByte;
    public final ModConfigSpec.ConfigValue<Integer> MbPerByte;

    Config(final ModConfigSpec.Builder builder) {
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
        public final ModConfigSpec.ConfigValue<Integer> Max_X;
        public final ModConfigSpec.ConfigValue<Integer> Max_Y;
        public final ModConfigSpec.ConfigValue<Integer> Max_Z;

        MaxBoxSize(final ModConfigSpec.Builder builder, String name) {

            builder.push(name);

            Max_X = builder.define("Max_X", 7, new MoreOrEqual<>(Integer.class, 1));
            Max_Y = builder.define("Max_Y", 7, new MoreOrEqual<>(Integer.class, 1));
            Max_Z = builder.define("Max_Z", 7, new MoreOrEqual<>(Integer.class, 1));

            builder.pop();
        }
    }

    static {
        Pair<Config, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Config::new);
        Instance = specPair.getLeft();
        Instance_Spec = specPair.getRight();
    }
}
