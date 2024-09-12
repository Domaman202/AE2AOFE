package ru.DmN.AE2AO;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.core.AppEng;
import appeng.core.localization.GuiText;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Objects;
import java.util.stream.Stream;

public class AE2AOFluidKeys extends AEKeyType {

    private static final ResourceLocation ID = AppEng.makeId("f");

    public static final AE2AOFluidKeys INSTANCE = new AE2AOFluidKeys();

    private AE2AOFluidKeys() {
        super(ID, AEFluidKey.class, GuiText.Fluids.text());
    }

    @Override
    public MapCodec<? extends AEKey> codec() {
        return AEFluidKey.MAP_CODEC;
    }

    @Override
    public int getAmountPerOperation() {
        // On Forge this was 125mb (so 125/1000th of a bucket)
        return AEFluidKey.AMOUNT_BUCKET * 125 / 1000;
    }

    @Override
    public int getAmountPerByte() {
        return AE2AOMain.config.MbPerByte.get();
    }

    @Override
    public AEFluidKey readFromPacket(RegistryFriendlyByteBuf input) {
        Objects.requireNonNull(input);

        return AEFluidKey.fromPacket(input);
    }

    @Override
    public int getAmountPerUnit() {
        return AEFluidKey.AMOUNT_BUCKET;
    }

    @Override
    public Stream<TagKey<?>> getTagNames() {
        return BuiltInRegistries.FLUID.getTagNames().map(t -> t);
    }

    @Override
    public String getUnitSymbol() {
        return "B";
    }
}