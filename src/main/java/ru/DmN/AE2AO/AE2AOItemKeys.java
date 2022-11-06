package ru.DmN.AE2AO;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKeyType;
import appeng.core.AppEng;
import appeng.core.localization.GuiText;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.Objects;
import java.util.stream.Stream;

public class AE2AOItemKeys extends AEKeyType {

    @Override
    public int getAmountPerByte() {
        return AE2AOMain.config.ItemsPerByte.get();
    }

    private static final ResourceLocation ID = AppEng.makeId("i");

    public static final AE2AOItemKeys INSTANCE = new AE2AOItemKeys();

    private AE2AOItemKeys() {
        super(ID, AEItemKey.class, GuiText.Items.text());
    }

    @Override
    public AEItemKey readFromPacket(FriendlyByteBuf input) {
        Objects.requireNonNull(input);

        return AEItemKey.fromPacket(input);
    }

    @Override
    public AEItemKey loadKeyFromTag(CompoundTag tag) {
        return AEItemKey.fromTag(tag);
    }

    @Override
    public boolean supportsFuzzyRangeSearch() {
        return true;
    }

    @Override
    public Stream<TagKey<?>> getTagNames() {
        return Registry.ITEM.getTagNames().map(t -> t);
    }
}